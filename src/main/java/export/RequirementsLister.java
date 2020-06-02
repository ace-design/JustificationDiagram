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
    	String stringToWrite = setLabel(node);

        list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
    	setModelUsed(conclusion);
    	String stringToWrite = setLabel(conclusion);

        list.append("-----------------------------------------------\n" + modelUsed + "\t")
                .append(stringToWrite, 1, stringToWrite.length() - 1)
                .append("\n-----------------------------------------------");
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
    	setModelUsed(subConclusion);
    	String stringToWrite = setLabel(subConclusion);
        list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
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
    	String stringToWrite = setLabel(support);
        list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
    }

    @Override
    public void visitRelation(Relation relation) { }
    
    
    /**
     * Analyse the state of the node and chose the corresponding model to used   
     * 
     * @param node current node to analyse.
     */
    public void setModelUsed(Node node) {
    	if (node.state.equals(State.DONE)) {
    		modelUsed = model[1];

    	}
    	else {
    		modelUsed = model[0];
    	}
    }
    
    public String setLabel(Node node) {
    	if(node.references != null && !node.references.contains("!noRef!") && !node.references.equals(" ")) {
    		return node.label.substring(0,node.label.length()-1) + " - references : " + node.references + "\"";
    	}
    	else {
    		return node.label;
    	}
    	
    }
 }
