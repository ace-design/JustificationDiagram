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
    	setState(this); 

    }

    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
    	setState(node);
    }
    
    /**
     * used to return the correct State 
     * 
     * @param node whose state is sought
     * @return State corresponding to the node
     */
    public void setState(Node node) {
    	
    	
    	File realization = new File("realization.txt");
    	if(realization.exists()) {
    		try {
				analyseRealisation(realization,node);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

     }
    
    /**
     * used to analyse the file 'realization.txt' who correpond to the tasks acomplished
     * 
     * @param realization list of tasks acomplished
     * @param node corresponding to the task
     * @throws IOException
     */
    public static void analyseRealisation(File realization,Node node) throws IOException {

    	RandomAccessFile ranRealization = new RandomAccessFile(realization,"r");
    	String lineRealization;    	
    	
    	while((lineRealization = ranRealization.readLine()) != null) {
			if(node.label.contains('\"' + lineRealization + '\"')) {
    			node.state = State.DONE;
    			break;
			}
			else {
				node.state = State.TODO;				
    		}
    	}
    	ranRealization.close();
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
