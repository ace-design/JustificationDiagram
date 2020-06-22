package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class GraphDrawerLayout implements JDVisitor {
    private final StringBuilder gv = new StringBuilder();

    public StringBuilder draw(JustificationDiagram diagram) {
        this.visitDiagram(diagram);
        return gv;
    }

    @Override
    public void visitDiagram(JustificationDiagram diagram) {
        for (String alias : diagram.nodes.keySet()) {
            diagram.nodes.get(alias).accept(this);
        }
        for (Relation relation : diagram.relations) {
            relation.accept(this);
        }
    }

    @Override
    public void visitNode(Node node) { }

    @Override
    public void visitConclusion(Conclusion conclusion) { }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) { }

    @Override
    public void visitStrategy(Strategy strategy) { } 

    @Override
    public void visitDomain(Domain domain) {
        for (Relation relation : domain.getOutputs()) {
            gv.append("\t{rank = same; ").append(domain.getAlias()).append("; ").append(relation.to.getAlias()).append(";}\n");
        }
    }

    @Override
    public void visitRationale(Rationale rationale) {
        for (Relation relation : rationale.getOutputs()) {
            gv.append("\t").append(relation.to.getAlias()).append(" -> ").append(relation.from.getAlias()).append(" [style=invis];\n");
            gv.append("\t{rank = same; ").append(rationale.getAlias()).append("; ").append(relation.to.getAlias()).append(";}\n");
        }
    }

    @Override
    public void visitSupport(Support support) { }

    @Override
    public void visitRelation(Relation relation) { }
}