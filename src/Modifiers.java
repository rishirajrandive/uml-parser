
public enum Modifiers {

	PRIVATE(2, "-"),
	PROTECTED(4, "#"),
	PUBLIC(1, "+"),
	PACKAGE(0, "~");
	
	private int modifier;
	private String symbol;
	
	private Modifiers(int modifier, String symbol) {
		this.modifier = modifier;
		this.symbol = symbol;
	}
	
	public int getModifier(){
		return this.modifier;
	}
	
	public String getSymbol(){
		return this.symbol;
	}
	
	public static String valueOf(int modifier){
		for(Modifiers mod : Modifiers.values()){
			if(mod.modifier == modifier){
				return mod.symbol;
			}
		}
		return "";
	}
}
