package com.uml.parser.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.uml.parser.enums.Modifiers;
import com.uml.parser.enums.RelationType;
import com.uml.parser.model.Relationship;
import com.uml.parser.model.UMLClass;
import com.uml.parser.model.UMLMethod;
import com.uml.parser.model.UMLVariable;

import net.sourceforge.plantuml.SourceStringReader;

/**
 * Generates the UML based on the {@link UMLClass} and {@link Relationship}
 * provided by {@link Counselor}
 * @author rishi
 *
 */
public class GenerateUML {

	private Counselor counselor;
	
	/**
	 * Constructor fetches instance of {@link Counselor}
	 */
	public GenerateUML() {
		counselor = Counselor.getInstance();
	}
	
	/**
	 * Generation of UML for class diagram is done by writing the grammar
	 * @param outputFileName
	 */
	public void createGrammar(String outputFileName){
		StringBuilder umlSource = new StringBuilder();
		umlSource.append("@startuml\nskinparam classAttributeIconSize 0\n");
		for(UMLClass umlClass : counselor.getUMLClasses()){
			if(umlClass.isInterface()){
				umlSource.append("interface " + umlClass.getName() + " << interface >> {\n");
			}else {
				umlSource.append("class " + umlClass.getName() + " {\n");
			}
			
			List<UMLVariable> variables = umlClass.getUMLVariables();
			for(UMLVariable variable : variables){
				if(variable.getModifier() != Modifiers.PROTECTED.modifier && variable.getModifier() != Modifiers.PACKAGE.modifier && 
						!variable.isUMLClassType()){
					umlSource.append(variable.getUMLString());
				}
			}
			
			boolean hasSetter = false;
			boolean hasGetter = false;
			String setVariable = "";
			String getVariable = "";
			UMLMethod setterMethod = null;
			//FIXME For #5 main has no args shown
			//FIXME No need to show the method for get and set, just make the variable public
			//For test case #3 and #4, conflict in #4 get and set methods are shown
			List<UMLMethod> methods = umlClass.getUMLMethods();
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
				}else if(isMethodPublic(method)){
					umlSource.append(method.getParameterizedUMLString());
				}
			}
			if(hasGetter && hasSetter && setVariable.equalsIgnoreCase(getVariable) && setterMethod != null){
				umlSource.append(setterMethod.getParameterizedUMLString());
			}
			
			umlSource.append("}\n\n");
		}
		
		for(Relationship relationship : counselor.getRelationships()){
			if(relationship.getType() == RelationType.DEPENDENCY && UMLHelper.isInterfaceDependency(relationship)){
				umlSource.append(relationship.getUMLString());
			}else if(relationship.getType() != RelationType.DEPENDENCY){
				umlSource.append(relationship.getUMLString());
			}
		}
		
		umlSource.append("@enduml");
		System.out.println(umlSource.toString());
		
		generateUML(outputFileName, umlSource.toString());
	}
	
	/**
	 * Actually generates the PNG with provided output file name
	 * @param outputFileName
	 * @param umlSource
	 */
	private void generateUML(String outputFileName, String umlSource){
		OutputStream png = null;
		try 
		{
			png = new FileOutputStream("/Users/rishi/Documents/workspace/uml-parser/class-diagram-gen/src/" + outputFileName + ".png");
		}
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		SourceStringReader reader = new SourceStringReader(umlSource);
		try 
		{
			reader.generateImage(png);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	private boolean isMethodPublic(UMLMethod method){
		if(method.getModifier() == Modifiers.PUBLIC.modifier || method.getModifier() == Modifiers.PUBLIC_STATIC.modifier ||
				method.getModifier() == Modifiers.PUBLIC_ABSTRACT.modifier){
			return true;
		}
		return false;
	}
}
