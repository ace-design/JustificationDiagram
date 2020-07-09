package models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import command.CommandFactory;
import export.JDVisitor;

public class Node implements Visitable {
	
	private static final Logger logger = LogManager.getLogger(Node.class);
	private String alias;
	private String label;
	private Set<Relation> inputs;
	private Set<Relation> outputs;
	private State state;
    
    //information
	private ActionNode actionNode; 
    
    // - steps
	private List<String> steps = new ArrayList<>();
     
    public Node(String alias, String label) { 
    	this.label = label;
        this.alias = alias;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
        this.state = State.TODO;  
        
        actionNode = new ActionNode();
    }

    
    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
        this.state = node.state;
        
        actionNode = new ActionNode();
    }
    

    /** 
     * If the current node have no child; check the realizationList.
     * Verifies that all necessary files are present.
     * Then analyze the children of the current node. If all the children of this one are DONE, it will be DONE, otherwise it will be TODO. 
     * If he has no children, no change will be made.  
     * 
     */
    public void prerequisiteAnalysis(List<String> labelList) {
  
    	boolean isDone = true;
    	
    	// Use to check that the current node depends on the state of these inputs.
    	if(inputs.isEmpty()) {
    		isDone = realizationListAnalyses(labelList);
    	}
    	
		// use to analyze the state of the child nodes 
    	if(isDone) {
    		isDone = relationAnalyse();
		}

    	// used to verify that the necessary files are present. 
    
    	
		if(actionNode != null && actionNode.getPath() != null && !actionNode.getPath().isEmpty()) {
			boolean areFilesValid = checkFileAnalyses();
			isDone = isDone && areFilesValid;
		}

		// used to check the number of files in a repository
		if(actionNode != null && actionNode.getPathWithNumber()!= null&& !actionNode.getPathWithNumber().isEmpty()) {
			boolean areNumberOfFilesValid = checkFileWithNumberAnalyses();
			isDone = isDone && areNumberOfFilesValid;
		}

		if(actionNode != null && actionNode.getActions() != null && !actionNode.getActions().isEmpty()) {
			boolean areActionsValid = checkAction();
				isDone = isDone && areActionsValid;
		}
		
    	if(isDone) {
    		this.state = State.DONE;
    	}
    }
    
    /**
     * use to analyze the actions of the nodes 
     * @Returns true if actions are valid
     * 
     */
    //TODO : why logs are stored in the node whereas the validity of the node is not stored
    public boolean checkAction() {
    	CommandFactory cf = CommandFactory.getInstance();
    	boolean isDone = false;

    	//TODO : change for a list of Objects ?
		for(String command : actionNode.getActions()) {
			List<String> returnOfExecute = cf.executeCommand(command);
			
			// get the boolean of the execution
			isDone = returnOfExecute.get(0).contains("true");
			
			// get the steps of the execution
			if(!returnOfExecute.get(1).isEmpty()) {
				steps.add(returnOfExecute.get(1));
			}
		}
		return isDone;
    }
    
    /**
     * use to analyze the state of the child nodes 
     * 
     * @return true if all child node are DONE, else return false.
     */
    public boolean relationAnalyse() {
		for (Relation relation : inputs) {
			if(!relation.getFrom().actionNode.isOptional() && relation.getFrom().state.equals(State.TODO)) {
				return false;
			}
		}
    	return true;
    }
    
    /**
     * used to check if the label is contains in 'labelList'
     * @return true if the label is contained in 'labelList'
     */
    public boolean realizationListAnalyses(List<String> labelList) {
    	
		return (labelList != null && labelList.contains(label)) ;
    }
    
    /**
     * used to verify that the necessary files are present.
     * 
     * @return true if all files are present, else return false.
     */
    public boolean checkFileAnalyses() {
    	boolean isDone = true;
    	for (String filePath : actionNode.getPath()) {
			if(!new File(filePath).exists()) {
				String logMsg = String.format("The file %s was not found to validate the node %s ", filePath, label);
				logger.error(logMsg);
				steps.add("[ ] " + filePath + " (not found)");
				isDone = false;
			}
			else {
				steps.add("[X] " + filePath);

			}
		}
    	return isDone;
    }
    
    /**
     * used to check the number of files in a repertory
     * 
     * @return returns true if the necessary number corresponds to the number of files to be found, else returen false.	
     */
    public boolean checkFileWithNumberAnalyses() {
    	boolean isDone = true;
    	for (Map.Entry<String,Integer> mapentry : actionNode.getPathWithNumber().entrySet()) {
    		String filePath = mapentry.getKey();
    		int currentLenght = 0;
			if(!new File(mapentry.getKey()).exists()) {
				steps.add("[ ] " + filePath  + " (not found)");
				isDone = false;
			}
			else if((currentLenght = new File(filePath).listFiles().length) != mapentry.getValue()) {
				steps.add("[ ] " +  filePath  + " (" + mapentry.getValue()+" files expected, but " + currentLenght   + " found)" );
				logger.error("The directory %s has %d files instead of %s. The node %s can't be validate", filePath,currentLenght,  mapentry.getValue(),label);
				isDone = false;
			}
			else {
				steps.add("[x] "+  filePath + " (" + mapentry.getValue() + " Files found)");

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
    
    public void setActionNode(ActionNode actionNode) {
    	this.actionNode = actionNode;
    }

    // Getter and Setter
    
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<Relation> getInputs() {
		return inputs;
	}

	public void setInputs(Set<Relation> inputs) {
		this.inputs = inputs;
	}

	public Set<Relation> getOutputs() {
		return outputs;
	}

	public void setOutputs(Set<Relation> outputs) {
		this.outputs = outputs;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public ActionNode getActionNode() {
		return actionNode;
	}
    
    
    
}
