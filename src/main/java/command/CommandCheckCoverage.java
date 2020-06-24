package command;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandCheckCoverage implements Command{

	private static final String COMMA_DELIMITER = ",";
	private static final Object MISSED = "INSTRUCTION_MISSED";
	private static final Object COVERED = "INSTRUCTION_COVERED";
	private static final String HTML = "html";
	private static final String CSV = "csv";
	private static final String FAIL = "fail";
	public String path;
	public String operator;
	public int coverage;	

	public CommandCheckCoverage(String path, int coverage) {
		this.path = path;
		this.coverage = coverage;
	}


	public CommandCheckCoverage() {
		this(null,0);
	}

	@Override
	public ArrayList<String> execute(String args) {

		setArgs(args);
		ArrayList<String> result = new ArrayList<> ();

		String fileKind = inputIsExpectedFile(path);

		if (fileKind.equals(FAIL) ) {
			result.add(FAIL);
		}

		int coverageFind = 0;

		if (fileKind.equals(HTML) )
			coverageFind = parsingHTMLJacocoReport();

		//TODO improve exception managment
		if (fileKind.equals(CSV) )
			try {
				coverageFind = parsingCSVJacocoReport();
			} catch (IOException e) {

				System.err.println("The file " + path + " was null" );
				result.add(FAIL);
			}

		String isDone = searchIdItsDone(coverageFind);

		String step = "";
		if(isDone.contains("true")) {
			// [x] Current coverage is 80, it's > 50
			step = "[x] Current coverage is " + coverageFind + ", it's " + operator + " " + coverage;
		}
		else {
			// [ ] Current coverage is 30, it's not > 50
			step = "[ ] Current coverage is " + coverageFind + ",  it's not " + operator + " " + coverage;
		}

		result.add(isDone);
		result.add(step);

		return result;


	}

	@SuppressWarnings("java:S1871")
	public String searchIdItsDone(int coverageFind) {		
		if(operator.equals("==") && coverageFind == coverage) {
			return "true";
		}
		else if(operator.equals("<") && coverageFind < coverage) {
			return "true";
		}
		else if(operator.equals("<=") && coverageFind <= coverage) {
			return "true";
		}
		else if(operator.equals(">") && coverageFind > coverage) {
			return "true";
		}
		else if(operator.equals(">=") && coverageFind >= coverage) {
			return "true";

		}
		else if(operator.equals("!=") && coverageFind != coverage) {
			return "true";
		}

		return "false";
	}

	/**
	 * 
	 * Used to analyze the "index.html" of the jacoco report and find the total coverage of the project.
	 * 
	 * @return return the total coverage of the project
	 */
	public int parsingHTMLJacocoReport() {


		int coverageFind = 0;
		BufferedReader input = null;

		if(inputIsValid(path)) {
			try {
				input = new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException e) {
				System.err.println("The file '" + path + "' was not found. Please indicate the index.html of the jacoco report.");
				return 0;
			}
			String line;
			try {
				while ((line = input.readLine()) != null)
				{
					int position = line.indexOf("<td class=\"ctr2\">");
					coverageFind = Integer.parseInt(line.substring(position +17,position+19));
				}
			} catch (IOException e) {
				System.err.println("The file " + path + " was null" );
			}
			try {
				input.close();
			} catch (IOException e) {
				System.err.println("The input " + input + " was null" );
			}
		}

		return coverageFind;
		
	} 


	/**
	 * 
	 * Used to analyze the "jacoco.csv" of the jacoco report and find the total coverage of the project.
	 * based on https://www.baeldung.com/java-csv-file-array
	 * @return return the total coverage of the project
	 * @throws IOException 
	 */
	public int parsingCSVJacocoReport() throws IOException {

		int coverageFind = 0;

		List<List<String>> records = readCSV();


		int indiceCovered = records.get(0).indexOf(COVERED);
		int indiceMissed = records.get(0).indexOf(MISSED);

		//Remove the header, i.e. the first line
		records.remove(0);

		int covered = getSumOfOneIndice(indiceCovered, records);
		int missed = getSumOfOneIndice(indiceMissed, records);
		if (missed+covered == 0)
			coverageFind = 0;
		else 
			coverageFind =  (100 * covered) /(missed+covered);
		return coverageFind;
	}


	private List<List<String>>  readCSV() throws IOException {
		List<List<String>> records = new ArrayList<>();
		try (
				BufferedReader input  = new BufferedReader(new FileReader(path));
				){
			String line;
			while ((line = input.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
			return records;
		}
		catch (FileNotFoundException e) {
			System.err.println("The file '" + path + "' was not found. Please indicate the jacoco.csv of the jacoco report.");
			throw e;
		} catch (IOException ie) {
			System.err.println("Not able to read the file '" + path );
			throw ie;
		}
	}


	private int getSumOfOneIndice(int indice, List<List<String>> records ) {
		int value = 0;
		for ( List<String> line : records) {
			value += Integer.parseInt(line.get(indice));
		}
		return value;
	}


	/**
	 * Set the path, the operator and the coverage.
	 * 
	 * @param args the normal ligne, example : "target/site/jacoco/index.html < 10"
	 */
	public void setArgs(String args) {
		// example :
		//justification/IUTtest/test < 10
		String[] tmp = args.split(" ");
		if (tmp.length != 3) {
			System.err.println(args + " is not a valid command to check coverage in jacoco report.");
			return ;
		}
		path = tmp[0];

		operator = tmp[1];

		coverage = Integer.parseInt(tmp[2]);

		System.out.println("CheckCover action - args : '" + path + "' for " + operator + " coverage " + coverage);
	}


	private static String inputIsExpectedFile(String in) {
		if (in.matches(".*\\.(html)")) 
			return HTML;
		if (in.matches(".*\\.(csv)"))
			return CSV;
		System.err.println(in + " is not a valid name. Please indicate the index.html or the .csv of the jacoco report.");
		return FAIL;
	}

	private static boolean inputIsValid(String in) {
		if (!in.matches(".*\\.(html)")) {
			System.err.println(in + " is not a valid name. Please indicate the index.html of the jacoco report.");
			return false;
		}
		return true;
	}




}
