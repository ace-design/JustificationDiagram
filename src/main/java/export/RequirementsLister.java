package export;


import justificationDiagram.JustificationDiagram;
import models.*;

public class RequirementsLister implements JDVisitor {
    StringBuilder list;
    
    // model to use according to the state of the node (todo or done)
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
    	String stringToWrite = setStringToWrite(node);

        list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
        setLogForFiles(node);
    }

    @Override
    public void visitConclusion(Conclusion conclusion) {
    	setModelUsed(conclusion);
    	String stringToWrite = setStringToWrite(conclusion);

        list.append("-----------------------------------------------\n" + modelUsed + "\t")
                .append(stringToWrite, 1, stringToWrite.length() - 1);
        setLogForFiles(conclusion);
        list.append("\n-----------------------------------------------");
    }

    @Override
    public void visitSubConclusion(SubConclusion subConclusion) {
    	setModelUsed(subConclusion);
    	String stringToWrite = setStringToWrite(subConclusion);
        list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
        setLogForFiles(subConclusion);

    }

    @Override
    public void visitStrategy(Strategy strategy) {
    	if(!strategy.getSteps().isEmpty()) {
    		setModelUsed(strategy);
        	String stringToWrite = setStringToWrite(strategy);

            list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
            setLogForFiles(strategy);
    	}
    }

    @Override
    public void visitDomain(Domain domain) {
    	if(!domain.getSteps().isEmpty()) {
    		setModelUsed(domain);
        	String stringToWrite = setStringToWrite(domain);

            list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
            setLogForFiles(domain);
    	}

    }

    @Override
    public void visitRationale(Rationale rationale) {
    	if(!rationale.getSteps().isEmpty()) {
    		setModelUsed(rationale);
        	String stringToWrite = setStringToWrite(rationale);

            list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
            setLogForFiles(rationale);
    	}

    }

    @Override
    public void visitSupport(Support support) {
    	setModelUsed(support);
    	String stringToWrite = setStringToWrite(support);
        list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
        setLogForFiles(support);

    }

    @Override
    public void visitRelation(Relation relation) { }
    
     
    /**
     * Analyse the state of the node and chose the corresponding model to used   
     * 
     * @param node current node to analyse.
     */
    public void setModelUsed(Node node) {
    	if (node.getState().equals(State.DONE)) {
    		modelUsed = model[1];

    	}
    	else {
    		modelUsed = model[0];
    	}
    }
    
    /**
     * Checks if the current node has a reference and if it is optional. 
     * If this is the case, return "'label' - references: 'ref' (optional)". 
     * If there is only the reference, return "'label'" - references: 'ref'".
     * If there is no reference and the node is optional, return "'label' (optional)".
     * Otherwise, return "label".
     * 
     * @param node to check
     * @return string to write in the todo file.
     */
    public String setStringToWrite(Node node) {
    	if(node.getInformationNode().reference != null && !node.getInformationNode().reference.contains("!noRef!") && !node.getInformationNode().reference.equals(" ")) {
    		if(node.getInformationNode().optional) {
    			return node.getLabel().substring(0,node.getLabel().length()-1) + " - reference : " + node.getInformationNode().reference + " (optional) \"";
    		}
    		else  {
    			return node.getLabel().substring(0,node.getLabel().length()-1) + " - reference : " + node.getInformationNode().reference + "\"";
    		}
    		
    	}
    	else {
    		if(node.getInformationNode().optional) {
    			return node.getLabel().substring(0,node.getLabel().length()-1)  + " (optional) \"";
    		}
    		else  {
        		return node.getLabel();
    		}
    	}
    	 
    }
    
    /**
     * Take the 'log' of the current node and write the log of the files in the 'list' (if they have an error or not)
     * 
     * @param node to check
     */
    public void setLogForFiles(Node node) {
    	if(!node.getSteps().isEmpty()) {
    		for (String step : node.getSteps()) {
        		list.append("\t").append(step, 0, step.length()).append("\n");
    		}
    	}
    	
    }
 }
