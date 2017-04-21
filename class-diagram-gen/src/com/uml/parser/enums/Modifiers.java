package com.uml.parser.enums;

/**
 * Defines all the possible modifiers and their symbols to be used
 * in the grammar
 * @author rishi
 *
 */
public enum Modifiers {

	PRIVATE(2, "-"),
	PRIVATE_STATIC(10, "- {static}"),
	PRIVATE_FINAL_STATIC(26, ""),
	
	PROTECTED(4, "#"),
	PROTECTED_STATIC(12, "{static}"),
	PROTECTED_ABSTRACT(1028, "# {abstract}"),
	PROTECTED_FINAL_STATIC(28, ""),
	
	PUBLIC(1, "+"),
	PUBLIC_STATIC(9, "+ {static}"),
	PUBLIC_ABSTRACT(1025, "+ {abstract}"),
	PUBLIC_FINAL_STATIC(25, ""),
	
	PACKAGE(0, "~"),
	PACKAGE_STATIC(8, "~ {static}"),
	PACKAGE_ABSTRACT(1024, "~ {abstract}"),
	PACKAGE_FINAL_STATIC(24, "");
	
	
	public int modifier;
	public String symbol;
	
	/**
	 * Initializes the modifiers with number and symobol for it 
	 * @param modifier
	 * @param symbol
	 */
	private Modifiers(int modifier, String symbol) {
		this.modifier = modifier;
		this.symbol = symbol;
	}
	
	/**
	 * Returns value for modifier
	 * @param modifier
	 * @return
	 */
	public static String valueOf(int modifier){
		for(Modifiers mod : Modifiers.values()){
			if(mod.modifier == modifier){
				return mod.symbol;
			}
		}
		return "";
	}
}
