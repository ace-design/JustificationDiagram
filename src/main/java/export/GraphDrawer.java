package export;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import justificationDiagram.JustificationDiagram;
import models.*;

import java.io.*;

public class GraphDrawer implements JDVisitor {
    private final StringBuilder gv = new StringBuilder("");

    public void draw(JustificationDiagram diagram, String file) throws IOException {
        this.visitDiagram(diagram);

        InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
        MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(file));
    }

    @Override
    public void visitDiagram(JustificationDiagram diagram) {
        gv.append("digraph G {\n\trankdir = \"BT\"\n");

        for (String alias : diagram.nodes.keySet()) {
            diagram.nodes.get(alias).accept(this);
        }

        for (Relation relation : diagram.relations) {
            relation.accept(this);
        }

        gv.append("}\n");
    }

    @Override
    public void visitNode(Node node) {
        gv.append("\t").append(node.alias).append(" [shape=box, label=").append(node.label).append("];\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
        gv.append("\t").append(conclusion.alias).append(" [shape=box, label=").append(conclusion.label).append("];\n");
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {

    }

    @Override
    public void visitStrategy(Strategy strategy) {
        gv.append("\t").append(strategy.alias).append(" [shape=polygon, sides=4, skew=.4, label=")
                .append(strategy.label).append("];\n");
    }

    @Override
    public void visitDomain(Domain domain) {

    }

    @Override
    public void visitRationale(Rationale rationale) {

    }

    @Override
    public void visitSupport(Support support) {
        gv.append("\t").append(support.alias).append(" [shape=box, label=").append(support.label).append("];\n");
    }

    @Override
    public void visitRelation(Relation relation) {
        gv.append("\t").append(relation.from.alias).append(" -> ").append(relation.to.alias).append(";\n");
    }
}
