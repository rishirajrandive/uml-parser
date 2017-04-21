import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uml.parser.main.GenerateUML;
import com.uml.parser.main.ParseJava;
/**
 * Main class to start the process. Validates the inputs and start t
 * @author rishi
 *
 */
public class RunUMLParser {

	public static void main(String[] args) {
			
		if(args.length < 2) {
			System.err.println("Invalid arguments, need two arguments!");
			System.out.println("1. Complete folder path to Java files.   2. UML Class diagram output PNG file name");
			System.out.println("Output file will be of .png format so no need to enter extenstion");
			return;
		}
		File folder = new File(args[0]);
		if(folder == null || !folder.isDirectory()) {
			System.out.println("Folder path provided is not valid, please check -> "+ args[0]);
			return;
		}
		
		List<File> files = new ArrayList<>();
		File[] filesInFolder = folder.listFiles();
		for (int i = 0; i < filesInFolder.length; i++) {
			File file = filesInFolder[i];
			if(file.isFile()){
				if(file.getName().endsWith("java") && !file.getName().equalsIgnoreCase("RunUMLParser.java")){
					files.add(file);
				}
			}
		}
		if(files.size() == 0){
			System.out.println("Folder path has no .java files, program works only for Java files. Check and re-run the program");
			return;
		}
		ParseJava obj = new ParseJava();
		obj.parseFiles(files);
		GenerateUML generateUML = new GenerateUML();
		generateUML.createGrammar(args[1]);
	}
}
