package parsing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import models.ActionNode;

class ActionNodeParsingTest {

	String path = "src/test/resources/action/";
	
	ActionNodeParsing anp ;
	
	@Test
	void test() {
		anp = new ActionNodeParsing(path + "actions.json");
		Map<String, ActionNode> values = anp.getActionNodes();
		assertTrue(values.containsKey("images Archivate"));
		assertTrue(values.containsKey("Validate testCoverage"));
		assertEquals("models.ActionNode",values.get("Validate testCoverage").getClass().getName());
		
	}

}
