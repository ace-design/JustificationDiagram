package command;

public class CommandHelper {

	public static final String CSV = "csv";
	public static final String FAIL = "fail";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static boolean compareInt (int value1, int value2, String operator) {		
	if(operator.equals("=="))
			return value1 == value2;
	if(operator.equals("<") )
			return value1 < value2;
	 if(operator.equals("<="))
		 return value1 <= value2;
	if(operator.equals(">"))
			return value1 > value2;
	if(operator.equals(">=") )
			return value1 >= value2;
	if(operator.equals("!=") )
		return value1 != value2;

		return false;
	}
	
	
	public static String extension(String in) {
		System.out.println("check extension for :" + in);
		return in.substring(in.lastIndexOf(".")+1);
	}
	
	public static Boolean inputIsCSVFile(String in) {
		if (extension(in).contentEquals(CSV))
			return true;
		else 
			System.err.println(in + " is not a valid name."+ " Unexpected extension :"+ CommandHelper.extension(in));
		return false;
	}
}
