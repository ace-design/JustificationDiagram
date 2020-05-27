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
    	setModelUsed(node);
        list.append(modelUsed).append(node.label, 1, node.label.length() - 1).append("\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
    	setModelUsed(conclusion);
        list.append("-----------------------------------------------\n" + modelUsed + "\t")
                .append(conclusion.label, 1, conclusion.label.length() - 1)
                .append("\n-----------------------------------------------");
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
    	setModelUsed(subConclusion);
        list.append(modelUsed).append(subConclusion.label, 1, subConclusion.label.length() - 1).append("\n");
    }

    @Override
    public void visitStrategy(Strategy strategy) {
    	setModelUsed(strategy);
    }

    @Override
    public void visitDomain(Domain domain) {
    	setModelUsed(domain);
    }

    @Override
    public void visitRationale(Rationale rationale) {
    	setModelUsed(rationale);
    }

    @Override
    public void visitSupport(Support support) {
    	setModelUsed(support);
        list.append(modelUsed).append(support.label, 1, support.label.length() - 1).append("\n");
    }

    @Override
    public void visitRelation(Relation relation) { }
    
    
    /**
     * Change the state of the node in fonction of there childrens, analyse the state of the node and chose the corresponding model to used   
     * 
     * @param node current node to analyse.
     */
    public void setModelUsed(Node node) {
    	node.analyseRelation(node);
    	if (node.state.equals(State.DONE)) {
    		modelUsed = model[1];

    	}
    	else {
    		modelUsed = model[0];
    	}
    }
}
