package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import shared.model.*;
import server.*;
import server.exception.*;

public class RequestHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("big server"); 
	
	@Override
	public RequestHandler(){
		this = super();
		fakeFlag = false;
	}

	private boolean fakeFlag;

	public void setFake(boolean isFake){
		fakeFlag = isFake;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String verb = exchange.getRequestMethod();
		
		if (verb.equals("HTTP_POST") {
			handlePost(exchange);
		}
		else if (verb.equals("HTTP_GET") {
			handleGet(exchange);
		}
		else {
			logger.log(Level.INFO, "Incorrect HTTP verb in request: " + verb_;
		}
			
		exchange.close();
		
	}

	private void handleGet(HttpExchange exchange) throws IOException, ServerAccessException, UserException {
		// do the normal thing for a get request
		//	make command
		//	execute command
		//	pack request (i.e. exchange.getResponseBody();

		// use getPathInfo to get URI, trim the /, change to .
	}

	private void handlePost(HttpExchange exchange) throws IOException, ServerAccessException, UserException {

		String URI = exchange.getPathInfo();
		logger.log(Level.INFO, "POST request to URI: " + URI);

		String endpoint = endpointToClassName(URI);

		Class proto_command = Class.forName(endpoint);
		Command command = proto_command.newInstance();

		String cookie = exchange.getRequestHeader("Cookie");
		String body = readBody(exchange.getRequestBody());

		JSONObject json = makeJSON(body);

		String reply = command.execute(json, cookie);

		packBody(exchange.getResponseBody(), reply);


	}

	// URI will still start with /
	private String endpointToClassName(String URI){
		URI = URI.subString(1);
		URI = URI.replace('/','.');
		if (fakeFlag) {
			URI = "mock." + URI;
		}
		else {
			URI = "commands." + URI;
		}
		return URI;
	}

	private String readBody(InputStream I){
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

		return JSONBuilder.toString():
	}

	private void packBody(OutputStream O, String data){
		OutputStream body = 
			new DataOutputStream(new BufferedOutputStream(O));
		body.write(data.getBytes());
		body.flush();
		body.close();
	}	

	private JSONObject makeJSON(String stringJSON)
			throws ServerProxyException{
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(stringJSON);
	
			return json;
		}
		catch(Exception e){
			// System.out.println("Problem parsing JSON: " + stringJSON);
			e.printStackTrace();
			throw new UserException("JSON probably invalid", e);
		}
	}
	
}
