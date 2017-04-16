# UML Parser

**UML Parser** is an application to generate UML Class diagrams for Java classes. This project tasks are managed by Kanban board created using [Waffle](https://waffle.io/)

Following are the tools/libraries used in this project:

1. **[Javaparser](http://javaparser.org/index.html)**: Easy to understand and use, it gives Abstract Syntax Tree (AST) from java code. Parsed java class can be easily processed to generate the UML diagram

2. **[PlantUML](http://plantuml.com/)**: UML diagrams can be generated using simple and intuitive language used by PlantUML. 

3. **[GraphViz](http://plantuml.com/graphviz-dot)**: Works with PlantUML to generate diagrams
3. **[AspectJ](https://eclipse.org/aspectj/doc/next/progguide/starting.html)**: Using AspectJ to parse the Java code and then create relevant grammar for PlantUML to generate the UML Sequence Diagram.
