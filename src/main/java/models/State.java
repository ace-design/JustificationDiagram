package models;

/**
 * 
 * This enumerartion is used to define the state of a node
 * 
 * @author Nicolas-Corbiere
 *
 */
public enum State {
	
	TODO("Todo"),
	DONE("Done");
	
	private String nom = "";
	
	 private State(String nom) {  
		 this.nom = nom;
    }  
      
	 @Override
     public String toString() {  
         return nom;  
    } 
     
	/*
	 * public State setState(String state) { if(state.equalsIgnoreCase("DONE")) {
	 * return DONE; } else { return TODO; } }
	 */
}
