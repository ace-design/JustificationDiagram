package command;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author blay
 *
 */
public class CommandCheckValueInCSV implements Command {


	private static final Logger logger = LogManager.getLogger(CommandCheckValueInCSV.class);

	private String path;
	private String column;
	private String operator;
	private String line;
	private int valueToCompare;

	@Override
	public ArrayList<String> execute(String args) {
		setArgs(args);
		ArrayList<String> result = new ArrayList<>();

		// Read the CSV File
		if (Boolean.FALSE.equals(CommandHelper.inputIsCSVFile(path))) {
			result.add(CommandHelper.FAIL);
			String logMsg =String.format("not a csv File :%s" , path);
			result.add(logMsg);
			logger.error(logMsg);
			return result;
		}

		CSVReader csvReader;
		try {
			csvReader = new CSVReader(path);
		} catch (Exception e) {
			String logMsg =String.format("error reading the file : %s %n %s", path,  e.getClass());
			logger.error(logMsg);
			result.add(CommandHelper.FAIL);
			return result;
		}

		// Get the value in CSV
		int lineNumber = -1;
		String value = "";
		try {
			lineNumber = Integer.parseInt(line);

			int columnNumber = -1;
			try {
				columnNumber = Integer.parseInt(column);
				value = csvReader.getValue(columnNumber, lineNumber);
			} catch (NumberFormatException e) {
				try {
					value = csvReader.getValue(column, lineNumber);
				} catch (OutOfCSVException e1) {
					String logMsg =String.format("Reference to an invalid Cell (%s, %d)in the csv : %s", column,lineNumber, e);
					logger.error(logMsg);
					result.add(CommandHelper.FAIL);
					result.add(logMsg);
					return result;
				}
			} catch (OutOfCSVException e) {
				String logMsg =String.format("Reference to an invalid Cell_ (%d, %d)in the csv : %s", columnNumber,lineNumber, e);
				logger.error(logMsg);
				result.add(CommandHelper.FAIL);
				result.add(logMsg);
				return result;
			}
			// Check value
			int valueInt;
			try {
				valueInt = Integer.parseInt(value);
			} catch (NumberFormatException e2) {
				String logMsg =String.format("%s : Not an integer in Cell (%d, %d)in the csv %s : %s",value, columnNumber,lineNumber, path, e2);
				logger.error(logMsg);
				result.add(CommandHelper.FAIL);
				result.add(logMsg);
				return result;
			}

			boolean resCommand = CommandHelper.compareInt(valueInt, valueToCompare, operator);

			if (resCommand) {
				result.add(CommandHelper.TRUE);
				result.add("[x] following property is verified : " + "value[" + column + " , " + line + "] ="
						+ valueInt + " " + operator + valueToCompare);
			}
			else {
				result.add(CommandHelper.FALSE);
				result.add("[] following property is not verified : " + "value[" + column + " , " + line + "] ="
						+ valueInt + " " + operator + valueToCompare);
			}
			return result;
		} catch (NumberFormatException e2) {
			String logMsg =String.format("%s is not a line number", line);
			logger.error(logMsg);
			result.add(CommandHelper.FAIL);
			result.add(logMsg);
			return result;
		}

	}

	// example
	// csvFile.scv "INSTRUCTION_MISSED" "1" "==" 546
	// csvFile.scv "3" "1" ">" 500
	private void setArgs(String args) {
		String[] tmp = args.split(" ");
		if (tmp.length != 5) {
			logger.error("%s is not a valid command to check value in a CSV file (5 args are expected).",args);
			return;
		}
		path = tmp[0];
		column = tmp[1];
		line = tmp[2];
		operator = tmp[3];
		valueToCompare = Integer.parseInt(tmp[4]);
		String logMsg =String.format("CheckValueInCSV action - args :  %s : [%s, %s] %s %d", path, column, line, operator, valueToCompare);
		logger.info(logMsg);
	}

}
