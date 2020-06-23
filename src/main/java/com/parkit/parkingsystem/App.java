package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <b>App is main class of the project. It is built to run the Park'it interface
 * in console.</b>
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class App {
	/**
	 * In this project, Logger will be call. It is important for a web
	 * project or app project. Indeed with Logger and Handler, it will follow and
	 * control all users actions.
	 * 
	 * getLogger : Method which have as argument the name of logger.
	 * 
	 * Level of standing :
	 * <ul>
	 * <li>7/ SEVERE</li>
	 * <li>6/ WARNING</li>
	 * <li>5/ INFO</li>
	 * <li>4/ CONFIG</li>
	 * <li>3/ FINE</li>
	 * <li>2/ FINER</li>
	 * <li>1/ FINEST</li>
	 * </ul>
	 * 
	 * @param logger Logger's name is "App"
	 * @since 1.0
	 */
	private static final Logger logger = LogManager.getLogger("App");

	/**
	 * The main method of this project.
	 * 
	 * logger.info : level of standing 5. Call InteractiveShell's method :
	 * loadInterface. It will open App's interface in console with customers
	 * choices.
	 * 
	 * @see InteractiveShell
	 * @param args array of string arguments
	 * @since 1.0
	 */
	public static void main(String args[]) {

		logger.info("Initializing Parking System");

		InteractiveShell.loadInterface();
	}
}
