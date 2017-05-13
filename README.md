# Java tools to generate UML Class and Sequence diagrams

**About**
There are three applications here:
1. **UML Class diagram generator**
2. **Web application for UML Class diagram generator**
3. **UML Sequence diagram generator**

**Tools and libraries used**
1. **[Javaparser](http://javaparser.org/index.html)**: Easy to understand and use, it gives Abstract Syntax Tree (AST) from java code. Parsed java class can be easily processed to generate the UML diagram

2. **[PlantUML](http://plantuml.com/)**: UML diagrams can be generated using simple and intuitive language used by PlantUML. 

3. **[GraphViz](http://plantuml.com/graphviz-dot)**: Works with PlantUML to generate diagrams
3. **[AspectJ](https://eclipse.org/aspectj/doc/next/progguide/starting.html)**: Using AspectJ to parse the Java code and then create relevant grammar for PlantUML to generate the UML Sequence Diagram.

**How it works**
1. **UML Class diagram generator**: The java files provided either directly or through the ZIP files are parsed using Javaparser for all the variables, methods, constructors, and interfaces. During parsing process, the code also creates the relationships between the classes. All the relationships and classes are stored in objects. Finally, grammar is created using these objects and given to PlantUML to generate the class diagram.

2. **Web application for UML Class diagram generator**: This internally uses the executable JAR generated using the #1. Simple layout created for uploading the ZIP using Bootstrap and Node.js uses Child Process module to start process to execute the JAR file. The JAR file is kept in one of folders of web application. ZIP files are extracted to 'test' folder temporarily and once class diagram is generated it's cleaned. The final diagram is shown on web page.

3. **UML Sequence diagram generator**: Uses AspectJ to understand when a method call is started and when it is ended. Pointcut is added to parse the input Java files. Code works fine for both folder path or ZIP file with java files. The output AspectJ parsing is used to create a grammar for the PlantUML, using this Sequence diagram is generated.


**How to run**
The steps below are after you have downloaded the project and kept the structure as it is. All the libraries are already included in the project. NOTE: You might need to change some paths based on configurations.
*Pre-requisite* 
Java is installed and able to compile java files using terminal.

1. **UML Class diagram generator**: Edit the Makefile and update CLASS_TEST_FOLDER_PATH. Either point to folder with Java files or to path to ZIP file (Example: /Users/rishi/Downloads/cmpe202-master/umlparser/test2.zip). Use command "make generate-class-diagram" or "make execute-class-diagram-jar".  If using Eclipse IDE, then it's staightforward.

2. **Web application for UML Class diagram generator**: To host this application of your own cloud, make sure that cloud environment has Java installed and GraphVIZ installed along with Node.js environment. Or else just to go https://uml-diagram-generator.herokuapp.com/ to run and use existing application to generate class diagrams.

3. **UML Sequence diagram generator**: To run this, you also need install Eclipse IDE AspectJ support and AspectJ compiler (AJC). Once that's done edit the run configurations of "SequenceDiagramGenerator.java" file in Eclipse IDE and point to correct folder with Java files or ZIP file with java files. Run the class as AspectJ/Java Application.


**Development** This project tasks are managed by Kanban board created using [Waffle](https://waffle.io/)

**Possible improvement**
Option for user to select what all things should be included in the final UML Class diagram and flexibility of Sequence diagram generator to generate for any project, not specific one for which is developed.

**Links**
UML Class diagram generator web application: https://uml-diagram-generator.herokuapp.com/
UML Class diagram generator demo video: https://www.youtube.com/watch?v=kghB42D1UaQ
UML Sequence diagram generator demo video: https://www.youtube.com/watch?v=NLxIUonmM24
 



