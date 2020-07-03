package command;

public class OutOfCSVException extends Exception {
	String path;
	int indice;

	public OutOfCSVException(String path, int indice) {
		this.path=path;
		this.indice = indice;
	}

}
