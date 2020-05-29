package models;

import java.util.ArrayList;

import export.*;

public class Domain extends Node {
	
	private ArrayList<String> realizationList;

    public Domain(String alias, String label,ArrayList<String> realizationList) {
        super(alias, label);
        this.realizationList = realizationList;
        setState(); 
    }

    public void accept(JDVisitor JDVisitor) {
        JDVisitor.visitDomain(this);
    }
    
    /**
     * Analyse the file 'realization.txt' and return the correct State for this node
     * 
     * @return State corresponding to the node
     */
    public void setState() {
    	
    	if(realizationList != null && realizationList.contains(label)) {
    		this.state = State.DONE;
    	}
    	else {
    		this.state = State.TODO;
    	}
    	
     }
    
}
