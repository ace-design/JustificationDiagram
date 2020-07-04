package command;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandCheckCoverage implements Command {

	private static final Logger logger = LogManager.getLogger(CommandCheckCoverage.class);

	private static final Object MISSED = "INSTRUCTION_MISSED";
	private static final Object COVERED = "INSTRUCTION_COVERED";

	private String path;
	private String operator;
	private int coverage;

	public CommandCheckCoverage(String path, int coverage) {
		this.path = path;
		this.coverage = coverage;
		 
	}

	public CommandCheckCoverage() {
		this(null, 0);
	}

	@Override
	public ArrayList<String> execute(String args) {
		setArgs(args);
		ArrayList<String> result = new ArrayList<>();

		if (Boolean.FALSE.equals(CommandHelper.inputIsCSVFile(path))) {
			result.add(CommandHelper.FAIL);
			result.add("Unexpected format for " + path);
		}

		int coverageFind = 0;

		try {
			coverageFind = parsingCSVJacocoReport();
		} catch (IOException e) {
			String logMsg = String.format("error Parsing CSV file : %s", path);
			logger.error(logMsg);
			result.add(CommandHelper.FAIL);
			result.add(logMsg);
		}

		String isDone = searchIdItsDone(coverageFind);

		String step = "";
		if (isDone.contains("true")) {
			// [x] Current coverage is 80, it's > 50
			step = "[x] Current coverage is " + coverageFind + ", it's " + operator + " " + coverage + " ";
		} else {
			// [ ] Current coverage is 30, it's not > 50
			step = "[ ] Current coverage is " + coverageFind + ",  it's not " + operator + " " + coverage + " ";
		}

		result.add(isDone);
		result.add(step);

		return result;

	}

	public String searchIdItsDone(int coverageFind) {
		logger.debug("compare %d %s %d", coverageFind, operator, coverage);
		return String.valueOf(CommandHelper.compareInt(coverageFind, coverage, operator));
	}

	/**
	 * 
	 * Used to analyze the "index.html" of the jacoco report and find the total
	 * coverage of the project.
	 * 
	 * @return return the total coverage of the project
	 */
	/*
	 * public int parsingHTMLJacocoReport() { int coverageFind = 0; BufferedReader
	 * input = null;
	 * 
	 * if(inputIsValid(path)) { try { input = new BufferedReader(new
	 * FileReader(path)); } catch (FileNotFoundException e) {
	 * System.err.println("The file '" + path +
	 * "' was not found. Please indicate the index.html of the jacoco report.");
	 * return 0; } String line; try { while ((line = input.readLine()) != null) {
	 * int position = line.indexOf("<td class=\"ctr2\">"); coverageFind =
	 * Integer.parseInt(line.substring(position +17,position+19)); } } catch
	 * (IOException e) { System.err.println("The file " + path + " was null" ); }
	 * try { input.close(); } catch (IOException e) {
	 * System.err.println("The input " + input + " was null" ); } }
	 * 
	 * return coverageFind;
	 * 
	 * }
	 */

	/**
	 * 
	 * Used to analyze the "jacoco.csv" of the jacoco report and find the total
	 * coverage of the project. based on
	 * https://www.baeldung.com/java-csv-file-array
	 * 
	 * @return return the total coverage of the project
	 * @throws IOException
	 */
	public int parsingCSVJacocoReport() throws IOException {

		int coverageFind = 0;

		CSVReader cs = new CSVReader(path);
		List<List<String>> records = cs.readCSV();

		int indiceCovered = records.get(0).indexOf(COVERED);
		int indiceMissed = records.get(0).indexOf(MISSED);

		// Remove the header, i.e. the first line
		records.remove(0);
		logger.debug("Records after removing the first line : {}", records);
		int covered = getSumOfOneIndice(indiceCovered, records);
		int missed = getSumOfOneIndice(indiceMissed, records);
		if (missed + covered == 0)
			coverageFind = 0;
		else
			coverageFind = (100 * covered) / (missed + covered);
		return coverageFind;
	}

	private int getSumOfOneIndice(int indice, List<List<String>> records) {

		int value = 0;

		for (List<String> line : records) {
			try {
				value += Integer.parseInt(line.get(indice));
			} catch (NumberFormatException e) {
				String logMsg = String.format("error Parsing CSV file, expecting a number : %s", line.get(indice));
				logger.error(logMsg);
				throw e;
			}
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
		// justification/IUTtest/test < 10
		String[] tmp = args.split(" ");
		if (tmp.length != 3) {
			logger.error("%s is not a valid command to check coverage in jacoco report.",args);
			return;
		}
		path = tmp[0];
		operator = tmp[1];
		coverage = Integer.parseInt(tmp[2]);
	}



}
