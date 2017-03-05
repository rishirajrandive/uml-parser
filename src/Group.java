import java.util.ArrayList;
import java.util.List;

public class Group {

	private static Group group;
	private List<UMLClass> elements;
	
	public static Group getInstance(){
		if(group == null){
			group = new Group();
		}
		return group;
	}
	
	private Group(){
		elements = new ArrayList<>();
	}

	/**
	 * @return the elements
	 */
	public List<UMLClass> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(List<UMLClass> elements) {
		this.elements = elements;
	}
	
}
