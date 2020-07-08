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
		System.out.println(values);
	}

}
