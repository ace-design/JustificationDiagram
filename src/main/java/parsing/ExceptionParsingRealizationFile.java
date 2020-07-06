package parsing;

public class ExceptionParsingRealizationFile extends Exception {

	private final String path;

	public String getPath() {
		return path;
	}

	public ExceptionParsingRealizationFile(String realizationPath) {
		path = realizationPath;
	}

}
