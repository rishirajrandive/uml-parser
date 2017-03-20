package com.uml.parser.main;

import java.util.ArrayList;
import java.util.List;

import com.uml.parser.enums.RelationType;
import com.uml.parser.model.Relationship;
import com.uml.parser.model.UMLClass;
import com.uml.parser.model.UMLMethod;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;

public class Counselor {
	
	private static Counselor counselor;
	private List<Relationship> relationships;
	private List<UMLClass> umlClasses;
	
	public static Counselor getInstance(){
		if(counselor == null){
			counselor = new Counselor();
		}
		return counselor;
	}
	
	private Counselor() {
		relationships = new ArrayList<>();
		umlClasses = new ArrayList<>();
	}

	public void checkForRelatives(UMLClass umlClass, TypeDeclaration type){
		ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) type;
		if(classOrInterfaceDeclaration.getExtends() != null){
			List<ClassOrInterfaceType> extendList = classOrInterfaceDeclaration.getExtends();
			for(ClassOrInterfaceType ext : extendList){
				createRelationship(umlClass, ext, RelationType.GENERALIZATION);
			}
		}
		
		if(classOrInterfaceDeclaration.getImplements() != null){
			List<ClassOrInterfaceType> implementList = classOrInterfaceDeclaration.getImplements();
			for(ClassOrInterfaceType imp : implementList){
				createRelationship(umlClass, imp, RelationType.REALIZATION);
			}
		}
	}
	
	public void checkForRelatives(UMLClass umlClass, UMLMethod method){
		if(method.getParameters() != null){
			List<Parameter> parameters = method.getParameters();
			for(Parameter parameter : parameters){
				if(UMLHelper.isUMLClassType(parameter.getType())){
					createRelationship(umlClass, parameter.getType(), RelationType.DEPENDENCY);
				}
			}
		}
	}
	
	public void checkForRelatives(UMLClass umlClass, FieldDeclaration field){
		Type fieldType = field.getType();
		if(UMLHelper.isUMLClassType(fieldType)){	
			createRelationship(umlClass, fieldType, RelationType.ASSOCIATION);
		}
	}
	
//	public void checkForRelatives(UMLClass umlClass, VariableDeclarationExpr variableDeclarationExpr){
//		Type variableType = variableDeclarationExpr.getType();
//		if(UMLHelper.isUMLClassArray(variableType)){
//			String umlClassName = UMLHelper.getArrayClassName(variableType);
//		}
//		if(UMLHelper.isUMLClassType(variableType)){	
//			createRelationship(umlClass, variableType, RelationType.ASSOCIATION);
//		}
//	}
	
	public void createRelationship(UMLClass umlClass, Type relative, RelationType relationType){
		Relationship relationship = new Relationship();
		relationship.setType(relationType);
		if(relationType == RelationType.ASSOCIATION && UMLHelper.isUMLClassArray(relative)){
			relationship.setParentCardinality("1");
			relationship.setChildCardinality("0..*");
			relationship.setChild(counselor.getUMLClass(UMLHelper.getArrayClassName(relative)));
		}else {
			relationship.setChild(counselor.getUMLClass(relative.toString()));
		}
		relationship.setParent(umlClass);
		addRelation(relationship);
		
//		// As there is a relationship it means child is also a Class or Interface, so adding it to the list
//		// of UMLClasses.
//		UMLClass childUMLClass = counselor.getUMLClass(relationship.getChild());
//		counselor.addUMLClass(childUMLClass);
	}
	
	
	/**
	 * @return the relationships
	 */
	public List<Relationship> getRelationships() {
		return relationships;
	}

	/**
	 * @param relationships the relationships to set
	 */
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}

	private void addRelation(Relationship newRelation){
		if(relationships.size() > 0){
			for(Relationship oldRelation : relationships){
				if(oldRelation.getParent().getName().equalsIgnoreCase(newRelation.getParent().getName()) && 
						oldRelation.getChild().getName().equalsIgnoreCase(newRelation.getChild().getName()) 
						&& oldRelation.getType() == newRelation.getType()){
					return;
				}else if(oldRelation.getParent().getName().equalsIgnoreCase(newRelation.getChild().getName()) && 
						oldRelation.getChild().getName().equalsIgnoreCase(newRelation.getParent().getName())
						&& oldRelation.getType() == newRelation.getType()){
					return;
				}
			}
		}
		relationships.add(newRelation);
	}
	
	public void addUMLClass(UMLClass newUMLClass){
		if(!hasUMLClass(newUMLClass)){
			umlClasses.add(newUMLClass);
		}
	}
	
	public List<UMLClass> getUMLClasses(){
		return umlClasses;
	}
	
	public UMLClass getUMLClass(String name){
		for(UMLClass umlClass : umlClasses){
			if(umlClass.getName().equalsIgnoreCase(name)){
				return umlClass;
			}
		}
		UMLClass newUMLClass = new UMLClass();
		newUMLClass.setName(name);
		umlClasses.add(newUMLClass);
		return newUMLClass;
	}
	
	public boolean hasUMLClass(UMLClass newUMLClass){
		for(UMLClass umlClass : umlClasses){
			if(umlClass.getName().equalsIgnoreCase(newUMLClass.getName())){
				return true;
			}
		}
		return false;
	}
}
