
public enum RelationType {	
	DEPENDENCY(""),
	ASSOCIATION("-->"),
	AGGREGATION(""),
	COMPOSITION(""),
	INHERITANCE(""),
	GENERALIZATION(""),
	REALIZATION("");
	
	private String symbol;
	private RelationType(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol(){
		return this.symbol;
	}
}
