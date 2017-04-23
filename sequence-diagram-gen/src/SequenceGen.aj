import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import net.sourceforge.plantuml.SourceStringReader;

public aspect SequenceGen {

	private int callDepth;
	private static StringBuffer plantUmlString = new StringBuffer();
	static boolean running;

	final pointcut constructorExecution(): !within(Main.generateUML*) && !within(SequenceGen) && execution(*.new(..));

	final pointcut traced() : !within(Main.generateUML*) && !within(SequenceGen) && (execution(* *.*(..)) && !call(*.new(..)));

	private boolean isConstructorExecution = false;
	private HashMap<Integer, String> objects = new HashMap<>();
	private HashMap<String, String> participants = new HashMap<>();
	private int currentParent = -1;
	private int currentChild = -1;

	before(): constructorExecution() {
		// System.out.println(thisJoinPoint.getSignature().getDeclaringTypeName());
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
		// plantUmlString.append("->" +
		// thisJoinPoint.getTarget().getClass().getCanonicalName());
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
			for (int i = 0; i < callDepth; i++) {
				System.out.print(".");
			} 
			
			if (callDepth == 0) {
				System.out.println(callDepth + "  " + m.getSignature().getDeclaringTypeName());
				objects.put(callDepth, m.getSignature().getDeclaringTypeName());
				plantUmlString.append("@startuml\nhide footbox\n");
			} else {
				System.out.println(callDepth + "  " + m.getThis().getClass().getSimpleName());
//				if(objects.containsKey(callDepth) && objects.containsKey(callDepth-1)){
//					plantUmlString.append("deactivate " + objects.get(callDepth) + "\n");
//					plantUmlString.append("deactivate " + objects.get(callDepth - 1) + "\n");
//				}
				
				
				objects.put(callDepth, m.getThis().getClass().getSimpleName());
				String parent = objects.get(callDepth - 1);
				String child = objects.get(callDepth);
				if(!participants.containsKey(parent)){
					participants.put(parent, child);
				}else {
					plantUmlString.append("deactivate " + participants.get(parent) + "\n");
					for(Entry<String, String> key : participants.entrySet()){
						if(key.getKey().equalsIgnoreCase(parent)){
							plantUmlString.append("deactivate " + key.getKey() + "\n");
						}
					}
					
				}
				plantUmlString.append(parent + "->" + child + ":" + methodName + "\n");
				plantUmlString.append("activate " + child + "\n");
				plantUmlString.append("activate " + parent + "\n");
			}
		}
		// System.out.println(m.getStaticPart().getSignature().getName());

	}

	private static void generateUML() {
		String outputFileName = "sample";
		String umlSource = "@startuml \n Main->TheEconomy:attach(obj : Observer) : void() \n " +
				 "Main->TheEconomy:attach(obj : Observer) : void() \n"+
				 "Main->TheEconomy:setState(status : String) : void()" +
				 "activate TheEconomy \n" +
				 "activate Main\n deactivate TheEconomy \n" +
				 "deactivate Main \n" +
				 " @enduml"; 
				 
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
