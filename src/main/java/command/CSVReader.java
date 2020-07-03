package command;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {
	
	
	private List<List<String>> records = new ArrayList<>();
	private String path;
	
	private static final String COMMA_DELIMITER = ",";
	
	private String comma_delimiter = COMMA_DELIMITER;
	

	public CSVReader(String path) throws IOException {
		this(path,COMMA_DELIMITER);
	}
	
	public CSVReader(String path, String comma_delimiter) throws IOException {
		super();
		this.path = path;
		this.comma_delimiter = comma_delimiter;
		records = readCSV();
	}
	

	private List<List<String>>  readCSV() throws IOException {
		List<List<String>> records = new ArrayList<>();
		try (
				BufferedReader input  = new BufferedReader(new FileReader(path));
				){
			String line;
			while ((line = input.readLine()) != null) {
				String[] values = line.split(comma_delimiter);
				records.add(Arrays.asList(values));
			}
			return records;
		}
		catch (FileNotFoundException e) {
			System.err.println("The file '" + path + "' was not found.");
			throw e;
		} catch (IOException ie) {
			System.err.println("Not able to read the file '" + path );
			throw ie;
		}
	}
	
	public List<String> getLine(int indice ) throws OutOfCSVException {
		if (indice >= records.size())
			throw new OutOfCSVException(path,indice);
		return records.get(indice);
	}
	
	 //The first line is number 0. Be careful, it's often a header line.
	public String getValue(String column, int lineIndice) throws OutOfCSVException {
		int columnIndice= records.get(0).indexOf(column);
		List<String> line = getLine(lineIndice);
		return line.get(columnIndice);
	}

	 //The first line is number 0. Be careful, it's often a header line.
	public String getValue(int column, int lineIndice) throws OutOfCSVException {
		List<String> line = getLine(lineIndice);
		return line.get(column);
	}

	
	
	
}
