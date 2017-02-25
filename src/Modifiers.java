
public enum Modifiers {

	PRIVATE(4, "-"),
	PUBLIC(1, "+");
	
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
}
