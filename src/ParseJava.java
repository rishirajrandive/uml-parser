import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.sound.midi.SysexMessage;

import gen.lib.cgraph.main__c;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import net.sourceforge.plantuml.SourceStringReader;

public class ParseJava {

	private Group group;
	
	public static void main(String[] args) {
		String fileName = "test.java";
		String dir = "/Users/rishi/Documents/workspace/UMLParser/src";
		String[] fileNames = new String[1];
		fileNames[0] = fileName;
		
		ParseJava obj = new ParseJava();
		obj.parseFiles(fileNames, dir);
	}
	
	
	public ParseJava() {
		group = Group.getInstance();
	}
	
	public void parseFiles(String[] fileNames, String dir){
		FileInputStream inputStream = null;
		try{
			for(String fileName : fileNames){
				File file = new File(dir + "/" + fileName);
				System.out.println(file.getAbsolutePath());
				inputStream = new FileInputStream(file.getAbsolutePath());
				CompilationUnit compliationUnit = JavaParser.parse(file);
				createElements(compliationUnit);
			}
		}catch(FileNotFoundException ex){
			System.err.println("Error: File not found. Trace: "+ ex.getMessage());
		}catch(IOException ex){
			System.err.println("Error: IO Exception. Trace: "+ ex.getStackTrace());
		}catch(ParseException ex){
			System.err.println("Error: Parse exception. Trace: "+ ex.getStackTrace());
		}

		generateUML();
	}
	
	private void createElements(CompilationUnit compliationUnit){
		
		List<TypeDeclaration> types = compliationUnit.getTypes();
		for(TypeDeclaration type : types){
			List<BodyDeclaration> bodyDeclarations = type.getMembers();
			boolean isInterface = ((ClassOrInterfaceDeclaration) type).isInterface();
			System.out.println("Name "+type.getName());
			Element element = new Element();
			element.setName(type.getName());
			element.setInterface(isInterface);;
			
			for(BodyDeclaration body : bodyDeclarations){
				if(body instanceof FieldDeclaration){
					element.getFieldDeclarations().add((FieldDeclaration) body);
				}else if(body instanceof MethodDeclaration){
					element.getMethodDeclarations().add((MethodDeclaration) body);
				}else if(body instanceof ConstructorDeclaration){
					element.getConstructorDeclarations().add((ConstructorDeclaration) body);
				}
			}
			group.getElements().add(element);
		}
	}
	
	public void generateUML(){
		StringBuilder umlSource = new StringBuilder();
		umlSource.append("@startuml\nskinparam classAttributeIconSize 0\n");
		for(Element element : group.getElements()){
			if(element.isInterface()){
				umlSource.append("interface " + element.getName() + " << interface >> {\n");
			}else {
				umlSource.append("class " + element.getName() + " {\n");
			}
			
			List<FieldDeclaration> fieldDeclarations = element.getFieldDeclarations();
			for(FieldDeclaration field : fieldDeclarations){
				List<VariableDeclarator> variables = field.getVariables();
				for(VariableDeclarator variable : variables){
					if(field.getModifiers() == Modifiers.PRIVATE.getModifier()){
						umlSource.append(Modifiers.PRIVATE.getSymbol());
					}else if(field.getModifiers() == Modifiers.PUBLIC.getModifier()){
						umlSource.append(Modifiers.PUBLIC.getSymbol());
					}
					umlSource.append(variable.getId().getName() + ":" + field.getType() + "\n");
				}
			}
			
			List<MethodDeclaration> methods = element.getMethodDeclarations();
			for(MethodDeclaration method : methods){
				if(method.getModifiers() == Modifiers.PRIVATE.getModifier()){
					umlSource.append(Modifiers.PRIVATE.getSymbol());
				}else if(method.getModifiers() == Modifiers.PUBLIC.getModifier()){
					umlSource.append(Modifiers.PUBLIC.getSymbol());
				}
				umlSource.append(method.getName() + "():" + method.getType() + "\n");
			}
		}
		umlSource.append("}\n@enduml");
		
		System.out.println(umlSource.toString());
		
		OutputStream png = null;
		try 
		{
			png = new FileOutputStream("/Users/rishi/Documents/workspace/UMLParser/src/test.png");
		}
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		SourceStringReader reader = new SourceStringReader(umlSource.toString());
		try 
		{
			reader.generateImage(png);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	

}
