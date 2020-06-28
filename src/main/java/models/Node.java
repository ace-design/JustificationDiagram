package models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import command.CommandFactory;
import export.JDVisitor;

public class Node implements Visitable {
	
	private String alias;
	private String label;
	private Set<Relation> inputs;
	private Set<Relation> outputs;
	private State state;
    
    //information
	private ActionNode actionNode; 
    
    // - steps
	private ArrayList<String> steps = new ArrayList<>();
     
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
    public void prerequisiteAnalysis(ArrayList<String> labelList) {
  
    	boolean isDone = true;
    	// Use to check that the current node depends on the state of these inputs.
    	if(inputs.isEmpty()) {
    		isDone = realizationListAnalyses(labelList);
    	}
		// use to analyze the state of the child nodes 
    	if(isDone) {
    		isDone = relationAnalyse();

		}
    	else {
			// used just for the steps
    		relationAnalyse();

    	}
    	// used to verify that the necessary files are present. 
		if(actionNode != null && actionNode.path != null && !actionNode.path.isEmpty()) {
			if(isDone) {
				isDone = checkFileAnalyses();

			}
			else {
				// used just for the steps
				checkFileAnalyses();

	    	}

    	}
		// used to check the number of files in a repertory
		if(actionNode != null && actionNode.pathWithNumber != null&& !actionNode.pathWithNumber.isEmpty()) {
			if(isDone) {
				isDone = CheckFileWithNumberAnalyses();

			}
			else {
				// used just for the steps
				CheckFileWithNumberAnalyses();

	    	}

    	}
		if(actionNode != null && actionNode.action != null && !actionNode.action.isEmpty()) {
			if(isDone) {
				isDone = checkAction();

			}
			else {
				// used just for the steps
				checkAction();
	    	}
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
		for(String command : actionNode.action) {
			cf.create();
			ArrayList<String> returnOfExecute = cf.executeCommand(command);
			
			// get the boolean of the execution
			if(returnOfExecute.get(0).contains("true")) {
				isDone = true;
			}
			else  {
				isDone = false;
			}
			
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
			if(!relation.from.actionNode.optional && relation.from.state.equals(State.TODO)) {
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
    	for (String filePath : actionNode.path) {
			if(!new File(filePath).exists()) {
				System.err.println("The file " + filePath + " was not found to validate the node " + label);
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
    public boolean CheckFileWithNumberAnalyses() {
    	boolean isDone = true;
    	for (Map.Entry<String,Integer> mapentry : actionNode.pathWithNumber.entrySet()) {
    		String filePath = mapentry.getKey();
    		int currentLenght = 0;
			if(!new File(mapentry.getKey()).exists()) {
				steps.add("[ ] " + filePath  + " (not found)");
				isDone = false;
			}
			else if((currentLenght = new File(filePath).listFiles().length) != mapentry.getValue()) {
				steps.add("[ ] " +  filePath  + " (" + mapentry.getValue()+" files expected, but " + currentLenght   + " found)" );
				System.err.println("The repetoire " + filePath + " has " + currentLenght + " files instead of " + mapentry.getValue() + " . The node " + label + " can't be validate");
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

	public ArrayList<String> getSteps() {
		return steps;
	}

	public void setStpes(ArrayList<String> steps) {
		this.steps = steps;
	}

	public ActionNode getActionNode() {
		return actionNode;
	}
    
    
    
}
