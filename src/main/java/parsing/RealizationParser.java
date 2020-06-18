package parsing;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import models.Node;

public class RealizationParser {
	
	public String path;
	
	public ArrayList<String> labelList;
	
	public RealizationParser(String path) {
		this.path = path;
		
		try {
			labelList = realizationParse(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Used to parse the file 'realizationPath' who correpond to the tasks
	 * acomplished, required number of files and references of nodes.
	 * 
	 * @param realizationPath path to the realization file
	 * @return list of node information
	 * @throws IOException 
	 */
	public static ArrayList<String> realizationParse(String realizationPath) throws IOException {

		File realization;
		ArrayList<String> realizationResult = new ArrayList<String>();
		
		if (realizationPath != null) {
			
			if ((realization = new File(realizationPath)).exists()) {
				
				RandomAccessFile ranRealization = null;
				try {
					ranRealization = new RandomAccessFile(realization, "r");
					String line;

					while ((line = ranRealization.readLine()) != null) {
						realizationResult.add("\"" + line + "\"");
					}
					ranRealization.close();

				} catch (IOException e) {
					e.printStackTrace();
					
				} 
				finally {
					if(ranRealization != null) {
						ranRealization.close();
					}
				  }

			} else {
				System.err.println(realizationPath + " don't exist");
			}

		}

		return realizationResult;

	}
	
	

}
