package command;

import java.util.ArrayList;

public interface Command {
	ArrayList<String> execute(String args);
}
