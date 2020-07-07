package parsing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class RealizationParserTest {


	String inputPath = "./src/test/resources/realization/";
	RealizationParser parser;
	
	@Test
	void testRealizationFile() throws ExceptionParsingRealizationFile {
		parser = new RealizationParser( inputPath+"example_realizationSimple.txt");
		List<String> labels = parser.getLabelList();
		assertEquals(3, labels.size());
		assertTrue(labels.get(0).contains(("Test Maven passed")),"position 0 : OK");
		assertTrue(labels.get(2).contains("Jacoco report Archivate"));
		
		
	}
	
	@Test
	void testNonEXISTINGFile() throws ExceptionParsingRealizationFile {
		assertThrows(ExceptionParsingRealizationFile.class, 
				() -> {new RealizationParser(inputPath+"notExist.txt");} );
	}
	
	@Test
	void testEmptyRealizationFile() throws ExceptionParsingRealizationFile {
		parser = new RealizationParser( inputPath+"empty.txt");
		List<String> labels = parser.getLabelList();
		assertEquals(0, labels.size());
		
	}
	
	

	

	

}
