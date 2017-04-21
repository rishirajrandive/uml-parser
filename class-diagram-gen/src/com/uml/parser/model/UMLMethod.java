package com.uml.parser.model;
import java.util.ArrayList;
import java.util.List;

import com.uml.parser.enums.Modifiers;

import japa.parser.ast.type.Type;

import japa.parser.ast.body.Parameter;

/**
 * Model representing methods in the classes
 * @author rishi
 *
 */
public class UMLMethod {

	private String name;
	private List<Parameter> parameters;
	private int modifier;
	private boolean isConstructor;
	private Type type;
	
	public UMLMethod() {
		parameters = new ArrayList<>();
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
	 * @return the parameters
	 */
	public List<Parameter> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
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
	 * @return the isConstructor
	 */
	public boolean isConstructor() {
		return isConstructor;
	}
	/**
	 * @param isConstructor the isConstructor to set
	 */
	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
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

	public String getParameterizedUMLString(){
		StringBuffer umlStr = new StringBuffer();
		if(parameters != null){
			umlStr.append(Modifiers.valueOf(modifier));
			umlStr.append(name + "(");
			for(int i=0; i < parameters.size(); i++){
				umlStr.append(parameters.get(i).getId().getName() + ": " + parameters.get(i).getType());
			}
			// TODO Make sure more than one parameters could be added
			umlStr.append(")");
		}else {
			umlStr.append(Modifiers.valueOf(modifier) + name + "()");
		}
		
		return (type != null) ? umlStr.append(": " + type + "\n").toString() : umlStr.append(" \n").toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof UMLMethod)){
			return false;
		}
		if(obj == this){
			return true;
		}
		return this.getName().equalsIgnoreCase(((UMLMethod)obj).getName());
	}
}
