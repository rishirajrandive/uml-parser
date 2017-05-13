package com.uml.parser.main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
		if(outputFileName.indexOf('.') != -1){
			outputFileName = outputFileName.split("\\.")[0];
		}
		StringBuilder umlSource = new StringBuilder();
		umlSource.append("@startuml \nskinparam classAttributeIconSize 0\n");
		for(UMLClass umlClass : counselor.getUMLClasses()){
			if(umlClass.isInterface()){
				umlSource.append("interface " + umlClass.getName() + " << interface >> {\n");
			}else {
				umlSource.append("class " + umlClass.getName() + " {\n");
			}
			
			boolean hasSetter = false;
			boolean hasGetter = false;
			String setVariable = "";
			String getVariable = "";
			UMLMethod setterMethod = null;
			UMLMethod getterMethod = null;
			List<UMLMethod> methods = umlClass.getUMLMethods();
			for(UMLMethod method : methods){
				if(!isMethodPublic(method)){
					continue;
				}
				if(method.isConstructor()){
					umlSource.append(method.getParameterizedUMLString());
				}else if(method.getName().contains("set") && method.getName().split("set").length > 1){
					hasSetter = true;
					setVariable = method.getName().split("set")[1];
					setterMethod = method;
				}else if(method.getName().contains("get") && method.getName().split("get").length > 1){
					hasGetter = true;
					getVariable = method.getName().split("get")[1];
					getterMethod = method;
				}else if(isMethodPublic(method)){
					umlSource.append(method.getParameterizedUMLString());
				}
			}
			if(hasGetter && hasSetter && setVariable.equalsIgnoreCase(getVariable) && setterMethod != null){
				if(umlClass.hasVariable(getVariable)){
					counselor.updateVariableToPublic(umlClass, getVariable);
					counselor.removeSetterGetterMethod(umlClass, getterMethod, setterMethod);
				}else {
					umlSource.append(getterMethod.getParameterizedUMLString());
					umlSource.append(setterMethod.getParameterizedUMLString());
				}
			}
			
			List<UMLVariable> variables = umlClass.getUMLVariables();
			for(UMLVariable variable : variables){
				if(variable.getModifier() != Modifiers.PROTECTED.modifier && variable.getModifier() != Modifiers.PACKAGE.modifier && 
						!variable.isUMLClassType()){
					umlSource.append(variable.getUMLString());
				}
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
		
		umlSource.append("hide circle \n@enduml");
		dumpGrammarToFile(umlSource.toString());
		generateUML(outputFileName, umlSource.toString());
	}
	
	/**
	 * Actually generates the PNG with provided output file name
	 * @param outputFileName
	 * @param umlSource
	 */
	private void generateUML(String outputFileName, String umlSource){
		try{
			OutputStream png = new FileOutputStream(outputFileName + ".png");
			SourceStringReader reader = new SourceStringReader(umlSource);
			reader.generateImage(png);
			System.out.println("Output UML Class diagram with name '"+outputFileName +".png' is generated in base directory");
		}
		catch (FileNotFoundException exception) {
			System.err.println("Failed to create output file " + exception.getMessage());
		}
		catch (IOException exception){
			System.err.println("Failed to write to output file " + exception.getMessage());
		}
	}
	
	/**
	 * Returns true if the method is public, checks for all combinations
	 * @param method
	 * @return
	 */
	private boolean isMethodPublic(UMLMethod method){
		if(method.getModifier() == Modifiers.PUBLIC.modifier || method.getModifier() == Modifiers.PUBLIC_STATIC.modifier ||
				method.getModifier() == Modifiers.PUBLIC_ABSTRACT.modifier){
			return true;
		}
		return false;
	}
	
	/**
	 * Dumps the grammar generated for UML Class diagram to txt file.
	 * @param grammar
	 */
	private void dumpGrammarToFile(String grammar){
	    try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("PlantUMLGrammar.txt"));
			writer.write(grammar);
		    writer.close();
		    System.out.println("PlantUML grammar is dumped in 'PlantUMLGrammar.txt' file in base directory");
		} catch (IOException e) {
			System.err.println("Failed to dump grammar to text file: "+ e.getMessage());
		}
	    
	}
}
