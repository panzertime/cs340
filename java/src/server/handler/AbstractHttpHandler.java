package server.handler;

import javax.xml.ws.spi.http.HttpHandler;

import org.json.simple.JSONObject;

import server.command.ICommand;

/**
 * This class allows the two sub classes,  getHandler and postHandler
 * access to the makeCommand function. It also allows them to extend 
 * HttpHandler for use in an HttpServer.
 *
 */
public abstract class AbstractHttpHandler extends HttpHandler {
	
	/**
	 * Uses the given string to create an ICommand object using Java's
	 * reflection class.
	 * @pre command is not null
	 * @post If the command is valid an ICommand object is created. Otherwise
	 * it returns null
	 * @param command the endpoint attached to the request
	 * @return The command associated with the request to be run
	 */
	ICommand makeCommand(String command) {
		return null;
	}
}
