import java.util.List;
import java.util.Map;

public class Relationships {

	private Element parent;
	private Element child;
	private RelationType type;
	private String parentCardinality;
	private String childCardinality;
	
	
	/**
	 * @return the parent
	 */
	public Element getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Element parent) {
		this.parent = parent;
	}
	/**
	 * @return the child
	 */
	public Element getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(Element child) {
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
}
