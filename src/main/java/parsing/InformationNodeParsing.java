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

public class InformationNodeParsing {

	public String path;
	public HashMap<String,InformationNode> information;

	public InformationNodeParsing(String path) {
		this.path = path;
		if(path != null) {
			parseInfomation();
		}
		
	}

	public void parseInfomation() {

		information = new HashMap<String,InformationNode>();

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(path)) {
			// Read JSON file
			Object obj = null;
			try {
				obj = jsonParser.parse(reader);
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}

			JSONArray employeeList = (JSONArray) obj;

			for (Object object : employeeList) {
				InformationNode info = parseInformation((JSONObject) object);
				information.put(info.label, info);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static InformationNode parseInformation(JSONObject JsonInformation) {

		ArrayList<String> fileList = null;
		HashMap<String, Integer> fileNumberMap = null;

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
			fileList = new ArrayList<String>();
			for (Object object : arrFiles) {
				fileList.add(object.toString());
			}

		}

		// Get Node list of Files with number
		JSONArray arrFilesNumber = (JSONArray) informationObject.get("FilesNumber");

		if (arrFilesNumber != null) {
			fileNumberMap = new HashMap<String, Integer>();

			for (Object object : arrFilesNumber) {
				JSONObject o = (JSONObject) object;
				fileNumberMap.put(o.get("Path").toString(), Integer.parseInt(o.get("Number").toString()));

			}

		}

		return new InformationNode(label, reference, fileList, fileNumberMap,optional);
		

	}


}
