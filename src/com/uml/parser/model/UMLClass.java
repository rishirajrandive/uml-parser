package com.uml.parser.model;
import java.util.ArrayList;
import java.util.List;

public class UMLClass {

	private List<UMLVariable> umlVariables;
	private List<UMLMethod> umlMethods;
	//private List<ConstructorDeclaration> constructorDeclarations;
	private boolean isInterface;
	private String name;
	
	public UMLClass() {
		name = "";
		isInterface = false;
		umlVariables = new ArrayList<>();
		umlMethods = new ArrayList<>();
	}
	
	/**
	 * @return the fieldDeclarations
	 */
	public List<UMLVariable> getUMLVariables() {
		return umlVariables;
	}
	/**
	 * @param umlVariables the fieldDeclarations to set
	 */
	public void setUMLVariables(List<UMLVariable> umlVariables) {
		this.umlVariables = umlVariables;
	}
	/**
	 * @return the methodDeclarations
	 */
	public List<UMLMethod> getUMLMethods() {
		return umlMethods;
	}
	/**
	 * @param umlMethods the methodDeclarations to set
	 */
	public void setUMLMethods(List<UMLMethod> umlMethods) {
		this.umlMethods = umlMethods;
	}
	
	/**
	 * @return the isInterface
	 */
	public boolean isInterface() {
		return isInterface;
	}
	/**
	 * @param isInterface the isInterface to set
	 */
	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
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
