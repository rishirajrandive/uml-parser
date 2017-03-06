package com.uml.parser.main;


import java.util.List;

import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;

public class UMLHelper {

	public static boolean isUMLClassType(Type type){
		if(type instanceof PrimitiveType){
			return false;
		}else if(type instanceof ReferenceType){
			ClassOrInterfaceType actualType = (ClassOrInterfaceType)((ReferenceType) type).getType();
			String typeStr = actualType.toString();
			if(typeStr.equalsIgnoreCase("Double") || typeStr.equalsIgnoreCase("Float") || 
					typeStr.equalsIgnoreCase("Long") || typeStr.equalsIgnoreCase("Integer") || 
					typeStr.equalsIgnoreCase("Short") || typeStr.equalsIgnoreCase("Character") ||
			        typeStr.equalsIgnoreCase("Byte") || typeStr.equalsIgnoreCase("Boolean") || 
			        typeStr.equalsIgnoreCase("String")){
				return false;
			}else if(typeStr.equalsIgnoreCase("ArrayList") || typeStr.equalsIgnoreCase("Collection")){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isUMLClassArray(Type type){
		if((type.toString().contains("[") || type.toString().contains("<")) && isUMLClassType(type)){
			return true;
		}
		return false;
	}
	
	public static String getArrayClassName(Type type){
		if(type.toString().contains("[")){
			return getElementName(type.toString());
		}else if(type.toString().contains("<")){
			ReferenceType collectionType = (ReferenceType) type;
			ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType) collectionType.getType();
			if(classOrInterfaceType.getTypeArgs() != null){
				List<Type> args = classOrInterfaceType.getTypeArgs();
				for(Type arg : args){
					if(isUMLClassType(arg)){
						return arg.toString();
					}
				}
			}
		}
		return null;
	}
	
	public static String getElementName(String typeStr){
		if(typeStr.contains("[")){
			System.out.println("Found array []");
			typeStr = typeStr.split("\\[")[0];
		}else if(typeStr.contains("<")){
			System.out.println("Found array <>");
			typeStr = typeStr.split("<")[0];
		}
		return typeStr;
	}
}
