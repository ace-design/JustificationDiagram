import nodes.Node;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JustificationDiagram {
    ArrayList<Node> nodes;
    ArrayList<Relation> relations;

    public JustificationDiagram() {
        nodes = new ArrayList<>();
        relations = new ArrayList<>();
    }

    public void createGraph(String file) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(file));
        out.print(this.toGv());
        out.close();
    }

    public StringBuilder toGv() {
        StringBuilder gv = new StringBuilder("digraph G {\n\trankdir = \"BT\"\n");

        for (Node node : nodes) {
            gv.append(node.toGv());
        }

        for (Relation relation : relations) {
            gv.append(relation.toGv());
        }
        return gv.append("}\n");
    }
}
