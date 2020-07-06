package command;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import command.Command;
import command.CommandCheckCoverage;


class CommandCheckValueInCSVTest {

	
	Command c = new CommandCheckValueInCSV();
	
	String PATH = "./src/test/resources/";
	
	@BeforeEach
	public void setUp() {
		c = new CommandCheckValueInCSV();
	}
	
	@Test
	void testPropertyEqualsInCSVisVerified() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv INSTRUCTION_MISSED 1 == 546");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[INSTRUCTION_MISSED , 1] =546 ==546",result.get(1));
	}
	@Test
	void testPropertyEqualsInCSVisVerified2() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv 3 1 == 546");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[3 , 1] =546 ==546",result.get(1));
	}
	
	@Test
	void testPropertyOutOfCVS() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv 3 100 == 546");
		assertEquals("fail", result.get(0));
		assertTrue(result.get(1).contains("OutOfCSVException"));
	}
	
	@Test
	void testPropertyNonExpectedValueOfCVS() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv Coverage 100 == 546");
		assertEquals("fail", result.get(0));
		assertTrue(result.get(1).contains("Reference to an invalid Cell"));
	}
	
	
	@Test
	void testPropertylessInCSVisVerified() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv 3 3 >= 50");
		assertEquals("true", result.get(0));
		assertEquals("[x] following property is verified : value[3 , 3] =63 >=50",result.get(1));
	}
	
	@Test
	void testPropertylessInCSVisNotVerified() {
		ArrayList<String> result = c.execute(PATH+ "jacocoComplexe0.csv 3 3 >= 100");
		assertEquals("false", result.get(0));
		assertEquals("[] following property is not verified : value[3 , 3] =63 >=100",result.get(1));
	}
	
	
	@Test
	void testPropertyFails() {
		ArrayList<String> result = c.execute(PATH+ "jacocoEclEmma2.csv 3 3 >= 90");
		assertEquals("fail", result.get(0));
    }
	
	@Test
	void testNotCsvFile() {
		ArrayList<String> result = c.execute(PATH+ "jacocoEclEmma2.html 3 3 >= 90");
		assertEquals("fail", result.get(0));
    }

	
	//TODO manage IOexception
	//@Test
	/* void testCoverageThrowException() {
		ArrayList<String> result = c.execute("./src/test/resources/jacocoEclEmma2.csv >= 90");
		System.out.println("----------------------------- " + result);
		Assertions.assertThrows(java.io.FileNotFoundException.class, () -> {
		   c.execute("./src/test/resources/jacocoEclEmma2.csv >= 90");} );
	}
	*/

}
