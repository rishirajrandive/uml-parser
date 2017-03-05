import japa.parser.ast.type.Type;

public class UMLVariable {

	private int modifier;
	private String name;
	private String initialValue;
	private boolean isUMLClassType;
	private boolean isMultiple;
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
	 * @return the isMultiple
	 */
	public boolean isMultiple() {
		return isMultiple;
	}
	/**
	 * @param isMultiple the isMultiple to set
	 */
	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
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
