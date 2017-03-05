import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class Element {

	private List<FieldDeclaration> fieldDeclarations;
	private List<MethodDeclaration> methodDeclarations;
	private List<ConstructorDeclaration> constructorDeclarations;
	private boolean isInterface;
	private String name;
	private Map<RelationType, List<String>> relations;
	
	public Element() {
		fieldDeclarations = new ArrayList<>();
		methodDeclarations = new ArrayList<>();
		constructorDeclarations = new ArrayList<>();
		relations = new HashMap<>();
	}
	
	/**
	 * @return the fieldDeclarations
	 */
	public List<FieldDeclaration> getFieldDeclarations() {
		return fieldDeclarations;
	}
	/**
	 * @param fieldDeclarations the fieldDeclarations to set
	 */
	public void setFieldDeclarations(List<FieldDeclaration> fieldDeclarations) {
		this.fieldDeclarations = fieldDeclarations;
	}
	/**
	 * @return the methodDeclarations
	 */
	public List<MethodDeclaration> getMethodDeclarations() {
		return methodDeclarations;
	}
	/**
	 * @param methodDeclarations the methodDeclarations to set
	 */
	public void setMethodDeclarations(List<MethodDeclaration> methodDeclarations) {
		this.methodDeclarations = methodDeclarations;
	}
	/**
	 * @return the constructorDeclarations
	 */
	public List<ConstructorDeclaration> getConstructorDeclarations() {
		return constructorDeclarations;
	}
	/**
	 * @param constructorDeclarations the constructorDeclarations to set
	 */
	public void setConstructorDeclarations(List<ConstructorDeclaration> constructorDeclarations) {
		this.constructorDeclarations = constructorDeclarations;
	}
	/**
	 * @return the isInterface
	 */
	public boolean isInterface() {
		return isInterface;
	}
	/**
	 * @param isInterface the isInterface to set
	 */
	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
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
	 * @return the relations
	 */
	public Map<RelationType, List<String>> getRelations() {
		return relations;
	}

	/**
	 * @param relations the relations to set
	 */
	public void setRelations(Map<RelationType, List<String>> relations) {
		this.relations = relations;
	}
	
	
}
