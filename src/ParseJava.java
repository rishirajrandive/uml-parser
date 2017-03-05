import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.type.ArrayType;
import javax.print.attribute.HashAttributeSet;
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
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.PrimitiveType.Primitive;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import net.sourceforge.plantuml.SourceStringReader;

public class ParseJava {

	private Group group;
	
	public static void main(String[] args) {
		String dir = "/Users/rishi/Documents/workspace/UMLParser/src";
		String[] fileNames = {"Car.java", "Customer.java"};
		
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
					
					List<FieldDeclaration> fieldDeclarations = element.getFieldDeclarations();
					for(FieldDeclaration field : fieldDeclarations){
						Type fieldType = field.getType();
						if(isElement(fieldType)){	
							Map<RelationType, List<String>> relations = new HashMap<>();
							List<String> relatives = null;
							if(relations.containsKey(RelationType.ASSOCIATION)){
								relatives = relations.get(RelationType.ASSOCIATION);
								relatives.add(getElementName(fieldType.toString()));
							}else{
								relatives = new ArrayList<>();
								relatives.add(getElementName(fieldType.toString()));
								relations.put(RelationType.ASSOCIATION, relatives);
							}
							element.setRelations(relations);
						}
					}
					
					
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
					umlSource.append(Modifiers.valueOf(field.getModifiers()));
					umlSource.append(variable.getId().getName() + ":" + field.getType() + (variable.getInit() == null ? "" : " = " + variable.getInit().toString()) + "\n");
				}
			}
			
			List<ConstructorDeclaration> constructors = element.getConstructorDeclarations();
			for(ConstructorDeclaration constructor : constructors){
				List<Parameter> parameters = constructor.getParameters();
				if(parameters != null){
					umlSource.append(Modifiers.valueOf(constructor.getModifiers()));
					umlSource.append(constructor.getName() + "(");
					for(int i=0; i < parameters.size(); i++){
						umlSource.append(parameters.get(i).getId().getName() + ":" + parameters.get(i).getType());
					}
					// TODO Make sure more than one parameters could be added
					umlSource.append(") \n");
				}
			}
			
			List<MethodDeclaration> methods = element.getMethodDeclarations();
			for(MethodDeclaration method : methods){
				umlSource.append(Modifiers.valueOf(method.getModifiers()));
				umlSource.append(method.getName() + "():" + method.getType() + "\n");
			}
			umlSource.append("}\n\n");
			
			//Add relationships
			List<String> relatives = element.getRelations().get(RelationType.ASSOCIATION);
			for(int index=0; relatives != null && index<relatives.size(); index++){
				umlSource.append(element.getName() + RelationType.ASSOCIATION.getSymbol() + relatives.get(index) + "\n\n");
			}
		}
		
		umlSource.append("@enduml");
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
	
	private boolean isElement(Type type){
		String typeStr = getElementName(type.toString());
		if(type instanceof ReferenceType){
			ReferenceType actualType = (ReferenceType) type;
			if(actualType.getType() instanceof PrimitiveType){
				return false;
			}else if(typeStr.equalsIgnoreCase("Double") || typeStr.equalsIgnoreCase("Float") || 
					typeStr.equalsIgnoreCase("Long") || typeStr.equalsIgnoreCase("Integer") || 
					typeStr.equalsIgnoreCase("Short") || typeStr.equalsIgnoreCase("Character") ||
			        typeStr.equalsIgnoreCase("Byte") || typeStr.equalsIgnoreCase("Boolean") || 
			        typeStr.equalsIgnoreCase("String")){
				return false;
			}else if(typeStr.equalsIgnoreCase("ArrayList")){
				return false;
			}
		}
		return true;
	}
	
	private String getElementName(String typeStr){
		if(typeStr.contains("[")){
			System.out.println("Found array []");
			typeStr = typeStr.split("\\[")[0];
		}else if(typeStr.contains("<")){
			System.out.println("Found array <>");
			typeStr = typeStr.split("<")[0];
		}
		return typeStr;
	}
}