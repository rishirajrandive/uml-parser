package com.uml.parser.enums;

public enum RelationType {
	// When a class is dependent on another class for its existence or implementation.
	// Example: When a class has object of another passed in a method as parameter.
	DEPENDENCY("..>"), 
	
	// Shows static relationship among two classes with multiplicity, can be uni/bi directional.
	// Example: Car and Customer. Customer has array of objects of Car or Car has object of Customer as owner.
	ASSOCIATION("--"),
	
	// Shows 'has-a' relation, form of association relationship, whole is made of its parts. 
	// Example: School and Student. School is made of Students.
	AGGREGATION("--o"), 
	
	// Shows 'is-a' relation, form of association where one class owns the other. And other class cannot exist if owner is destroyed.
	// Example: Car and Engine where Car owns Engine. Engine cannot exist if Car is destroyed.
	COMPOSITION("--*"),
	
	// Represents inheritance concept in java. When a class is extended by other using extends.
	GENERALIZATION("--|>"),
	
	// Represents relation between class and interface. When class implements interfaces.
	REALIZATION("..|>"); // When implements is used
	
	private String symbol;
	private RelationType(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol(){
		return this.symbol;
	}
}
