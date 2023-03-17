package fr.linkyproject.dataservice.utils;

import java.util.logging.Logger;

public final class Errors {
	private static final Logger LOGGER = Logger.getLogger("Errors");
	
	public static void error(Throwable th, String message) {
		LOGGER.severe(message + " -> " + th.getMessage());
		th.printStackTrace();
		System.exit(0);
	}
	
	public static void error(String message) {
		LOGGER.severe(message);
		System.exit(0);
	}
	
	public static void trycatch(ErrorWrapper ew, String message) {
		try {
			ew.execute();
		}
		catch(Throwable th) {
			error(th, message);
		}
	}
	
	private Errors() {}
}
