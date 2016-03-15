package server.handler;

import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;

/**
 * The handler to be used to make a get request. It never requires any
 * parameters other than a occasional cookie.
 *
 */
public class GetHandler extends AbstractHttpHandler {

	/**
	 * handles the get requests by converting the endpoint into an ICommand.
	 * It then runs that ICommand.
	 * @pre exchange is not null
	 * @post the given post command will return an appropriate message and 
	 * response depending on the command and cookie it is given.
	 * The database will never be changed.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}
}
