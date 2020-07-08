package command;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import command.Command;
import command.CommandCheckCoverage;
import parsing.ExceptionParsingRealizationFile;
import parsing.RealizationParser;


class CommandCheckCoverageTest {

	
	Command c = new CommandCheckCoverage();
	
	String PATH = "./src/test/resources/jacoco/";
	
	@BeforeEach
	public void setUp() {
		c = new CommandCheckCoverage();
		
	}
	
	@Test
	void testCoverageInCSVisNotVerified() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv >= 2");
		assertEquals("false", result.get(0));
		assertEquals("[ ] Current coverage is 0,  it's not >= 2 ", result.get(1));
		
	}
	
	@Test
	void testCoverageInCSVIsVerified() {
		ArrayList<String> result = c.execute(PATH+ "jacocoSimple.csv >= 18");
		assertEquals("true", result.get(0));
		assertEquals("[x] Current coverage is 18, it's >= 18 ", result.get(1) );
	}
	
	@Test
	void testCoverageInComplexCSV() {
		ArrayList<String> result = c.execute(PATH+ "jacocoEclEmma.csv >= 90");
		assertEquals("true", result.get(0));
		assertEquals("[x] Current coverage is 95, it's >= 90 ", result.get(1) );
	}
	
	@Test
	void testCoverageFails() {
		ArrayList<String> result = c.execute(PATH+ "jacocoEclEmma2.csv >= 90");
		assertEquals("fail", result.get(0));
		assertTrue(result.get(1).contains("error Parsing CSV file"));
    }

	@Test
	void testJacocoCoverage4ThisProject() throws ExceptionParsingRealizationFile {
		ArrayList<String> result = c.execute("target/site/jacoco/jacoco.csv >= 80");
		assertEquals("true", result.get(0));
	}
	

}
