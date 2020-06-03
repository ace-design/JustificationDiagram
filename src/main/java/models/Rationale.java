package models;

import java.io.File;
import java.util.ArrayList;

import export.*;

public class Rationale extends Node {

    public Rationale(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
        setState(); 
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitRationale(this);
    }
    
    /**
     * Verifies that all necessary files are present and 
     * analyse the list 'realizationList' and set the correct State for this node
     * 
     */
    public void setState() {    	
    	boolean isDone = true;

    	if(!checkFile.isEmpty()) {
    		for (String filePath : checkFile) {
				if(!new File(filePath).exists()) {
					isDone = false;
					System.err.println("File " + filePath + " not found");
    				break;
				}
			}
    	}

    	if(realizationList != null && realizationList.contains(label) && isDone) {
    		this.state = State.DONE;
    	}
    	else {
    		this.state = State.TODO;
    	}
    	
  
    	
     }
}
