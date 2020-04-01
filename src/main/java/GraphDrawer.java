import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphDrawer implements JDVisitor {
    private StringBuilder graph = new StringBuilder("");

    public void draw(JustificationDiagram diagram, String file) throws IOException {
        this.visitDiagram(diagram);

        PrintWriter out = new PrintWriter(new FileWriter(file));
        out.print(graph);
        out.close();
    }

    @Override
    public void visitDiagram(JustificationDiagram diagram) {
        graph.append(new StringBuilder("digraph G {\n\trankdir = \"BT\"\n"));

        for (Node node : diagram.nodes) {
            node.accept(this);
        }

        for (Relation relation : diagram.relations) {
            relation.accept(this);
        }

        graph.append("}\n");
    }

    @Override
    public void visitNode(Node node) {
        graph.append(new StringBuilder("\t" + node.alias + " [shape=box, label=" + node.label + "];\n"));
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
        graph.append(new StringBuilder("\t" + conclusion.alias + " [shape=box, label=" + conclusion.label + "];\n"));
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {

    }

    @Override
    public void visitStrategy(Strategy strategy) {
        graph.append(new StringBuilder("\t" + strategy.alias + " [shape=polygon, sides=4, skew=.4, label="
                + strategy.label + "];\n"));
    }

    @Override
    public void visitDomain(Domain domain) {

    }

    @Override
    public void visitRationale(Rationale rationale) {

    }

    @Override
    public void visitSupport(Support support) {
        graph.append(new StringBuilder("\t" + support.alias + " [shape=box, label=" + support.label + "];\n"));
    }

    @Override
    public void visitRelation(Relation relation) {
        graph.append(new StringBuilder("\t" + relation.from + " -> " + relation.to + ";\n"));
    }
}
