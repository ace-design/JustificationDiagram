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
        for (Relation relation : domain.outputs) {
            gv.append("\t{rank = same; ").append(domain.alias).append("; ").append(relation.to.alias).append(";}\n");
        }
    }

    @Override
    public void visitRationale(Rationale rationale) {
        for (Relation relation : rationale.outputs) {
            gv.append("\t").append(relation.to.alias).append(" -> ").append(relation.from.alias).append(" [style=invis];\n");
            gv.append("\t{rank = same; ").append(rationale.alias).append("; ").append(relation.to.alias).append(";}\n");
        }
    }

    @Override
    public void visitSupport(Support support) { }

    @Override
    public void visitRelation(Relation relation) { }
}