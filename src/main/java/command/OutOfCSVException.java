package command;

public class OutOfCSVException extends Exception {
	private final String path;
	private final int indice;
	private String message = " Out of CVS";

	public String getPath() {
		return path;
	}
	
	public int getIndice() {
		return indice;
	}
	
	
	public OutOfCSVException(String path, int indice) {
		this.path=path;
		this.indice = indice;		
	}

	public OutOfCSVException(String path, String message) {
		this(path,-1);
		this.message = message;
		
		
	}

}
