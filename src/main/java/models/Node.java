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
    public HashMap<String,Integer> checkFileWithNumber = new HashMap<String,Integer>();
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
    public void prerequisiteAnalysis() {
    	
    	boolean isDone = true;
    	// Use to check that the current node depends on the state of these inputs.
    	if(!inputs.isEmpty()) {
    		// use to analyze the state of the child nodes 
    		isDone = relationAnalyse();
	    	// used to verify that the necessary files are present.  
			if(!checkFile.isEmpty() && isDone) {
				isDone = checkFileAnalyses();
	    	}
			// used to check the number of files in a repertory
			if(!checkFileWithNumber.isEmpty() && isDone) {
				isDone = CheckFileWithNumberAnalyses();
	    	}
	    	if(isDone) {
	    		this.state = State.DONE;
	    	}
    	}
    }
    
    /**
     * use to analyze the state of the child nodes 
     * 
     * @return true if all child node are DONE, else return false.
     */
    public boolean relationAnalyse() {
    	// used to verify that the necessary files are present.  
		for (Relation relation : inputs) {
			if(relation.from.state.equals(State.TODO)) {
				return false;
			}
		}
    	return true;
    }
    
    /**
     * used to verify that the necessary files are present.
     * 
     * @return true if all files are present, else return false.
     */
    public boolean checkFileAnalyses() {
    	for (String filePath : checkFile) {
			if(!new File(filePath).exists()) {
				System.err.println("File " + filePath + " not found");
				return false;
			}
		}
    	return true;
    }
    
    /**
     * used to check the number of files in a repertory
     * 
     * @return returns true if the necessary number corresponds to the number of files to be found, else returen false.	
     */
    public boolean CheckFileWithNumberAnalyses() {
    	for (Map.Entry<String,Integer> mapentry : checkFileWithNumber.entrySet()) {
			if(!new File(mapentry.getKey()).exists() || new File(mapentry.getKey()).listFiles().length != mapentry.getValue() ) {
				System.err.println("The repetoire " + mapentry.getKey() + " has " + new File(mapentry.getKey()).listFiles().length + " files instead of " + mapentry.getValue() );
				return false;
			}		
		}
    	return true;
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
						
						setChekFile(tmp2[0]);
						references = tmp2[1];
							
					}
					else {
						// if 'realizationLine' look like this : 'label!-!texte.txt'
						setChekFile(tmp[1]);
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

    /**
     * Analyzes the character string and decomposes it to obtain a list of paths to the files to check
     * 
     * @param string to analyzes (look like this : file1;file2;file3)
     */
    public void setChekFile(String realizationResult) {
    	
    	String[] fileList = realizationResult.split(";");
    	
    	for (String filePath : fileList) {
    		if(!filePath.equals(" ") && filePath != null ) {
    			if(filePath.contains("!number!")) {
    				// if 'filePath' look like this : 'directory!number!10'
    				String[] tmp = filePath.split("!number!");
    				Integer number = Integer.parseInt(tmp[1]);
    				checkFileWithNumber.put(tmp[0], number);
    			}
    			else {
    				// if 'filePath' look like this : 'test.txt'

    				checkFile.add(filePath);
    			}
    			
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
