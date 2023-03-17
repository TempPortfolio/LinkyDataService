package fr.linkyproject.dataservice.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	private Map<String, Command> commands = new HashMap<>();
	
	public void register(String name, Command cmd) {
		if(commands.containsKey(name))
			throw new IllegalArgumentException("Already registered coammand '" + name + "'");
		
		this.commands.put(name, cmd);
	}
	
	public Result call(String name, String[] args) {
		Command cmd = this.commands.get(name);
		
		if(cmd == null)
			return Result.UNKNOW;
		
		return cmd.onCommand(args);
	}
}
