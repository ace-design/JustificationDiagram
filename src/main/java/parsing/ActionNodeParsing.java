package parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import models.ActionNode;
import models.Node;

/**
 * 
 * This class analyzes an action file and creates a map that contains the label of the node and the corresponding ActionNode.
 * 
 * @author Nicolas-Corbiere
 *
 */
public class ActionNodeParsing {
	
	private static final Logger logger = LogManager.getLogger(ActionNodeParsing.class);

	private String path;
	private Map<String,ActionNode> actionNodes;

	public Map<String, ActionNode> getActionNodes() {
		return actionNodes;
	}

	public ActionNodeParsing(String path) {
		this.path = path;
		if(path != null) {
			parseInfomation();
		}
		
	}

	/**
	 * retrieve the objects from the json file and analyze them
	 */
	public void parseInfomation() {

		actionNodes = new HashMap<>();

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(path)) {
			
			readInformation(jsonParser,reader);

		} catch (FileNotFoundException e) {
			logger.error("The file for actions %s was not found", path);
		} catch (IOException e) {
			logger.error("Fail reading the file for actions %s ", path);
		} 

	}
	
	public void readInformation(JSONParser jsonParser, FileReader reader) {
		// Read JSON file
		Object obj = null;
		try {
			obj = jsonParser.parse(reader);
			JSONArray employeeList = (JSONArray) obj;

			for (Object object : employeeList) {
				ActionNode info = parseInformation((JSONObject) object);
				actionNodes.put(info.label, info);
			}
			
		} catch (org.json.simple.parser.ParseException | IOException e) {
			// if the information files is null
			logger.error("ActionNode is null, there is no information in this file.");
		}
		
		
	}

	/**
	 * create an ActionNode with the information from the JsonInformation
	 * 
	 * @param jsonInformation object find in the file, the object correspond to a node
	 * @return
	 */
	public ActionNode parseInformation(JSONObject jsonInformation) {

		ArrayList<String> fileList = null;
		HashMap<String, Integer> fileNumberMap = null;
		ArrayList<String> actionList = null;
		
		// Get Node object within list
		JSONObject informationObject = (JSONObject) jsonInformation.get("Node");

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

		return new ActionNode(label, reference, fileList, fileNumberMap,optional,actionList);
	}


}
