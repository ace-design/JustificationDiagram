package models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * This class is used to store information retrieved with the "ActionNodeParsing" parser.
 * 
 * @author Nicolas-Corbiere
 *
 */
public class ActionNode {
	
	public String label;
	public String reference;
	public ArrayList<String> path; 
	public HashMap<String,Integer> pathWithNumber;
	public boolean optional;

	public ArrayList<String> action; 
	
	public ActionNode() {
		label = null;
		reference = null;
		path = new ArrayList<String> ();
		pathWithNumber = new HashMap<String,Integer> ();
		optional = false;
	}
	
	public ActionNode(String label,String reference,ArrayList<String> path, HashMap<String,Integer> pathWithNumber,boolean optional,ArrayList<String> action) {
		this.label = label;
		this.path = path;
		this.pathWithNumber = pathWithNumber;
		this.reference = reference;
		this.optional = optional;
		this.action = action;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setPath(ArrayList<String> path) {
		this.path = path;
	}

	public void setPathWithNumber(HashMap<String, Integer> pathWithNumber) {
		this.pathWithNumber = pathWithNumber;
	}
	
	public void setAction(ArrayList<String> action) {
		this.action = action;
	}

}
