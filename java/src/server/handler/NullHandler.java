package server.handler;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;


import org.json.simple.JSONObject;

import server.command.ICommand;

public class NullHandler extends AbstractHttpHandler {
	
	@Override
	public void setFake(boolean isFake){
		return;
	}

	@Override
	public void handle(HttpExchange exchange){
		return;
	}

}
