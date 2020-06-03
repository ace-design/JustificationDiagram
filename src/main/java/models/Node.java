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
        
        realizationResultAnalysis(realizationResult);

    }

    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
        this.state = node.state;
        this.references="!noRef!";

        realizationResultAnalysis(realizationResult);

    }
    

    /** 
     * Verifies that all necessary files are present
     * Then analyze the children of the current node. If all the children of this one are DONE, it will be DONE, otherwise it will be TODO. 
     * If he has no children, no change will be made.  
     * 
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

    /**
     * Analyzes the String list that contains the 'realization' information. 
     * With this information, it will fill in the CheckList, the references and the list of labels made. 
     * 
     * @param realizationResult
     */
    public void realizationResultAnalysis(ArrayList<String> realizationResult) {
    	    	
    	for (String realizationLine : realizationResult) {
    		
    		realizationLine = realizationLine.replaceAll("\"", "");
    		    		
   			if(realizationLine.contains("!-!")) {
				String[] tmp = realizationLine.split("!-!");
				
				String currentLabel = "\"" + tmp[0] + "\"";
				
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + tmp[0] + "\"");
					
					if(tmp[1].contains("!ref!")) {
						// if 'realizationLine' look like this : 'label!-!texte.txt!ref!references'
						String[] tmp2 = tmp[1].split("!ref!");
						
						analyseChekFile(tmp2[0]);
						references = tmp2[1];
							
					}
					else {
						// if 'realizationLine' look like this : 'label!-!texte.txt'
						analyseChekFile(tmp[1]);
						references = "!noRef!";
					}
				}
				
			
			}
			else if (realizationLine.contains("!ref!")) {
				// if 'realizationLine' look like this : 'label!ref!references'
				String[] tmp = realizationLine.split("!ref!");
				String currentLabel = "\"" + tmp[0] + "\"";
				
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + tmp[0] + "\"");
					references = tmp[1];
					checkFile = new ArrayList<String>();
				}
				
				
			}
			else {
				// if 'realizationLine' look like this : 'label'
				String currentLabel = "\"" + realizationLine + "\"";
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + realizationLine + "\"");
					references = "!noRef!";
					checkFile = new ArrayList<String>();
				}
				
			}
        	
		}
    	
    }
    // TODO : changer l'anaylise de l'existances de fichier par la vérification du 
    // bon nombre de fichier dans la liste de repertoire donner (oui il faut donner une liste de repertoire, ça donne plus de liberté)
    /**
     * Analyzes the character string and decomposes it to obtain a list of paths to the files to check
     * 
     * @param string to analyzes (look like this : file1;file2;file3)
     */
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
