.PHONY: clean

target :
	mvn compile

graph.gv : target
	mvn exec:java -Dexec.mainClass="Main"

graph.png : graph.gv
	dot -Tpng graph.gv -o graph.png

run : graph.png

clean :
	rm -rf *.gv *.png
	mvn clean
