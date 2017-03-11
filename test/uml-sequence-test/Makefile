all: clean

clean: 
	find . -name "*.class" -exec rm -rf {} \;
	
compile:
	javac -cp . *.java

run: compile
	java -cp . Main


	