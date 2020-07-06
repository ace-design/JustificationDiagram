package parsing;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import command.CommandCheckCoverage;
import models.Node;

public class RealizationParser {

	private static final Logger logger = LogManager.getLogger(RealizationParser.class);

	private String path;

	private List<String> labelList;


	public List<String> getLabelList() {
		return labelList;
	}

	public RealizationParser(String path) throws ExceptionParsingRealizationFile {
		this.path = path;
		labelList = realizationParse(path);

	}

	/**
	 * Used to parse the file 'realizationPath' that corresponds to the accomplished
	 * tasks , required number of files and references of nodes.
	 * 
	 * @param realizationPath path to the realization file
	 * @return list of node information
	 * @throws IOException
	 */
	public static List<String> realizationParse(String realizationPath) throws ExceptionParsingRealizationFile {

		File realization;
		ArrayList<String> realizationResult = new ArrayList<>();

		if (realizationPath != null) {
			realization = new File(realizationPath);
			if (realization.exists()) {
				try (RandomAccessFile ranRealization = new RandomAccessFile(realization, "r")){
					String line;
					while ((line = ranRealization.readLine()) != null) {
						realizationResult.add("\"" + line + "\"");
					}
				} catch (IOException e) {
					logger.error("Exception raised during reading of the realization file %s", realizationPath);
					throw new ExceptionParsingRealizationFile(realizationPath);
				}

			} else {
				logger.error("%s  doesn't exist", realizationPath);
				throw new ExceptionParsingRealizationFile(realizationPath);
			}
		}	
		return realizationResult;
	}
}
