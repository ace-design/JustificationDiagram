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
    
    // - information of realization file
    public ArrayList<String> realizationResult = new ArrayList<>();
    public ArrayList<String> realizationList = new ArrayList<>();
    public ArrayList<String> checkFile = new ArrayList<>();
    public HashMap<String,Integer> checkFileWithNumber = new HashMap<>();
    public String references;
    
    // - log
    public ArrayList<String> logForFiles = new ArrayList<>();
    
    // - constant
    public String constantNoReferences = "!noRef!";
    public String constantReferences = "!ref!";
    public String constantNumber = "!number!";
 
    public Node(String alias, String label,ArrayList<String> realizationResult) { 
    	this.label = label;
        this.alias = alias;
        this.inputs = new HashSet<>();
        this.outputs = new HashSet<>();
        this.references= constantNoReferences; 
        this.state = State.TODO;  
        
        realizationResultParse(realizationResult);

    }

    public Node(Node node) {
    	this.label = node.label;
        this.alias = node.alias;
        this.inputs = node.inputs;
        this.outputs = node.outputs;
        this.state = node.state;
        this.references = constantNoReferences;

        realizationResultParse(realizationResult);

    }
    

    /** 
     * If the current node have no child; check the realizationList.
     * Verifies that all necessary files are present.
     * Then analyze the children of the current node. If all the children of this one are DONE, it will be DONE, otherwise it will be TODO. 
     * If he has no children, no change will be made.  
     * 
     */
    public void prerequisiteAnalysis() {
    	
    	boolean isDone = true;
    	// Use to check that the current node depends on the state of these inputs.
    	if(inputs.isEmpty()) {
    		isDone = realizationListAnalyses();
    		}
		// use to analyze the state of the child nodes 
		isDone = relationAnalyse();
    	// used to verify that the necessary files are present.  
		if(!checkFile.isEmpty()) {
			isDone = checkFileAnalyses();
    	}
		// used to check the number of files in a repertory
		if(!checkFileWithNumber.isEmpty()) {
			isDone = CheckFileWithNumberAnalyses();
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
			if(relation.from.state.equals(State.TODO)) {
				return false;
			}
		}
    	return true;
    }
    
    /**
     * used to check if the label is contains in 'realizationList'
     * @return true if the label is containt in 'realizationList'
     */
    public boolean realizationListAnalyses() {
    	
		if(realizationList != null && realizationList.contains(label)) {
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
    	for (String filePath : checkFile) {
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
    	for (Map.Entry<String,Integer> mapentry : checkFileWithNumber.entrySet()) {
    		String filePath = mapentry.getKey();
    		int currentLenght = 0;
			if(!new File(mapentry.getKey()).exists()) {
				logForFiles.add("[ ] " + filePath  + " - (not found)");
				isDone = false;
			}
			else if((currentLenght = new File(filePath).listFiles().length) != mapentry.getValue()) {
				logForFiles.add("[ ] " +  filePath  + " - (" + currentLenght +" files found instead of " + mapentry.getValue() + ")" );
				System.err.println("The repetoire " + filePath + " has " + currentLenght + " files instead of " + mapentry.getValue() + " . The node " + label + " can't be validate");
				isDone = false;
			}
			else {
				logForFiles.add("[x] "+  filePath);

			}
		}
    	return isDone;
    }

    /**
     * Analyzes the String list that contains the 'realization' information. 
     * With this information, it will fill in the CheckList, the references and the list of labels made. 
     * 
     * @param realizationResult
     */
    public void realizationResultParse(ArrayList<String> realizationResult) {
    	    	 
    	for (String realizationLine : realizationResult) {
    		
    		realizationLine = realizationLine.replaceAll("\"", "");
    		    		 
   			if(realizationLine.contains("!-!")) {
				String[] tmp = realizationLine.split("!-!");
				
				String currentLabel = "\"" + tmp[0] + "\"";
				
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + tmp[0] + "\"");
					
					if(tmp[1].contains(constantReferences)) {
						// if 'realizationLine' look like this : 'label!-!texte.txt!ref!references'
						String[] tmp2 = tmp[1].split(constantReferences);
						
						setChekFile(tmp2[0]);
						references = tmp2[1];
							
					}
					else {
						// if 'realizationLine' look like this : 'label!-!texte.txt'
						setChekFile(tmp[1]);
						references = constantNoReferences;
					}
				}
				
			
			}
			else if (realizationLine.contains(constantReferences)) {
				// if 'realizationLine' look like this : 'label!ref!references'
				String[] tmp = realizationLine.split(constantReferences);
				String currentLabel = "\"" + tmp[0] + "\"";
				
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + tmp[0] + "\"");
					references = tmp[1];
					checkFile = new ArrayList<>();
				}
				
				
			}
			else {
				// if 'realizationLine' look like this : 'label'
				String currentLabel = "\"" + realizationLine + "\"";
				if(currentLabel.equals(label)) {
					realizationList.add("\"" + realizationLine + "\"");
					references = constantNoReferences;
					checkFile = new ArrayList<>();
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
    		if(!filePath.equals(" ")) {
    			if(filePath.contains(constantNumber)) {
    				// if 'filePath' look like this : 'directory!number!10'
    				String[] tmp = filePath.split(constantNumber);
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
