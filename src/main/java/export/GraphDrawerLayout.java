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
        for (String alias : diagram.getNodes().keySet()) {
            diagram.getNodes().get(alias).accept(this);
        }
        for (Relation relation : diagram.getRelations()) {
            relation.accept(this);
        }
    }

    @Override
    public void visitNode(Node node) {
    	//Do nothing because Node is too general
    }

    @Override
    public void visitConclusion(Conclusion conclusion) { 
    	//Do nothing because of ...
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) { 
    	//Do nothing because of ...
    }

    @Override
    public void visitStrategy(Strategy strategy) { 
    	//Do nothing because of ...
    } 

    @Override
    public void visitDomain(Domain domain) {
        for (Relation relation : domain.getOutputs()) {
            gv.append("\t{rank = same; ").append(domain.getAlias()).append("; ").append(relation.getTo().getAlias()).append(";}\n");
        }
    }

    @Override
    public void visitRationale(Rationale rationale) {
        for (Relation relation : rationale.getOutputs()) {
            gv.append("\t").append(relation.getTo().getAlias()).append(" -> ").append(relation.getFrom().getAlias()).append(" [style=invis];\n");
            gv.append("\t{rank = same; ").append(rationale.getAlias()).append("; ").append(relation.getTo().getAlias()).append(";}\n");
        }
    }

    @Override
    public void visitSupport(Support support) {
    	//Do nothing because of ...
    }

    @Override
    public void visitRelation(Relation relation) { 
    	//Do nothing because of ...
    }
}