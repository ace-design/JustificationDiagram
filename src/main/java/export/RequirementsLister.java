package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class RequirementsLister implements JDVisitor {
    private final StringBuilder list = new StringBuilder();

    public StringBuilder generate(JustificationDiagram diagram) {
        this.visitDiagram(diagram);
        return list;
    }

    @Override
    public void visitDiagram(JustificationDiagram diagram) {
        list.append("Requirements list\n\n");
    }

    @Override
    public void visitNode(Node node) {

    }

    @Override
    public void visitConclusion(Conclusion conclusion) {

    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {

    }

    @Override
    public void visitStrategy(Strategy strategy) {

    }

    @Override
    public void visitDomain(Domain domain) {

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
