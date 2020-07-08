package command;

public class OutOfCSVException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfCSVException(String message) {
		super(message);		
	}
	
	public OutOfCSVException(String path, int indice) {
		this("Out of CVS Exception for" + path +" to" + indice);	
	}

	public OutOfCSVException(String path, String message) {
		this(message + "for : " + path);		
	}

}
