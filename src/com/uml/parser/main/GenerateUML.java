package com.uml.parser.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.uml.parser.enums.RelationType;
import com.uml.parser.model.Relationship;
import com.uml.parser.model.UMLClass;
import com.uml.parser.model.UMLMethod;
import com.uml.parser.model.UMLVariable;

import net.sourceforge.plantuml.SourceStringReader;

public class GenerateUML {

	private List<UMLClass> umlClasses;
	private Counselor counselor;
	
	public GenerateUML() {
		counselor = Counselor.getInstance();
		umlClasses = counselor.getUMLClasses();
	}
	
	public void generateUML(String outputFileName){
		StringBuilder umlSource = new StringBuilder();
		umlSource.append("@startuml\nskinparam classAttributeIconSize 0\n");
		for(UMLClass umlClass : umlClasses){
			if(umlClass.isInterface()){
				umlSource.append("interface " + umlClass.getName() + " << interface >> {\n");
			}else {
				umlSource.append("class " + umlClass.getName() + " {\n");
			}
			
			List<UMLVariable> variables = umlClass.getUMLVariables();
			for(UMLVariable variable : variables){
				umlSource.append(variable.getUMLString());
			}
			
			boolean hasSetter = false;
			boolean hasGetter = true;
			String setVariable = "";
			String getVariable = "";
			UMLMethod setterMethod = null;
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
				}else {
					umlSource.append(method.getUMLString());
				}
			}
			if(hasGetter && hasSetter && setVariable.equalsIgnoreCase(getVariable) && setterMethod != null){
				umlSource.append(setterMethod.getParameterizedUMLString());
			}
			
			umlSource.append("}\n\n");
		}
		
		for(Relationship relationship : counselor.getRelationships()){
			if(relationship.getType() == RelationType.DEPENDENCY && isInterfaceDependency(relationship)){
				umlSource.append(relationship.getUMLString());
			}else if(relationship.getType() != RelationType.DEPENDENCY){
				umlSource.append(relationship.getUMLString());
			}
		}
		
		umlSource.append("@enduml");
		System.out.println(umlSource.toString());
		
		OutputStream png = null;
		try 
		{
			png = new FileOutputStream("/Users/rishi/Documents/workspace/UMLParser/src/" + outputFileName + ".png");
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
	
	private boolean isInterfaceDependency(Relationship relationship){
		UMLClass child = relationship.getChild();
		UMLClass parent = relationship.getParent();
		if(child.isInterface() && !parent.isInterface()){
			return true;
		}
		return false;
	}
}
