package models;

import java.io.File;
import java.util.ArrayList;

import export.*;

public class Support extends Node {

    public Support(String alias, String label,ArrayList<String> realizationResult) {
        super(alias, label,realizationResult);
        setState(); 
    }

    @Override
    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitSupport(this);
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
