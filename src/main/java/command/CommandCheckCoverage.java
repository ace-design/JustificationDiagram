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
		ArrayList<String> result = new ArrayList<String> ();
		
		
		int coverageFind = parsingHTMLJacocoReport();
		String isDone = "false";
		
		if(operator.equals("==")) {
			if(coverageFind == coverage) {
				isDone = "true";
			}
		}
		else if(operator.equals("<")) {
			if(coverageFind < coverage) {
				isDone = "true";
			}
		}
		else if(operator.equals("<=")) {
			if(coverageFind <= coverage) {
				isDone = "true";
			}
		}
		else if(operator.equals(">")) {
			if(coverageFind > coverage) {
				isDone = "true";
			}
		}
		else if(operator.equals(">=")) {
			if(coverageFind >= coverage) {
				isDone = "true";
			}
		}
		else if(operator.equals("!=")) {
			if(coverageFind != coverage) {
				isDone = "true";
			}
		}
		
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
	
	public int parsingHTMLJacocoReport() {
		
		int coverageFind = 0;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			System.err.println("The file '" + path + "' was not found.");
			return 0;
		}
		String line;
		try {
			while ((line = in.readLine()) != null)
			{
				  int position = line.indexOf("<td class=\"ctr2\">");
				  coverageFind = Integer.parseInt(line.substring(position +17,position+19));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return coverageFind;
		
	}
	
	public void setArgs(String args) {
		// example :
		//justification/IUTtest/test < 10
		String[] tmp = args.split(" ");
		
		path = tmp[0];
			
		operator = tmp[1];
		
		coverage = Integer.parseInt(tmp[2]);
	}

	
	
}
