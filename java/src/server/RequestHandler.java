package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import org.JSONSimple.*;
import org.JSONSimple.parser.*;
import shared.model.*;
import server.*;

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
			

		/* 

			this was a post request: extract body, submit thru facade.

		Object[] o = (Object[])xmlStream.fromXML(exchange.getRequestBody());
		userToken t = (userToken)o[0];
		batchProposal bp = (batchProposal)o[1];
		boolean b;
		
		
		try {
			b = facade.submitBatch(t, bp);
		}
		catch (ServerException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(b, exchange.getResponseBody());
		exchange.getResponseBody().close();
		*/
		
	}

	private void handleGet(HttpExchange exchange) throws IOException {
		// do the normal thing for a get request
		//	make command
		//	execute command
		//	pack request (i.e. exchange.getResponseBody();

		// use getPathInfo to get URI, trim the /, change to .
	}

	private void handlePost(HttpExchange exchange) throws IOException {
		// do the normal thing for a post request
		//	make command
		//	execute command
		//	pack request (i.e. exchange.getResponseBody();

		String URI = exchange.getPathInfo();
		logger.log(Level.INFO, "POST request to URI: " + URI);
		// transform URI somehow
		// respect fakeFlag

		String endpoint = endpointToClassName(URI);

		Class proto_command = Class.forName(endpoint);
		Command command = proto_command.newInstance();

		command.execute(cookie, json);


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

	
}
