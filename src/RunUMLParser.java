import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.uml.parser.main.GenerateUML;
import com.uml.parser.main.ParseJava;

public class RunUMLParser {

	public static void main(String[] args) {
			
		if(args.length < 2) {
			System.err.println("Invalid arguments, please read instructions and try again!");
			System.out.println("Replace the params in bracket with values and use: java -jar <jarname.jar> <classpath> <outputfilename>");
			System.out.println("Output file will be of .png format so no need to enter extenstion");
		}
		File folder = new File(args[0]);
		if(folder == null || !folder.isDirectory()) {
			System.out.println("Seems path is not correct, please check: "+ args[0]);
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
		
		ParseJava obj = new ParseJava();
		obj.parseFiles(files);
		GenerateUML generateUML = new GenerateUML();
		generateUML.generateUML(args[1]);
	}
}
