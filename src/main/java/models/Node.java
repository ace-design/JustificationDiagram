package models;

import export.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Node implements Visitable {
    public String alias;
    public String label;
    public Set<Relation> inputs;
    public Set<Relation> outputs;
    public State state;

    public Node(String alias, String label) { 
    	this.label = label;
        this.alias = alias;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
        this.state = State.TODO;        
    }

    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
        this.state = node.state;
    }
    

    /**
     * Analanyse the children of the current node to changed is state. If all is children are DONE, i will done. If he don't have childrens, no changes will be made.
     * 
     * @param node to analyse and change
     */
    public void analyseRelation(Node node) {
    	boolean isDone = true;
    	if(!node.inputs.isEmpty()) {
    		for (Relation relation : node.inputs) {
    			if(relation.from.state.equals(State.TODO)) {
    				isDone = false;
    				break;
    			}
    			
    		}
        	if(isDone) {
        		this.state = State.DONE;
        	}
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
