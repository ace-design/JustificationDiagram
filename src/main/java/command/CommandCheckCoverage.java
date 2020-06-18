package command;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CommandCheckCoverage implements Command{

	public String path;
	public String operator;
	public int coverage;	
	
	public CommandCheckCoverage(String path, int coverage) {
		this.path = path;
		this.coverage = coverage;
	}
	
	
	public CommandCheckCoverage() {
		this.path = null;
	}
	
	@Override
	public ArrayList<String> execute(String args) {
		
		setArgs(args);
		ArrayList<String> result = new ArrayList<> ();
		
		
		int coverageFind = parsingHTMLJacocoReport();
		
		String isDone = searchIdItsDone(coverageFind);
		
		String log = "";
		if(isDone.contains("true")) {
			// [x] Current coverage is 80, it's > 50
			log = "[x] Current coverage is " + coverageFind + ", it's " + operator + " " + coverage;
		}
		else {
			// [ ] Current coverage is 30, it's not > 50
			log = "[ ] Current coverage is " + coverageFind + ",  it's not " + operator + " " + coverage;
		}
		
		result.add(isDone);
		result.add(log);
		
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
				e.printStackTrace();
			}
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return coverageFind;
		
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
		
		path = tmp[0];
			
		operator = tmp[1];
		
		coverage = Integer.parseInt(tmp[2]);
	}
	
	private static boolean inputIsValid(String in) {
		if (!in.matches(".*\\.(html)")) {
			System.err.println(in + " is not a valid name. Please indicate the index.html of the jacoco report.");
			return false;
		}
		return true;
	}


	
	
}
