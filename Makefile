##
# Makefile for UML Parser.
# Generates UML Class diagram PNG file with name given and for folder path specified.
#
# NOTE: Make sure Java is installed and able to run "javac" on your machine
##

CDG_BASE_DIR = class-diagram-gen/src/
CDG_BIN_DIR = class-diagram-gen/bin/
CDG_LIB_DIR = class-diagram-gen/lib/*


CDG_MAIN = $(CDG_BASE_DIR)com/uml/parser/main/*.java
CDG_ENUMS = $(CDG_BASE_DIR)com/uml/parser/enums/*.java
CDG_MODEL = $(CDG_BASE_DIR)com/uml/parser/model/*.java

# Main class which runs the program
CDG_RUN = RunUMLParser
# Complete folder path for the Java files for which UML class diagram is required
TEST_FOLDER_PATH = /Users/rishi/Downloads/cmpe202-master/umlparser/uml-parser-test-5
# UML Class diagram output PNG file name
OUTPUT_FILE_NAME = umloutput


all: clean

clean:
	find . -name "*.class" -exec rm -rf {} \;

generate-class-diagram:
	###### Compiling classes required for UML Class diagram generator ######
	javac -d $(CDG_BIN_DIR) -cp .:$(CDG_LIB_DIR) $(CDG_ENUMS)
	javac -d $(CDG_BIN_DIR) -cp .:$(CDG_BIN_DIR):$(CDG_LIB_DIR) $(CDG_MODEL)
	javac -d $(CDG_BIN_DIR) -cp .:$(CDG_BIN_DIR):$(CDG_LIB_DIR) $(CDG_MAIN)
	javac -d $(CDG_BIN_DIR) -cp .:$(CDG_BIN_DIR):$(CDG_LIB_DIR) $(CDG_BASE_DIR)*.java
	##### Running UML Class diagram generator ######
	java -cp .:$(CDG_BIN_DIR):$(CDG_LIB_DIR) $(CDG_RUN) $(TEST_FOLDER_PATH) $(OUTPUT_FILE_NAME)
