import java.util.ArrayList;
import java.util.List;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class Element {

	private List<FieldDeclaration> fieldDeclarations;
	private List<MethodDeclaration> methodDeclarations;
	private List<ConstructorDeclaration> constructorDeclarations;
	private boolean isInterface;
	private String name;
	
	public Element() {
		fieldDeclarations = new ArrayList<>();
		methodDeclarations = new ArrayList<>();
		constructorDeclarations = new ArrayList<>();
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
}
