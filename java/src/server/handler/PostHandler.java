package server.handler;

import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import org.json.simple.JSONObject;

import server.command.ICommand;

/**
 * Class for handling post commands.
 *
 */
public class PostHandler extends AbstractHttpHandler {

	/**
	 * It takes the address of the command and creates an ICommand from the
	 * endpoint. If JSON is passed it will create a JSONObject. It then
	 * runs execute on that command.
	 * @pre endpoint is valid
	 * @post If cookie and the corresponding JSONObject is valid, 
	 * changes will be made to the server and a 200 response will be sent.
	 * Otherwise it will send a 400 and the server will remain unchanged.
	 */
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Takes the JSON String passed in the HTTP body and creates
	 * a JSONObject out of it.
	 * @pre json is a valid json string(can be empty but not null)
	 * @post a JSONObject that corresponds to the string is created
	 * @param json the JSON string passed in the body
	 * @return The translated JSONObject 
	 */
	private JSONObject makeJSONFromString(String json){
		return null;
		
	}
}
