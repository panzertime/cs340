package server.command.games;

import server.command.ICommand;
import server.data.ServerKernel;
import server.data.User;
import server.exception.UserException;
import server.utils.CatanCookie;
import server.utils.CookieException;

/**
 * Class for keeping common Games command functionality in the same place
 *
 */
public abstract class GamesCommand implements ICommand {

	//TODO Fix code duplication in GameCommand
	/**
	 * Uses the passed string to check the database to see if the cookie
	 * parameters are valid
	 * @pre none
	 * @post the cookie is determined to be legitimate or not
	 * @param cookie the cookie string passed in the request
	 * @return whether or not the username, password, and ID are valid
	 */
	public boolean validCookie(String cookie) {
		boolean result = false;
		try {
			CatanCookie catanCookie = new CatanCookie(cookie, true);
			User user = new User(catanCookie.getName(), 
					catanCookie.getPassword(), catanCookie.getUserID());
			result = ServerKernel.sole().userExists(user);
		} catch (UserException | CookieException e) {
			//DEBUG ONLY 
			//System.err.println(e.getMessage());
		}
		
		return result;
	}
}
