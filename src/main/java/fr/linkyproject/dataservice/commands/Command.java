package fr.linkyproject.dataservice.commands;

@FunctionalInterface
public interface Command {
	public Result onCommand(String[] args);
}
