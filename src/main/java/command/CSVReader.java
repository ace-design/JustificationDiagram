package command;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CSVReader {
	
	private static final Logger logger = LogManager.getLogger(CSVReader.class);
	
	private List<List<String>> records = new ArrayList<>();
	private String path;
	
	private static final String DEFAULT_COMMA_DELIMITER = ",";
	
	private String commaDelimiter = DEFAULT_COMMA_DELIMITER;
	

	public CSVReader(String path) throws IOException {
		this(path,DEFAULT_COMMA_DELIMITER);
	}
	
	public CSVReader(String path, String pcommaDelimiter) throws IOException {
		super();
		this.path = path;
		this.commaDelimiter = pcommaDelimiter;
		records = readCSV();
	}
	

	public List<List<String>>  readCSV() throws IOException {
		records = new ArrayList<>();
		try (
				BufferedReader input  = new BufferedReader(new FileReader(path));
				){
			String line;
			while ((line = input.readLine()) != null) {
				String[] values = line.split(commaDelimiter);
				records.add(Arrays.asList(values));
			}
			return records;
		}
		catch (FileNotFoundException e) {
			String logMsg =String.format("The file %s was not found",path);
			logger.error(logMsg);
			throw e;
		} catch (IOException ie) {
			String logMsg =String.format("Not able to read the file %s",path);
			logger.error(logMsg);
			throw ie;
		}
	}
	
	//Todo : Tests it
	public List<String> getLine(int indice ) throws OutOfCSVException {
		if (indice >= records.size())
			throw new OutOfCSVException(path,indice);
		return records.get(indice);
	}
	
	 //The first line is number 0. Be careful, it's often a header line.
	public String getValue(String column, int lineIndice) throws OutOfCSVException {
		int columnIndice= records.get(0).indexOf(column);
		if (columnIndice == -1) {
			throw new OutOfCSVException(path, "unexpected column name :"+ column);
		}
		List<String> line = getLine(lineIndice);
		return line.get(columnIndice);
	}

	 //The first line is number 0. Be careful, it's often a header line.
	public String getValue(int column, int lineIndice) throws OutOfCSVException {
		List<String> line = getLine(lineIndice);
		return line.get(column);
	}

	
	
	
}
