package command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class CommandFactoryTest {
	String PATH = "./src/test/resources/";
	CommandFactory cf = CommandFactory.getInstance();
	
	

	@Test
	void testCheckCoverage() {
		List<String> returnOfExecute = cf.executeCommand("CheckCoverage "+PATH+ "jacocoSimple.csv >= 18");
		assertEquals("true", returnOfExecute.get(0));
		assertEquals("[x] Current coverage is 18, it's >= 18 ", returnOfExecute.get(1));
	}
	
	@Test
	void testCheckCSV() {
		List<String> result = cf.executeCommand("CheckValueInCSV "+ PATH+ "jacocoComplexe0.csv INSTRUCTION_MISSED 1 == 546");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[INSTRUCTION_MISSED , 1] =546 ==546",result.get(1));
	}
	
	@Test
	void testChecklessCSV() {
		List<String> result = cf.executeCommand("CheckValueInCSV "+ PATH+ "jacocoComplexe0.csv INSTRUCTION_MISSED 1 <= 600");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[INSTRUCTION_MISSED , 1] =546 <=600",result.get(1));
	}
	
	
	@Test
	void testUnknownCommand() {
		List<String> result = cf.executeCommand("unexpected "+ PATH+ "jacocoComplexe0.csv INSTRUCTION_MISSED 1 <= 600");
		assertEquals("false", result.get(0));
		assertTrue(result.get(1).contains("command does not exist"));
	}
	
	

}
