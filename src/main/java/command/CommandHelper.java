package command;



public class CommandHelper {

	public static final String CSV = "csv";
	public static final String FAIL = "fail";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String HTML = "html";

	
	private CommandHelper() {
		throw new IllegalStateException("Utility class");
	}
	
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
		return in.substring(in.lastIndexOf(".")+1);
	}
	
	public static Boolean inputIsCSVFile(String in) {
		String extension = extension(in);
		return extension.contentEquals(CSV);
	}
}
