package server.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import server.command.ICommand;
import server.exception.ServerAccessException;
import server.exception.UserException;
import server.utils.CookieException;


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
			String reply = new String();
			if (verb.equals("POST")) {
				reply = handlePost(exchange);				
			}
			else if (verb.equals("GET")) {
				reply = handleGet(exchange);	
			}
			else {
				logger.log(Level.INFO, "Incorrect HTTP verb in request: " + verb);
				throw new ServerAccessException(verb);
			}
				
		//	logger.log(Level.INFO, "Body has length " + reply.length());
			exchange.sendResponseHeaders(200, reply.length());
			packBody(exchange.getResponseBody(), reply);
			exchange.getResponseBody().close();
			exchange.close();

		}
		
		catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.INFO, "Problem in handler: " + e.getMessage());
			Headers oheaders = exchange.getResponseHeaders();
			oheaders.add(URLEncoder.encode("Content-type", "UTF-8"), URLEncoder.encode("application/json", "UTF-8"));
			exchange.sendResponseHeaders(400, e.getMessage().length());
			packBody(exchange.getResponseBody(), e.getMessage());			
			exchange.close();
		}

		
	}

	private String handleGet(HttpExchange exchange) throws IOException, ServerAccessException, UserException, 
			ClassNotFoundException, InstantiationException, IllegalAccessException {

		String URI = exchange.getRequestURI().getPath();
		logger.log(Level.INFO, "GET request to URI: " + URI);

		String endpoint = endpointToClassName(URI);

	//	logger.log(Level.INFO, "about to make command " + endpoint);

		Class proto_command = Class.forName(endpoint);
		ICommand command = (ICommand) proto_command.newInstance();

		String reply = new String();
		
		Headers headers = exchange.getRequestHeaders();
		String cookie = new String();
		if(!headers.get("Cookie").isEmpty()){
			cookie = headers.get("Cookie").get(0);
			cookie = URLDecoder.decode(cookie, "UTF-8"); 
		}

		if (URI.equals("/games/list")) {
			reply = command.execute(null, cookie);
		}
		else if (URI.equals("/game/model")) {
			// we'd have to actually check for a param here and set it
			String version = new String();
			version = exchange.getRequestURI().getQuery();
			if (version == null) {
				version = "";
			}
			if (!version.equals("") && !fakeFlag) {
				version = version.substring(8);
				server.command.game.model mCommand = (server.command.game.model) command;
				mCommand.setVersion(Integer.parseInt(version));
			}
			else if (!version.equals("")) {
				version = version.substring(8);
				server.command.mock.game.model mCommand = (server.command.mock.game.model) command;
				mCommand.setVersion(Integer.parseInt(version));
			}
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
		oheaders.add(URLEncoder.encode("Content-type", "UTF-8"), URLEncoder.encode("application/json", "UTF-8"));

		
		return reply;


	}

	private String handlePost(HttpExchange exchange) throws IOException, ServerAccessException, UserException, 
			ClassNotFoundException, InstantiationException, IllegalAccessException, CookieException {

		String URI = exchange.getRequestURI().getPath();
		logger.log(Level.INFO, "POST request to URI: " + URI);

		String endpoint = endpointToClassName(URI);
	//	logger.log(Level.INFO, "Endpoint selected: " + endpoint);

		Class proto_command = Class.forName(endpoint);
		ICommand command = (ICommand) proto_command.newInstance();
		
	///	logger.log(Level.INFO, "URI IS : ->" + URI + "<-");


		Headers headers = exchange.getRequestHeaders();
		String cookie = new String();
		if(headers.containsKey("Cookie")){
			if(!headers.get("Cookie").isEmpty()){
				cookie = headers.get("Cookie").get(0);
				cookie = URLDecoder.decode(cookie, "UTF-8"); 				
			}
		}
		
		String body = readBody(exchange.getRequestBody());

		JSONObject json = makeJSON(body);
		
		String reply = new String();
		String newCookie = new String();
		String gameCookie = new String();
		logger.log(Level.INFO, "URI IS : ->" + URI + "<-");

		if (URI.equals("/user/login")) {
			if (!fakeFlag) {
		//		logger.log(Level.INFO, "Doing the login command");
				server.command.user.UserCommand uCommand = (server.command.user.UserCommand) command;
				reply = uCommand.execute(json, cookie);
				newCookie = uCommand.getCookie().toCookie();
			}
			else {
		//		logger.log(Level.INFO, "Doing the login command");
				server.command.mock.user.UserCommand uCommand = (server.command.mock.user.UserCommand) command;
				reply = uCommand.execute(json, cookie);
				newCookie = uCommand.getCookie().toCookie();
			}
		}
		else if (URI.equals("/user/register")) {
			if (!fakeFlag) {
				server.command.user.UserCommand uCommand = (server.command.user.UserCommand) command;
				reply = uCommand.execute(json, cookie);
				newCookie = uCommand.getCookie().toCookie();
			}
			else {
				server.command.mock.user.UserCommand uCommand = (server.command.mock.user.UserCommand) command;
				reply = uCommand.execute(json, cookie);
				newCookie = uCommand.getCookie().toCookie();
			}
		}
		else if (URI.equals("/games/join")) {
			if (!fakeFlag) {
				server.command.games.join gCommand = (server.command.games.join) command;
				reply = command.execute(json, cookie);
				gameCookie = gCommand.getCookie().toCookie();
				newCookie = "";
			}
			else {
				server.command.mock.games.join gCommand = (server.command.mock.games.join) command;
				reply = command.execute(json, cookie);
				gameCookie = gCommand.getCookie().toCookie();
				newCookie = "";
			}	
		}
		else {
			reply = command.execute(json, cookie);
		}

		Headers oheaders = exchange.getResponseHeaders();
		//Check if text/html
		//or applicaton/json
		oheaders.add(URLEncoder.encode("Content-type", "UTF-8"), URLEncoder.encode("application/json", "UTF-8"));
		//oheaders.add(URLEncoder.encode("Content-type", "UTF-8"), "text/html");
		if (!newCookie.equals("")) {
			// set cookie header
	//		logger.log(Level.INFO, "Unencoded cookie: " + newCookie);
			String mutatedCookie = newCookie.substring(0, newCookie.length() - 8);
			mutatedCookie = URLEncoder.encode(mutatedCookie, "UTF-8");
		 	mutatedCookie += ";Path=/;"; 
	//		logger.log(Level.INFO, "Encoded cookie: " + mutatedCookie);
			oheaders.add(URLEncoder.encode("Set-cookie", "UTF-8"), mutatedCookie);
		}
		else if (!gameCookie.equals("")) { 
	//		logger.log(Level.INFO, "Game cookie: " + gameCookie);
			oheaders.add(URLEncoder.encode("Set-cookie", "UTF-8"), gameCookie);
		}

		return reply;

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

		return matchBrackets(JSONBuilder.toString());
	}

	private void packBody(OutputStream O, String data) throws IOException {
	//	logger.log(Level.INFO, "Packing " + data);
		DataOutputStream body = 
			new DataOutputStream(new BufferedOutputStream(O));
		//body.write(data.getBytes());
		body.writeBytes(data);
		body.flush();

	//	logger.log(Level.INFO, "Written: " + body.size());
		
	//	body.flush();
	//	body.close();
	//	O.flush();
	//	O.close();

	//	logger.log(Level.INFO, "Done packing.");


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

	private String matchBrackets(String matchable){
		char bracket = matchable.charAt(matchable.length() - 1);
		String closer;
		if(bracket == ']'){
			// System.out.println("Matching a [");		
			closer = "[";
		}
		else {
			// System.out.println("Matching a {");
			closer = "{";
		}
		return closer + matchable;
	}

	
}
