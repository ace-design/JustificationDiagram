package export;

import justificationDiagram.JustificationDiagram;
import models.*;

public class RequirementsLister implements JDVisitor {
    StringBuilder list;
    
    // model in case of the evidences are todo or done
    String model[] = {"[ ]\t","[X]\t"}; 
    String modelUsed = model[0]; 
    
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
    	setModelUsed(node.state);
        list.append(modelUsed).append(node.label, 1, node.label.length() - 1).append("\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
        list.append("-----------------------------------------------\n[ ]\t")
                .append(conclusion.label, 1, conclusion.label.length() - 1)
                .append("\n-----------------------------------------------");
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
    	setModelUsed(subConclusion.state);
        list.append(modelUsed).append(subConclusion.label, 1, subConclusion.label.length() - 1).append("\n");
    }

    @Override
    public void visitStrategy(Strategy strategy) { }

    @Override
    public void visitDomain(Domain domain) { }

    @Override
    public void visitRationale(Rationale rationale) { }

    @Override
    public void visitSupport(Support support) {
    	setModelUsed(support.state);
        list.append(modelUsed).append(support.label, 1, support.label.length() - 1).append("\n");
    }

    @Override
    public void visitRelation(Relation relation) { }
    
    
    /**
     * Analyse the state of the node and chose the corresponding model to used 
     * 
     * @param state state of the current node.
     */
    public void setModelUsed(State state) {
    	if (state.equals(State.DONE)) {
    		modelUsed = model[1];
    	}
    	else {
    		modelUsed = model[0];
    	}
    }
}
