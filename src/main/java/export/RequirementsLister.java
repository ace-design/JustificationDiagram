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
    	if(!strategy.logForFiles.isEmpty()) {
    		setModelUsed(strategy);
        	String stringToWrite = setStringToWrite(strategy);

            list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
            setLogForFiles(strategy);
    	}
    }

    @Override
    public void visitDomain(Domain domain) {
    	if(!domain.logForFiles.isEmpty()) {
    		setModelUsed(domain);
        	String stringToWrite = setStringToWrite(domain);

            list.append(modelUsed).append(stringToWrite, 1, stringToWrite.length() - 1).append("\n");
            setLogForFiles(domain);
    	}

    }

    @Override
    public void visitRationale(Rationale rationale) {
    	if(!rationale.logForFiles.isEmpty()) {
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
    	if (node.state.equals(State.DONE)) {
    		modelUsed = model[1];

    	}
    	else {
    		modelUsed = model[0];
    	}
    }
    
    /**
     * Checks if the current node has a reference. 
     * If it does, return "'label' - references : 'ref'", otherwise return the 'label'.
     * 
     * @param node to check
     * @return string to write in the todo file.
     */
    public String setStringToWrite(Node node) {
    	if(node.references != null && !node.references.contains("!noRef!") && !node.references.equals(" ")) {
    		return node.label.substring(0,node.label.length()-1) + " - references : " + node.references + "\"";
    	}
    	else {
    		return node.label;
    	}
    	 
    }
    
    /**
     * Take the 'logForFiles' of the current node and write the log of the files in the 'list' (if they have an error or not)
     * 
     * @param node to check
     */
    public void setLogForFiles(Node node) {
    	if(!node.logForFiles.isEmpty()) {
    		for (String log : node.logForFiles) {
        		list.append("\t").append(log, 0, log.length()).append("\n");
    		}
    	}
    	
    }
 }
