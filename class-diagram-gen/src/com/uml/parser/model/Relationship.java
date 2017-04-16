package com.uml.parser.model;
import com.uml.parser.enums.RelationType;

/**
 * Model for relationships between all the classes.
 * @author rishi
 *
 */
public class Relationship {

	private UMLClass parent;
	private UMLClass child;
	private RelationType type;
	private String parentCardinality;
	private String childCardinality;
	
	/**
	 * Contructor for the relationship
	 */
	public Relationship() {
		parentCardinality = null;
		childCardinality = null;
	}
	
	/**
	 * @return the parent
	 */
	public UMLClass getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(UMLClass parent) {
		this.parent = parent;
	}
	/**
	 * @return the child
	 */
	public UMLClass getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(UMLClass child) {
		this.child = child;
	}
	/**
	 * @return the type
	 */
	public RelationType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(RelationType type) {
		this.type = type;
	}
	/**
	 * @return the parentCardinality
	 */
	public String getParentCardinality() {
		return parentCardinality;
	}
	/**
	 * @param parentCardinality the parentCardinality to set
	 */
	public void setParentCardinality(String parentCardinality) {
		this.parentCardinality = parentCardinality;
	}
	/**
	 * @return the childCardinality
	 */
	public String getChildCardinality() {
		return childCardinality;
	}
	/**
	 * @param childCardinality the childCardinality to set
	 */
	public void setChildCardinality(String childCardinality) {
		this.childCardinality = childCardinality;
	}
	
	public String getUMLString(){
		if(parentCardinality != null && childCardinality != null){
			return (parent.getName() + "\"" + parentCardinality + "\"" + type.getSymbol() + "\"" + childCardinality + "\"" + child.getName() + "\n\n");
		}
		return (child.getName() + type.getSymbol() + parent.getName() + "\n\n");
	}
}
