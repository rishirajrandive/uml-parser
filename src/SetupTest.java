
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

public class SetupTest {

	public static void main(String[] args) {
		try{
			String fileName = "/Users/rishi/Documents/workspace/UMLParser/src/test.java";
			File file = new File(fileName);
			System.out.println("File path "+ file.getAbsolutePath());
			FileInputStream in = new FileInputStream(file.getAbsolutePath());
			// parse the file
			CompilationUnit cu = JavaParser.parse(in);
			// prints the resulting compilation unit to default system output
			System.out.println(cu.toString());
		}catch(FileNotFoundException ex){
			System.err.println("File not found "+ ex.getStackTrace());
		}catch (ParseException e) {
			System.err.println("Parsing exception "+ e.getStackTrace());
		}
	}
}
