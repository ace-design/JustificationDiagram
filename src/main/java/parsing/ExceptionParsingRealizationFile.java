package parsing;

public class ExceptionParsingRealizationFile extends Exception {

	private String path;

	public ExceptionParsingRealizationFile(String realizationPath) {
		path = realizationPath;
	}

}
