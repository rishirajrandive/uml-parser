package com.uml.parser.enums;

/**
 * Defines all the possible modifiers and their symbols to be used
 * in the grammar
 * @author rishi
 *
 */
public enum Modifiers {

	PRIVATE(2, "-"),
	PROTECTED(4, "#"),
	PUBLIC(1, "+"),
	PACKAGE(0, "~");
	
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
	
	public static int getModifier(Modifiers modifier){
		for(Modifiers mod : Modifiers.values()){
			if(mod.modifier == modifier.modifier){
				return mod.modifier;
			}
		}
		return -1;
	}
}
