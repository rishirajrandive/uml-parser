import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SequenceDiagramGenerator {

	private static final int BUFFER_SIZE = 4096;

	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Invalid arguments, need two arguments!");
			System.out.println("1. Complete folder path to Java files.");
			System.out.println("Output file will be of .png format so no need to enter extenstion");
			return;
		}
		SequenceDiagramGenerator obj = new SequenceDiagramGenerator();
		obj.startProcess(args[0]);
		obj.clearTestFolder();
	}

	/**
	 * Starts the process of UML class diagram generation
	 * 
	 * @param inputPath
	 * @param outputFileName
	 */
	private void startProcess(String inputPath) {
		if (inputPath.indexOf(".zip") != -1) {
			try {
				unzipAndProcess(inputPath);

			} catch (IOException e) {
				System.out.println("Failed to unzip the folder. \nNote: Keep the Java files in root folder of zip:\n"
						+ e.getMessage());
			}
		} else {
			File folder = new File(inputPath);
			if (folder == null || !folder.isDirectory()) {
				System.out.println("Folder path provided is not valid, please check -> " + inputPath);
				return;
			}
			processFiles(getFileListFromFolder(folder));
		}
	}

	/**
	 * Returns list of files from the folder
	 * 
	 * @param folder
	 * @return
	 */
	private List<File> getFileListFromFolder(File folder) {
		List<File> files = new ArrayList<>();
		File[] filesInFolder = folder.listFiles();
		for (int i = 0; filesInFolder != null && i < filesInFolder.length; i++) {
			File file = filesInFolder[i];
			if (isValidFile(file)) {
				files.add(file);
			}
		}
		return files;
	}

	/**
	 * Returns if the file is valid Java file or not
	 * 
	 * @param file
	 * @return
	 */
	private boolean isValidFile(File file) {
		if (file.isFile()) {
			if (file.getName().endsWith("java") && !file.getName().equalsIgnoreCase("RunUMLParser.java")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Actual process on list of collected java file starts
	 * 
	 * @param files
	 * @param outputFileName
	 */
	private void processFiles(List<File> files) {
		if (files.size() == 0) {
			System.out.println(
					"Folder path has no .java files, program works only for Java files. Check and re-run the program\n"
							+ "If you are using Zip file make sure the Java files are in home directory of Zip file");
			return;
		}
		
		try {
            Process processCompile = Runtime.getRuntime().exec("/Users/rishi/aspectj1.8/bin/ajc -1.8 -cp .:lib/aspectjrt.jar:lib/plantuml.jar test-case/*.java test-case/*.aj");
            printOutput(" Error:", processCompile.getErrorStream());
            Process processRun = Runtime.getRuntime().exec("java -cp .:lib/aspectjrt.jar:lib/plantuml.jar:test-case/ Main");
            printOutput(" Output:", processRun.getInputStream());
            printOutput(" Error:", processRun.getErrorStream());
        } catch (IOException e) {
            System.out.println("Error generating UML Sequence diagram: "+ e.getMessage());
        }
	}

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified
	 * by destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	public void unzipAndProcess(String zipFilePath) throws IOException {
		String destDirectory = "test-case";
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
        	destDir.mkdir();
        }
        
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            }else {
            	// if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        
        File file = new File(destDir.getAbsolutePath());
        processFiles(getFileListFromFolder(file));
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	/**
	 * 
	 */
	private void clearTestFolder() {
		String destDirectory = "test-case";
		File destDir = new File(destDirectory);
		if (destDir.exists()) {
			for (File file : destDir.listFiles()) {
				if(!file.getName().equals("SequenceGen.aj"))
					file.delete();
			}
		}
	}
	
	private void printOutput(String name, InputStream ins) throws IOException {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
        in.close();
      }

}
