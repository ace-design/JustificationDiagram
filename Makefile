.DEFAULT_GOAL := all
.PHONY: clean

PLANTUML_JAR_URL = https://sourceforge.net/projects/plantuml/files/plantuml.jar/download

all : run models

target :
	mvn compile

graph.gv : target
	mvn exec:java -Dexec.mainClass="JDCompiler"

graph.png : graph.gv
	dot -Tpng graph.gv -o graph.png

run : graph.png

plantuml.jar:
	curl -sSfL $(PLANTUML_JAR_URL) -o plantuml.jar

%.png : plantuml.jar
	java -jar plantuml.jar "models/*.puml"

models : %.png

clean :
	rm -rf *.gv *.png models/*.png
	mvn clean
