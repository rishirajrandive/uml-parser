import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import net.sourceforge.plantuml.SourceStringReader;

public aspect SequenceGen {

	private int callDepth;
	private static StringBuffer plantUmlString = new StringBuffer();
	static boolean running;

	final pointcut constructorExecution(): !within(Main.generateUML*) && !within(Participant) && !within(SequenceGen) && execution(*.new(..));

	final pointcut traced() : !within(Main.generateUML*) && !within(Participant) && !within(SequenceGen) && (execution(* *.*(..)) && !call(*.new(..)));

	private boolean isConstructorExecution = false;
	private HashMap<Integer, Participant> objects = new HashMap<>();
	private Stack<Integer> stack = new Stack<>();

	before(): constructorExecution() {
		isConstructorExecution = true;
	}

	after(): constructorExecution() {
		isConstructorExecution = false;
	}

	before() : traced() {
		print("Before", thisJoinPoint);
		callDepth++;
	}

	after() : traced() {
		callDepth--;
		if(callDepth == 0){
			plantUmlString.append("@enduml\n");
			generateUML();
		}
	}

	private void print(String prefix, JoinPoint m) {
		if (!isConstructorExecution) {
			MethodSignature signature = (MethodSignature) m.getStaticPart().getSignature();
			String methodName = signature.getName() + "(";
			String[] paramNames = signature.getParameterNames();
			Class[] paramType = signature.getParameterTypes();

			for (int i = 0; i < paramNames.length; i++) {
				methodName += paramNames[i] + " : " + paramType[i].getSimpleName();
				if (i != paramNames.length - 1) {
					methodName += ", ";
				}
			}
			methodName += ") : " + signature.getReturnType().getSimpleName() + "()";
			
			if (callDepth == 0) {
				System.out.println(callDepth + "  " + m.getSignature().getDeclaringTypeName());
				
				objects.put(callDepth, new Participant(m.getSignature().getDeclaringTypeName()));
				plantUmlString.append("@startuml\nhide footbox\n");
				stack.add(callDepth);
			} else {
				if(callDepth <= stack.peek()){
					while(!stack.isEmpty() && callDepth <= stack.peek()){
						int objectId = stack.pop();
						plantUmlString.append("deactivate " + objects.get(objectId).getName() + "\n");
						objects.get(objectId).setActive(false);
					}
					if(callDepth == 1){
						plantUmlString.append("deactivate " + objects.get(stack.peek()).getName() + "\n");
						objects.get(stack.peek()).setActive(false);
					}
				}
				
				if(callDepth > stack.peek()){
					objects.put(callDepth, new Participant(m.getThis().getClass().getSimpleName()));
					Participant parent = objects.get(stack.peek());
					Participant child = objects.get(callDepth);
					plantUmlString.append(parent.getName() + "->" + child.getName() + ":" + methodName + "\n");
					if(!parent.isActive()){
						plantUmlString.append("activate " + parent.getName() + "\n");
						parent.setActive(true);
					}
					if(!child.isActive()){
						plantUmlString.append("activate " + child.getName() + "\n");
						child.setActive(true);
					}
					stack.push(callDepth);
				}
			}
		}
	}

	private static void generateUML() {
		String outputFileName = "sample";
		String umlSource = plantUmlString.toString();
				 
		System.out.println(umlSource);
		try {
			OutputStream png = new FileOutputStream(outputFileName + ".png");
			SourceStringReader reader = new SourceStringReader(umlSource);
			reader.generateImage(png);
			System.out.println(
					"Output UML Class diagram with name '" + outputFileName + ".png' is generated in base directory");
		} catch (FileNotFoundException exception) {
			System.err.println("Failed to create output file " + exception.getMessage());
		} catch (IOException exception) {
			System.err.println("Failed to write to output file " + exception.getMessage());
		}
	}

}

class Participant{
	boolean isActive;
	String name;
	
	public Participant() {
		isActive = false;
	}
	
	public Participant(String name) {
		this.name = name;
		this.isActive = false;
	}
	
	public Participant(boolean isActive, String name) {
		this.isActive = isActive;
		this.name = name;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
