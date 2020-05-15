package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class RequirementsLister implements JDVisitor {
    StringBuilder list;

    public StringBuilder generate(JustificationDiagram diagram) {
        TopologicalSort sort = new TopologicalSort(diagram);
        diagram.accept(this);

        for (Node node : sort.order) {
            node.accept(this);
        }
        return list;
    }

    @Override
    public void visitDiagram(JustificationDiagram diagram) {
        list = new StringBuilder("Requirements list\n\n");
    }

    @Override
    public void visitNode(Node node) {
        list.append("[ ]\t").append(node.label, 1, node.label.length() - 1).append("\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
        list.append("-----------------------------------------------\n[ ]\t")
                .append(conclusion.label, 1, conclusion.label.length() - 1)
                .append("\n-----------------------------------------------");
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
        list.append("[ ]\t").append(subConclusion.label, 1, subConclusion.label.length() - 1).append("\n");
    }

    @Override
    public void visitStrategy(Strategy strategy) { }

    @Override
    public void visitDomain(Domain domain) { }

    @Override
    public void visitRationale(Rationale rationale) { }

    @Override
    public void visitSupport(Support support) {
        list.append("[ ]\t").append(support.label, 1, support.label.length() - 1).append("\n");
    }

    @Override
    public void visitRelation(Relation relation) { }
}
