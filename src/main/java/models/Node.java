package models;

import export.*;
import java.util.*;

public class Node implements Visitable {
    public String alias;
    public String label;
    public Set<Relation> inputs;
    public Set<Relation> outputs;
    public State state;

    public Node(String alias, String label) {
    	setStateAndLabel(label, this); 
        this.alias = alias;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
    }

    public Node(Node node) {
    	setStateAndLabel(node.label,node);
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
    }
    
    /** 
     * 
     * used to set the label and the state of the node
     * 
     * @param label initial label of the node
     * @param node node that will be changed 
     */
    public void setStateAndLabel(String label,Node node){
    	    	
    	if(label.contains("!-!")) {
    		String[] result = label.split("!-!");
        	node.state = setState(result[0].substring(1)); // if I don't add the substring; I have (for exemple) '"@DONE' instade of '@DONE' 
        	node.label = "\"" + result[1]; // if I don't add the "\"", I have a probleme
        	    	}
    	else {
    		node.label = label;
    		node.state = State.TODO;
    	}

    }
    
    /**
     * used to return the correct State 
     * 
     * @param label first label who have been find in the '.jd' 
     * @return State corresponding to the label
     */
    public State setState(String label) {
     	if(label.equalsIgnoreCase("@DONE")) {
     		return State.DONE;
     	}
     	else {
     		return State.TODO;
     	}
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
}
