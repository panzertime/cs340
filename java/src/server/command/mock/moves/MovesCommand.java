package server.command.mock.moves;

import server.command.ICommand;

/**
 * Class for keeping common move command functionality in the same place
 *
 */
public abstract class MovesCommand implements ICommand {

	/**
	 * Uses the passed string to check the database to see if the cookie
	 * parameters are valid
	 * @pre none
	 * @post the cookie is determined to be legitimate or not
	 * @param cookie the cookie string passed in the request
	 * @return whether or not the username, password, ID and gameID are valid
	 */
	public boolean validCookie(String cookie) {
		return false;
		
	}
}
