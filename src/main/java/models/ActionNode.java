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
	
	private String label;
	private String reference;
	private List<String> path; 
	private Map<String,Integer> pathWithNumber;
	private boolean optional;
	private List<String> actions; 
	
	public ActionNode() {
		label = null;
		reference = null;
		path = new ArrayList<> ();
		setPathWithNumber(new HashMap<> ());
		optional = false;
	}
	
	public ActionNode(String label,String reference,List<String> path, Map<String,Integer> pathWithNumber,boolean optional,List<String> action) {
		this.label = label;
		this.path = path;
		this.setPathWithNumber(pathWithNumber);
		this.reference = reference;
		this.optional = optional;
		this.actions = action;
	}




	private void setPathWithNumber(Map<String, Integer> pathWithNumber) {
		this.pathWithNumber = pathWithNumber;
	}
	

	public String getLabel() {
		return label;
	}

	public String getReference() {
		return reference;
	}

	public List<String> getPath() {
		return path;
	}

	public Map<String,Integer> getPathWithNumber() {
		return pathWithNumber;
	}

	public boolean isOptional() {
		return optional;
	}

	public List<String> getActions() {
		return actions;
	}

}
