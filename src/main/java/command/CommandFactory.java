package command;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandFactory {

    private static CommandFactory instance; //Singleton
	private final HashMap<String, Command>	commands;
	
	private CommandFactory() {
		this.commands = new HashMap<>();
	}

	public void addCommand(String name, Command command) {
		this.commands.put(name, command);
	}
	
	public ArrayList<String> executeCommand(String commandLigne) {
		
		String name = commandLigne.split(" ")[0];
		String args = "";
			
		args = commandLigne.replace(name + " ", "");
		
		//todo
		//Use reflexivity
		if ( this.commands.containsKey(name) ) {
			return this.commands.get(name).execute(args);
		}
		else {
			System.err.println("This command does not exist, please check the command validity : \n" );
			this.listCommands();
			ArrayList<String> result = new ArrayList<> ();
			result.add("false");
			result.add(name + " this command does not exist");
			return result;
		}
	}

	public void listCommands() {
		// using stream (Java 8)
		System.out.println("Commands enabled :");
		this.commands.keySet().stream().forEach(System.out::println);
	}
	 
	/* Singleton pattern */
    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }
	
    //TODO = Why???
	/* Factory pattern */
	public void create() {
		// commands are added here using lambda. It also possible to dynamically add commands without editing code.
		addCommand("CheckCoverage", new CommandCheckCoverage());
	}
}
