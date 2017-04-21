package com.uml.parser.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Model representing each class provided.
 * @author rishi
 *
 */
public class UMLClass {

	private List<UMLVariable> umlVariables;
	private List<UMLMethod> umlMethods;
	private boolean isInterface;
	private String name;
	private List<String> parents;
	
	/**
	 * Constructor for initializing the variables
	 */
	public UMLClass() {
		name = "";
		isInterface = false;
		umlVariables = new ArrayList<>();
		umlMethods = new ArrayList<>();
		parents = new ArrayList<>();
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

	/**
	 * @return the parents
	 */
	public List<String> getParents() {
		return parents;
	}

	/**
	 * @param parents the parents to set
	 */
	public void setParents(List<String> parents) {
		this.parents = parents;
	}
	
	/**
	 * Adds individual parent one by one
	 * @param parent
	 */
	public void addParent(String parent){
		this.parents.add(parent);
	}
	
	/**
	 * Returns true if the Class already has variable
	 * @param variable
	 * @return
	 */
	public boolean hasVariable(String variable){
		for(UMLVariable var : umlVariables){
			if(var.getName().equalsIgnoreCase(variable)){
				return true;
			}
		}
		return false;
	}
	
}
