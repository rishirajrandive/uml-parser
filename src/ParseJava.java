import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.TypeDeclaration;

public class ParseJava {
	
	public static StringBuilder parseFiles(File[] files, String dir){
		FileInputStream inputStream = null;
		try{
			for(File file : files){
				inputStream = new FileInputStream(dir+ "/" + file.getName());
				CompilationUnit compliationUnit = JavaParser.parse(file);
				
				List<ImportDeclaration> imports = compliationUnit.getImports();
				for(ImportDeclaration im : imports){
					System.out.println("Name "+ im.getName());
					System.out.println("Asterik "+ im.isAsterisk());
					System.out.println("Static "+ im.isStatic());
				}
				
				List<TypeDeclaration> types = compliationUnit.getTypes();
				for(TypeDeclaration type : types){
					if(type instanceof ClassOrInterfaceDeclaration){
						if(((ClassOrInterfaceDeclaration) type).isInterface()){
							
						}
						
					}else if(type instanceof EnumDeclaration){
						
					}
				}
			}
		}catch(FileNotFoundException ex){
			
		}catch(IOException ex){
			
		}catch(ParseException ex){
			
		}
		
		
		return null;
	}

}
