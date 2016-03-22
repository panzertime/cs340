package server.handler;

import com.sun.net.httpserver.HttpHandler;


import org.json.simple.JSONObject;

import server.command.ICommand;

/**
 * This class allows the two sub classes,  getHandler and postHandler
 * access to the makeCommand function. It also allows them to extend 
 * HttpHandler for use in an HttpServer.
 *
 */
public abstract class AbstractHttpHandler implements HttpHandler {
	
	public abstract void setFake(boolean isFake);

}
