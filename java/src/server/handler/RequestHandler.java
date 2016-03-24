package server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;
import java.util.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;


import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import org.json.simple.*;
import org.json.simple.parser.*;
import shared.model.*;
import server.*;
import server.exception.*;
import server.command.*;
import server.command.user.*;
import server.command.game.*;
import server.command.games.*;
import server.command.moves.*;
import server.command.mock.*;
import server.command.mock.user.*;
import server.command.mock.game.*;
import server.command.mock.games.*;
import server.command.mock.moves.*;
import server.utils.*;


public class RequestHandler extends AbstractHttpHandler {

	private Logger logger = Logger.getLogger("big server"); 
	
//	public RequestHandler(){
//		super();
	//	this = super;
//		fakeFlag = false;
//	}

	private boolean fakeFlag = false;

	@Override
	public void setFake(boolean isFake){
		fakeFlag = isFake;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String verb = exchange.getRequestMethod();
		try{	
			if (verb.equals("POST")) {
				handlePost(exchange);
			}
			else if (verb.equals("GET")) {
				handleGet(exchange);
			}
			else {
				logger.log(Level.INFO, "Incorrect HTTP verb in request: " + verb);
				throw new ServerAccessException(verb);
			}
				
			exchange.sendResponseHeaders(200, 0);
			exchange.close();

		}
		
		catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.INFO, "Problem in handler: " + e.getMessage());
			packBody(exchange.getResponseBody(), e.getMessage());
			exchange.sendResponseHeaders(400, -1);
			exchange.close();
		}

		
	}

	private void handleGet(HttpExchange exchange) throws IOException, ServerAccessException, UserException, 
			ClassNotFoundException, InstantiationException, IllegalAccessException {

		String URI = exchange.getRequestURI().getPath();
		logger.log(Level.INFO, "POST request to URI: " + URI);

		String endpoint = endpointToClassName(URI);

		logger.log(Level.INFO, "about to make command " + endpoint);

		Class proto_command = Class.forName(endpoint);
		ICommand command = (ICommand) proto_command.newInstance();

		String reply = new String();
		
		Headers headers = exchange.getRequestHeaders();
		String cookie = new String();
		if(!headers.get("Cookie").isEmpty()){
			cookie = headers.get("Cookie").get(0);
		}

		if (URI.equals("/games/list")) {
			reply = command.execute(null, cookie);
		}
		else if (URI.equals("/game/model")) {
			// we'd have to actually check for a param here and set it
			reply = command.execute(null, cookie);

			// we expect this to sometimes just return "true", which is not JSON
		}
		else if (URI.equals("/game/commands")) {
			reply = command.execute(null, cookie);
		}
		else if (URI.equals("/game/listAI")) {
			reply = command.execute(null, cookie);
		}
		else {
			throw new UserException("bad API endpoint");
		}
		
		ArrayList<String> head = new ArrayList<String>();
		head.add("application/json");
		Headers oheaders = exchange.getResponseHeaders();
		oheaders.put("Content­Type", head);

		packBody(exchange.getResponseBody(), reply);

	}

	private void handlePost(HttpExchange exchange) throws IOException, ServerAccessException, UserException, 
			ClassNotFoundException, InstantiationException, IllegalAccessException, CookieException {

		String URI = exchange.getRequestURI().getPath();
		logger.log(Level.INFO, "POST request to URI: " + URI);

		String endpoint = endpointToClassName(URI);
		logger.log(Level.INFO, "Endpoint selected: " + endpoint);

		Class proto_command = Class.forName(endpoint);
		ICommand command = (ICommand) proto_command.newInstance();
		
		logger.log(Level.INFO, "URI IS : ->" + URI + "<-");


		Headers headers = exchange.getRequestHeaders();
		String cookie = new String();
		if(!headers.get("Cookie").isEmpty()){
			cookie = headers.get("Cookie").get(0);
		}

		String body = readBody(exchange.getRequestBody());

		JSONObject json = makeJSON(body);
		
		String reply = new String();
		String newCookie = new String();
		logger.log(Level.INFO, "URI IS : ->" + URI + "<-");

		if (URI.equals("/user/login")) {
			logger.log(Level.INFO, "Doing the login command");
			server.command.user.UserCommand uCommand = (server.command.user.UserCommand) command;
			reply = uCommand.execute(null, cookie);
			newCookie = uCommand.getCookie().toCookie();
		}
		else if (URI.equals("/user/register")) {
			server.command.user.UserCommand uCommand = (server.command.user.UserCommand) command;
			reply = uCommand.execute(null, cookie);
			newCookie = uCommand.getCookie().toCookie();

		}
		else if (URI.equals("/games/join")) {
		//	GameCommand gCommand = (GameCommand) command;
			reply = command.execute(null, cookie);
		//	newCookie = gCommand.getCookie().toCookie();
		}
		else {
			reply = command.execute(json, cookie);
		}

		ArrayList<String> head = new ArrayList<String>();
		head.add("application/json");
		Headers oheaders = exchange.getResponseHeaders();
		oheaders.put("Content­Type", head);
		if (!newCookie.equals("")){
			// set cookie header
			head.clear();
			head.add(newCookie);
			oheaders.put("Set-cookie", head);
		}

		packBody(exchange.getResponseBody(), reply);


	}

	// URI will still start with /
	private String endpointToClassName(String URI){
		URI = URI.substring(1);
		URI = URI.replace('/','.');
		if (fakeFlag) {
			URI = "server.command.mock." + URI;
		}
		else {
			URI = "server.command." + URI;
		}
		return URI;
	}

	private String readBody(InputStream I) throws IOException {
		DataInputStream body = 
				new DataInputStream(new BufferedInputStream(I));

		StringBuilder JSONBuilder = new StringBuilder();
		InputStreamReader JSONReader = new InputStreamReader(body);
		int letter = JSONReader.read();
			
		letter = JSONReader.read();
		while(letter >= 0){
			JSONBuilder.append((char) letter);
			letter = JSONReader.read();
		}
		JSONReader.close();

		return JSONBuilder.toString();
	}

	private void packBody(OutputStream O, String data) throws IOException {
		OutputStream body = 
			new DataOutputStream(new BufferedOutputStream(O));
		body.write(data.getBytes());
		body.flush();
		body.close();
	}	

	private JSONObject makeJSON(String stringJSON)
			throws UserException{
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(stringJSON);
	
			return json;
		}
		catch(Exception e){
			// System.out.println("Problem parsing JSON: " + stringJSON);
			e.printStackTrace();
			throw new UserException("JSON probably invalid");
		}
	}
	
}
