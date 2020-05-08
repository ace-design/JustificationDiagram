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
    public void visitDiagram(JustificationDiagram diagram) { }

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

        gv.append("\t{rank = same; ").append(domain.alias).append("; ").append(domain.input).append(";}\n");
    }

    @Override
    public void visitRationale(Rationale rationale) {

    }

    @Override
    public void visitSupport(Support support) {

    }

    @Override
    public void visitRelation(Relation relation) {

    }
}