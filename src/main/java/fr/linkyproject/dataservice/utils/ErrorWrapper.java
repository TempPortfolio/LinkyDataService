package fr.linkyproject.dataservice.utils;

@FunctionalInterface
public interface ErrorWrapper {
	public void execute() throws Throwable;
}