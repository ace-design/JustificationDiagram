package command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CommandFactoryTest {
	String PATH = "./src/test/resources/";
	CommandFactory cf = CommandFactory.getInstance();
	
	

	@Test
	void testCheckCoverage() {
		ArrayList<String> returnOfExecute = cf.executeCommand("CheckCoverage "+PATH+ "jacocoSimple.csv >= 18");
		assertEquals("true", returnOfExecute.get(0));
		assertEquals("[x] Current coverage is 18, it's >= 18 ", returnOfExecute.get(1));
	}
	
	@Test
	void testCheckCSV() {
		ArrayList<String> result = cf.executeCommand("CheckValueInCSV "+ PATH+ "jacocoComplexe0.csv INSTRUCTION_MISSED 1 == 546");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[INSTRUCTION_MISSED , 1] =546 ==546",result.get(1));
	}
	
	@Test
	void testChecklessCSV() {
		ArrayList<String> result = cf.executeCommand("CheckValueInCSV "+ PATH+ "jacocoComplexe0.csv INSTRUCTION_MISSED 1 <= 600");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[INSTRUCTION_MISSED , 1] =546 <=600",result.get(1));
	}
	

}
