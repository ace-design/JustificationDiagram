package command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandFactory {

    private static CommandFactory instance; //Singleton
	private final HashMap<String, Command>	commands;
	
	private final String ANTECEDENT = "command.Command";
	
	private CommandFactory() {
		this.commands = new HashMap<>();
	}

	public void addCommand(String name, Command command) {
		this.commands.put(name, command);
	}
	
	public ArrayList<String> executeCommand(String commandLigne) {
		
		String commandName = commandLigne.split(" ")[0];
		String args = "";
			
		args = commandLigne.replace(commandName + " ", "");
		
		//todo : improve with args
		//Use reflexivity
		try {
			Class<?> commandClass = Class.forName(ANTECEDENT+commandName);
			Command command = (Command) commandClass.getDeclaredConstructor().newInstance();
			return command.execute(args);
		} 
		catch (InstantiationException | IllegalAccessException |ClassNotFoundException |IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				return unexpectedBehavior(commandName, args, e);
			}
	}
	

	private ArrayList<String> unexpectedBehavior(String commandName, String args, Exception e) {
		System.err.println("This command does not exist or there is a problem with args, please check the command validity : \n" + e +"\n");
		ArrayList<String> result = new ArrayList<> ();
		result.add("false");
		result.add(commandName + " this command does not exist with " + args);
		return result;
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
