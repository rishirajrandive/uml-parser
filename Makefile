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
CLASS_TEST_FOLDER_PATH = /Users/rishi/Downloads/cmpe202-master/umlparser/test2.zip
# UML Class diagram output PNG file name
CLASS_OUTPUT_FILE_NAME = umloutput


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
	java -cp .:$(CDG_BIN_DIR):$(CDG_LIB_DIR) $(CDG_RUN) $(CLASS_TEST_FOLDER_PATH) $(CLASS_TEST_FOLDER_PATH)

execute-class-diagram-jar:
	java -jar UMLClassDiagramGen.jar $(CLASS_TEST_FOLDER_PATH) $(CLASS_TEST_FOLDER_PATH)



# ######################################################################################
# SDG_BASE_DIR = sequence-diagram-gen/src/
# SDG_BIN_DIR = sequence-diagram-gen/bin/
# SDG_LIB_DIR = sequence-diagram-gen/lib/*
#
# SDG_MAIN = $(SDG_BASE_DIR)*.java
# SDG_AJ = $(SDG_BASE_DIR)*.aj
#
# # Main class which runs the program
# SDG_RUN = SequenceDiagramGenerator
# # Complete folder path for the Java files for which UML sequence diagram is required
# SEQ_TEST_FOLDER_PATH = /Users/rishi/Downloads/cmpe202-master/umlparser/sequence.zip
# # UML Sequence diagram output PNG file name
# SEQ_OUTPUT_FILE_NAME = sequence-diagram
#
# generate-sequence-diagram:
# 	###### Compiling classes required for UML Sequence diagram generator ######
# 	javac -d $(SDG_BIN_DIR) -cp .:$(SDG_LIB_DIR) $(SDG_MAIN)
# 	##### Running UML Sequence diagram generator ######
# 	java -cp .:$(SDG_BIN_DIR):$(SDG_LIB_DIR) $(SDG_RUN) $(SEQ_TEST_FOLDER_PATH) $(SEQ_OUTPUT_FILE_NAME)
#
# execute-sequence-diagram-jar:
# 	java -jar UMLClassDiagramGen.jar $(SEQ_TEST_FOLDER_PATH) $(SEQ_OUTPUT_FILE_NAME)
