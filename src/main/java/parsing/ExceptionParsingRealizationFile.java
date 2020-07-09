package parsing;

public class ExceptionParsingRealizationFile extends Exception {

	private final String path;

	public ExceptionParsingRealizationFile(String realizationPath) {
		path = realizationPath;
	}

	@Override
	public String toString() {
		return "ExceptionParsingRealizationFile [path=" + path + "]";
	}

}
