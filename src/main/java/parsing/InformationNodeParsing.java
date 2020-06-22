package parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import models.InformationNode;

/**
 * 
 * This class analyzes an information file and creates a map that contains the label of the node and the corresponding InformationNode.
 * 
 * @author Nicolas-Corbiere
 *
 */
public class InformationNodeParsing {

	public String path;
	public HashMap<String,InformationNode> information;

	public InformationNodeParsing(String path) {
		this.path = path;
		if(path != null) {
			parseInfomation();
		}
		
	}

	/**
	 * retrieve the objects from the json file and analyze them
	 */
	public void parseInfomation() {

		information = new HashMap<>();

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(path)) {
			
			readInformation(jsonParser,reader);

		} catch (FileNotFoundException e) {
			System.err.println("The file " + path + " was not found");
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	public void readInformation(JSONParser jsonParser, FileReader reader) {
		// Read JSON file
		Object obj = null;
		try {
			obj = jsonParser.parse(reader);
			JSONArray employeeList = (JSONArray) obj;

			for (Object object : employeeList) {
				InformationNode info = parseInformation((JSONObject) object);
				information.put(info.label, info);
			}
			
		} catch (org.json.simple.parser.ParseException | IOException e) {
			// if the information files is null
    		System.err.println("InformationNodes is null, there is no information in this file.");
		}
		
		
	}

	/**
	 * create an InformationNode with the information from the JsonInformation
	 * 
	 * @param JsonInformation object find in the file, the object correspond to a node
	 * @return
	 */
	public InformationNode parseInformation(JSONObject JsonInformation) {

		ArrayList<String> fileList = null;
		HashMap<String, Integer> fileNumberMap = null;
		ArrayList<String> actionList = null;
		
		// Get Node object within list
		JSONObject informationObject = (JSONObject) JsonInformation.get("Node");

		// Get Node Label
		String label = (String) informationObject.get("Label");

		// Get Node Reference
		String reference = (String) informationObject.get("Reference");
		if (reference == null) {
			reference = "!noRef!";
		}
		
		// Get if the Node is Optional
		String optionalString = (String) informationObject.get("Optional");
		boolean optional = false;
		if (optionalString != null) {
			optional = optionalString.contains("true");
		}
		
		// Get Node list of Files

		JSONArray arrFiles = (JSONArray) informationObject.get("Files");

		if (arrFiles != null) {
			fileList = new ArrayList<>();
			for (Object object : arrFiles) {
				fileList.add(object.toString());
			}

		}

		// Get Node list of Files with number
		JSONArray arrFilesNumber = (JSONArray) informationObject.get("FilesNumber");

		if (arrFilesNumber != null) {
			fileNumberMap = new HashMap<>();

			for (Object object : arrFilesNumber) {
				JSONObject o = (JSONObject) object;
				fileNumberMap.put(o.get("Path").toString(), Integer.parseInt(o.get("Number").toString()));

			}

		}
		
		JSONArray arrActions = (JSONArray) informationObject.get("Actions");

		if (arrActions != null) {
			actionList = new ArrayList<>();
			for (Object object : arrActions) {
				actionList.add(object.toString());
			}

		}

		return new InformationNode(label, reference, fileList, fileNumberMap,optional,actionList);
	}


}
