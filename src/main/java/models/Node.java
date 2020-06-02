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
    
    public ArrayList<String> realizationResult = new ArrayList<String>();
    public ArrayList<String> realizationList = new ArrayList<String>();
    public ArrayList<String> checkFile = new ArrayList<String>();
    public String references;

    public Node(String alias, String label,ArrayList<String> realizationResult) { 
    	this.label = label;
        this.alias = alias;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
        this.references="!noRef!";
        
        this.state = State.TODO;  
        
        analyseRealizationResult(realizationResult);

    }

    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
        this.state = node.state;
        this.references="!noRef!";

        analyseRealizationResult(realizationResult);

    }
    

    /** 
     * Analyse the children of the current node to changed is state. If all is children are DONE, i will done. If he don't have childrens, no changes will be made.
     * 
     * @param node to analyse and change
     */
    public void analyseRelation() {
    	
    	boolean isDone = true;
    	if(!inputs.isEmpty()) {

    		for (Relation relation : inputs) {
    			if(relation.from.state.equals(State.TODO)) {

    				isDone = false;
    				break;
    			}
    			if(!checkFile.isEmpty() && isDone) {
    				
    	    		for (String filePath : checkFile) {
    					if(!new File(filePath).exists()) {
    						isDone = false;
    						System.err.println("File " + filePath + " not found");
    	    				break;
    					}
    				}
    	    	}
    			
    		}
        	if(isDone) {
        		this.state = State.DONE;
        	}
    	}	
    	
		
    	
    	
    }

    
    public void analyseRealizationResult(ArrayList<String> realizationResult) {
    	    	
    	for (String string : realizationResult) {
    		
    		string = string.replaceAll("\"", "");
    		    		
   			if(string.contains("!-!")) {
				String[] tmp = string.split("!-!");
				
				String currentLabel = "\"" + tmp[0] + "\"";
				
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + tmp[0] + "\"");
					
					if(tmp[1].contains("!ref!")) {
						String[] tmp2 = tmp[1].split("!ref!");
						
						analyseChekFile(tmp2[0]);
						references = tmp2[1];
							
					}
					else {
						analyseChekFile(tmp[1]);
						references = "!noRef!";
					}
				}
				
			
			}
			else if (string.contains("!ref!")) {
				String[] tmp = string.split("!ref!");
				String currentLabel = "\"" + tmp[0] + "\"";
				
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + tmp[0] + "\"");
					references = tmp[1];
					checkFile = new ArrayList<String>();
				}
				
				
			}
			else {
				String currentLabel = "\"" + string + "\"";
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + string + "\"");
					references = "!noRef!";
					checkFile = new ArrayList<String>();
				}
				
			}
        	
		}
    	
    }
    
    public void analyseChekFile(String string) {
    	
    	String[] fileList = string.split(";");
    	
    	for (String string2 : fileList) {
    		if(!string2.equals(" ") && string2 != null) {
    			checkFile.add(string2);
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
