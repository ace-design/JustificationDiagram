package command;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author blay
 *
 */
public class CommandCheckValueInCSV implements Command {

	public CommandCheckValueInCSV() {
	};

	private String path;
	private String column;
	private String operator;
	private String line;
	private int valueToCompare;

	@Override
	public ArrayList<String> execute(String args) {
		boolean fail = false;
		setArgs(args);
		ArrayList<String> result = new ArrayList<>();

		// Read the CSV File
		if (!CommandHelper.inputIsCSVFile(path)) {
			result.add(CommandHelper.FAIL);
			return result;
		}

		CSVReader csvReader;
		try {
			csvReader = new CSVReader(path);
		} catch (Exception e) {
			System.err.println("error reading the file :" + path + "\n" + e);
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
					System.err.println("non valid case in csv" + e);
					result.add(CommandHelper.FAIL);
					return result;
				}
			} catch (OutOfCSVException e) {
				System.err.println("non valid case in csv" + e);
				result.add(CommandHelper.FAIL);
				return result;
			}
			// Chack value
			int valueInt;
			try {
				valueInt = Integer.parseInt(value);
			} catch (NumberFormatException e2) {
				System.err.println(value + " is not an integer");
				result.add(CommandHelper.FAIL);
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
			System.err.println(line + " is not a number for the line");
			result.add(CommandHelper.FAIL);
			return result;
		}

	}

	// todo : use logger
	// example
	// csvFile.scv "INSTRUCTION_MISSED" "1" "==" 546
	// csvFile.scv "3" "1" ">" 500
	private void setArgs(String args) {
		String[] tmp = args.split(" ");
		if (tmp.length != 5) {
			System.err.println(args + " is not a valid command to check value in a CSV file (5 args are expected).");
			return;
		}
		path = tmp[0];
		column = tmp[1];
		line = tmp[2];
		operator = tmp[3];
		valueToCompare = Integer.parseInt(tmp[4]);
		System.out.println("CheckValueInCSV action - args : '" + path + " column :" + column + " line : " + line
				+ "operator:" + operator + " value " + valueToCompare);
	}

}
