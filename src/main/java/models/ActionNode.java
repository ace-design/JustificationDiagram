package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<String> path; 
	public Map<String,Integer> pathWithNumber;
	public boolean optional;

	public List<String> actions; 
	
	public ActionNode() {
		label = null;
		reference = null;
		path = new ArrayList<> ();
		pathWithNumber = new HashMap<> ();
		optional = false;
	}
	
	public ActionNode(String label,String reference,List<String> path, Map<String,Integer> pathWithNumber,boolean optional,List<String> action) {
		this.label = label;
		this.path = path;
		this.pathWithNumber = pathWithNumber;
		this.reference = reference;
		this.optional = optional;
		this.actions = action;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public void setPathWithNumber(Map<String, Integer> pathWithNumber) {
		this.pathWithNumber = pathWithNumber;
	}
	
	public void setAction(List<String> action) {
		this.actions = action;
	}

}
