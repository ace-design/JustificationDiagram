package models;

import export.*;

import java.io.File;
import java.util.*;

public class Node implements Visitable {
    public String alias;
    public String label;
    public Set<Relation> inputs;
    public Set<Relation> outputs;
    public State state;
    
    //information
    public InformationNode informationNode; 
    
    // - log
    public ArrayList<String> logForFiles = new ArrayList<>();
    
    // - constant
    public String constantNoReferences = "!noRef!";
 
    public Node(String alias, String label,ArrayList<String> realizationResult) { 
    	this.label = label;
        this.alias = alias;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
        this.state = State.TODO;  
        
        informationNode = new InformationNode();
    }

    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
        this.state = node.state;
        
        informationNode = new InformationNode();
    }
    

    /** 
     * If the current node have no child; check the realizationList.
     * Verifies that all necessary files are present.
     * Then analyze the children of the current node. If all the children of this one are DONE, it will be DONE, otherwise it will be TODO. 
     * If he has no children, no change will be made.  
     * 
     */
    public void prerequisiteAnalysis(ArrayList<String> labelList) {
  
    	boolean isDone = true;
    	// Use to check that the current node depends on the state of these inputs.
    	// TODO : modifier les commentaires ici
    	if(inputs.isEmpty()) {
    		isDone = realizationListAnalyses(labelList);
    	}
		// use to analyze the state of the child nodes 
    	if(isDone) {
    		isDone = relationAnalyse();

		}
    	else {
    		// use juste for the log
    		relationAnalyse();

    	}
    	// used to verify that the necessary files are present. 
		if(informationNode != null && informationNode.path != null && !informationNode.path.isEmpty()) {
			if(isDone) {
				isDone = checkFileAnalyses();

			}
			else {
				// use juste for the log
				checkFileAnalyses();

	    	}

    	}
		// used to check the number of files in a repertory
		if(informationNode != null && informationNode.pathWithNumber != null&& !informationNode.pathWithNumber.isEmpty()) {
			if(isDone) {
				isDone = CheckFileWithNumberAnalyses();

			}
			else {
				// use juste for the log
				CheckFileWithNumberAnalyses();

	    	}

    	}
    	if(isDone) {
    		this.state = State.DONE;
    	}
    	
    }
    
    /**
     * use to analyze the state of the child nodes 
     * 
     * @return true if all child node are DONE, else return false.
     */
    public boolean relationAnalyse() {
		for (Relation relation : inputs) {
			if(!relation.from.informationNode.optional && relation.from.state.equals(State.TODO)) {
				return false;
			}
		}
    	return true;
    }
    
    /**
     * used to check if the label is contains in 'labelList'
     * @return true if the label is containt in 'labelList'
     */
    public boolean realizationListAnalyses(ArrayList<String> labelList) {
    	
		if(labelList != null && labelList.contains(label)) {
			return true;
		}
		else {
			return false;
		}
    }
    
    /**
     * used to verify that the necessary files are present.
     * 
     * @return true if all files are present, else return false.
     */
    public boolean checkFileAnalyses() {
    	boolean isDone = true;
    	for (String filePath : informationNode.path) {
			if(!new File(filePath).exists()) {
				System.err.println("The file " + filePath + " was not found to validate the node " + label);
				logForFiles.add("[ ] " + filePath + " - (not found)");
				isDone = false;
			}
			else {
				logForFiles.add("[X] " + filePath);

			}
		}
    	return isDone;
    }
    
    /**
     * used to check the number of files in a repertory
     * 
     * @return returns true if the necessary number corresponds to the number of files to be found, else returen false.	
     */
    public boolean CheckFileWithNumberAnalyses() {
    	boolean isDone = true;
    	for (Map.Entry<String,Integer> mapentry : informationNode.pathWithNumber.entrySet()) {
    		String filePath = mapentry.getKey();
    		int currentLenght = 0;
			if(!new File(mapentry.getKey()).exists()) {
				logForFiles.add("[ ] " + filePath  + " - (not found)");
				isDone = false;
			}
			else if((currentLenght = new File(filePath).listFiles().length) != mapentry.getValue()) {
				logForFiles.add("[ ] " +  filePath  + " - (" + mapentry.getValue()+" file expected, but " + currentLenght   + " found)" );
				System.err.println("The repetoire " + filePath + " has " + currentLenght + " files instead of " + mapentry.getValue() + " . The node " + label + " can't be validate");
				isDone = false;
			}
			else {
				logForFiles.add("[x] "+  filePath + " (" + mapentry.getValue() + " Files found)");

			}
		}
    	return isDone;
    }

    public void addInput(Relation input) {
        this.inputs.add(input);
    }

    public void addOutput(Relation output) {
        this.outputs.add(output);
    }

    public void accept(JDVisitor visitor) {
        visitor.visitNode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(alias, node.alias) &&
                Objects.equals(label, node.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, label);
    }
    
    // TODO: faire la javadoc ! 
    /**
	 *
     */
    public void setInformationNode(InformationNode informationNode) {
    	this.informationNode = informationNode;
    }
    
}
