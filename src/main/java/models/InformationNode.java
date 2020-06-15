package models;

import java.util.ArrayList;
import java.util.HashMap;

public class InformationNode {
	
	public String label;
	public String reference;
	public ArrayList<String> path;
	public HashMap<String,Integer> pathWithNumber;
	public boolean optional;
	
	public InformationNode() {
		label = null;
		reference = null;
		path = new ArrayList<String> ();
		pathWithNumber = new HashMap<String,Integer> ();
		optional = false;
	}
	
	public InformationNode(String label,String reference,ArrayList<String> path, HashMap<String,Integer> pathWithNumber,boolean optional) {
		this.label = label;
		this.path = path;
		this.pathWithNumber = pathWithNumber;
		this.reference = reference;
		this.optional = optional;
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
	
	
	
	
	
	

}
