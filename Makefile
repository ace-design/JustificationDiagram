.PHONY: clean

ANTLR4 := java -Xmx500M -cp "/usr/local/lib/antlr-4.7.1-complete.jar" org.antlr.v4.Tool
GRUN := java -Xmx500M -cp "/usr/local/lib/antlr-4.7.1-complete.jar" org.antlr.v4.gui.TestRig

antlr : src/main/antlr4/PlantUML.g4
	$(ANTLR4) -o src/main/java/plantUml/ -package plantUml -visitor PlantUML.g4
	javac -classpath .:/usr/local/lib/antlr-4.7.1-complete.jar src/main/java/plantUml/*.java

target : antlr
	mvn compile

graph.gv : target
	java -classpath ./target/classes/:/usr/local/lib/antlr-4.7.1-complete.jar Main

graph.png : graph.gv
	dot -Tpng graph.gv -o graph.png

run : graph.png

clean :
	rm -rf src/main/java/plantUml/* *.gv *.png
	mvn clean
