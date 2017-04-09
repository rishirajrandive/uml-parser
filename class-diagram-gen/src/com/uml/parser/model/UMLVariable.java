package com.uml.parser.model;
import com.uml.parser.enums.Modifiers;

import japa.parser.ast.type.Type;

/**
 * Model representing variables in classes
 * @author rishi
 *
 */
public class UMLVariable {

	private int modifier;
	private String name;
	private String initialValue;
	private boolean isUMLClassType;
	private Type type;
	
	/**
	 * @return the modifier
	 */
	public int getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(int modifier) {
		this.modifier = modifier;
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
	 * @return the initialValue
	 */
	public String getInitialValue() {
		return initialValue;
	}
	/**
	 * @param initialValue the initialValue to set
	 */
	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}
	/**
	 * @return the isUMLClassType
	 */
	public boolean isUMLClassType() {
		return isUMLClassType;
	}
	/**
	 * @param isUMLClassType the isUMLClassType to set
	 */
	public void setUMLClassType(boolean isUMLClassType) {
		this.isUMLClassType = isUMLClassType;
	}
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getUMLString(){
		return Modifiers.valueOf(modifier) + name + ": " + type + initialValue + "\n";
	}
}
