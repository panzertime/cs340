package shared.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
	
	private static Logger logger;
	
	private static void initLog() {
		try {
			Level logLevel = Level.FINE;

			logger = Logger.getLogger("catan");
			logger.setLevel(logLevel);
			logger.setUseParentHandlers(false);

			Handler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(logLevel);
			consoleHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(consoleHandler);

			FileHandler fileHandler = new FileHandler("catan.log", false);
			fileHandler.setLevel(logLevel);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static Logger getLogger() {
		if (logger == null)
			initLog();
		return logger;
	}
	
	public static void error(Exception e) {
		getLogger().log(Level.SEVERE, e.getMessage(), e);
	}
	
	public static void error(String message) {
		getLogger().log(Level.SEVERE, message);
	}
	
	public static void debug(String message) {
		getLogger().log(Level.INFO, message);
	}
	
	public static void config(String message) {
		getLogger().log(Level.CONFIG, message);
	}
	
	public static void test(String message) {
		getLogger().log(Level.INFO, message);
	}

}
