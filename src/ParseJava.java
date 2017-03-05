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
			UMLClass element = new UMLClass();
			element.setName(type.getName());
			element.setInterface(isInterface);;
			
			for(BodyDeclaration body : bodyDeclarations){
				if(body instanceof FieldDeclaration){
					createUMLVariables(element, (FieldDeclaration) body);
					createRelatives(element, (FieldDeclaration) body);
				}else if(body instanceof MethodDeclaration){
					createUMLMethods(element, (MethodDeclaration) body, false);
				}else if(body instanceof ConstructorDeclaration){
					createUMLMethods(element, (ConstructorDeclaration) body, true);
				}
			}
			group.getElements().add(element);
		}
	}
	
	private void createUMLVariables(UMLClass umlClass, FieldDeclaration field){
		List<VariableDeclarator> variables = field.getVariables();
		for(VariableDeclarator variable : variables){
			UMLVariable umlVariable = new UMLVariable();
			umlVariable.setModifier(field.getModifiers());
			umlVariable.setMultiple(isArray(field.getType()));
			umlVariable.setName(variable.getId().getName());
			umlVariable.setInitialValue(variable.getInit() == null ? "" : " = " + variable.getInit().toString());
			umlVariable.setUMLClassType(isUMLClassType(field.getType()));
			umlVariable.setType(field.getType());
			umlClass.getUMLVariables().add(umlVariable);
		}
	}
	
	private void createRelatives(UMLClass umlClass, FieldDeclaration field){
		Type fieldType = field.getType();
		if(isUMLClassType(fieldType)){	
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
			umlClass.setRelations(relations);
		}
	}
	
	private void createUMLMethods(UMLClass umlClass, BodyDeclaration body, boolean isConstructor){
		UMLMethod umlMethod = new UMLMethod();
		if(isConstructor){
			ConstructorDeclaration constructor = (ConstructorDeclaration) body;
			umlMethod.setConstructor(true);
			umlMethod.setModifier(constructor.getModifiers());
			umlMethod.setName(constructor.getName());
			umlMethod.setParameters(constructor.getParameters());
		}else {
			MethodDeclaration method = (MethodDeclaration) body;
			umlMethod.setConstructor(false);
			umlMethod.setModifier(method.getModifiers());
			umlMethod.setName(method.getName());
			umlMethod.setParameters(method.getParameters());
			umlMethod.setType(method.getType());
		}
		umlClass.getUMLMethods().add(umlMethod);
	}
	
	public void generateUML(){
		StringBuilder umlSource = new StringBuilder();
		umlSource.append("@startuml\nskinparam classAttributeIconSize 0\n");
		for(UMLClass element : group.getElements()){
			if(element.isInterface()){
				umlSource.append("interface " + element.getName() + " << interface >> {\n");
			}else {
				umlSource.append("class " + element.getName() + " {\n");
			}
			
			List<UMLVariable> variables = element.getUMLVariables();
			for(UMLVariable variable : variables){
				umlSource.append(variable.getUMLString());
			}
			
			boolean hasSetter = false;
			boolean hasGetter = true;
			String setVariable = "";
			String getVariable = "";
			UMLMethod setterMethod = null;
			List<UMLMethod> methods = element.getUMLMethods();
			for(UMLMethod method : methods){
				if(method.isConstructor()){
					umlSource.append(method.getParameterizedUMLString());
				}else if(method.getName().contains("set")){
					hasSetter = true;
					setVariable = method.getName().split("set")[1];
					setterMethod = method;
				}else if(method.getName().contains("get")){
					hasGetter = true;
					getVariable = method.getName().split("get")[1];
					umlSource.append(method.getUMLString());
				}else {
					umlSource.append(method.getUMLString());
				}
			}
			if(hasGetter && hasSetter && setVariable.equalsIgnoreCase(getVariable) && setterMethod != null){
				umlSource.append(setterMethod.getParameterizedUMLString());
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
	
	private boolean isUMLClassType(Type type){
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
	
	private boolean isArray(Type type){
		return (type.toString().contains("["));
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