import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class UMLClass {

	private List<UMLVariable> umlVariables;
	private List<UMLMethod> umlMethods;
	//private List<ConstructorDeclaration> constructorDeclarations;
	private boolean isInterface;
	private String name;
	private Map<RelationType, List<String>> relations;
	
	public UMLClass() {
		umlVariables = new ArrayList<>();
		umlMethods = new ArrayList<>();
//		constructorDeclarations = new ArrayList<>();
		relations = new HashMap<>();
	}
	
	/**
	 * @return the fieldDeclarations
	 */
	public List<UMLVariable> getUMLVariables() {
		return umlVariables;
	}
	/**
	 * @param umlVariables the fieldDeclarations to set
	 */
	public void setUMLVariables(List<UMLVariable> umlVariables) {
		this.umlVariables = umlVariables;
	}
	/**
	 * @return the methodDeclarations
	 */
	public List<UMLMethod> getUMLMethods() {
		return umlMethods;
	}
	/**
	 * @param umlMethods the methodDeclarations to set
	 */
	public void setUMLMethods(List<UMLMethod> umlMethods) {
		this.umlMethods = umlMethods;
	}
//	/**
//	 * @return the constructorDeclarations
//	 */
//	public List<ConstructorDeclaration> getConstructorDeclarations() {
//		return constructorDeclarations;
//	}
//	/**
//	 * @param constructorDeclarations the constructorDeclarations to set
//	 */
//	public void setConstructorDeclarations(List<ConstructorDeclaration> constructorDeclarations) {
//		this.constructorDeclarations = constructorDeclarations;
//	}
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
