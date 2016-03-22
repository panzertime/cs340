package server.command.moves;

import server.command.ICommand;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;
import server.utils.CatanCookie;
import server.utils.CookieException;

/**
 * Class for keeping common move command functionality in the same place
 *
 */
public abstract class MovesCommand implements ICommand {

	/**
	 * Uses the passed string to check the database to see if the cookie
	 * parameters are valid
	 * @pre the cookie passed in has username cookie and game cookie
	 * @post the cookie is determined to be legitimate or not
	 * @param cookie the cookie string passed in the request
	 * @return whether or not the username, password, ID and gameID are valid
	 */
	public boolean validCookie(String cookie) {
		boolean userExists = false;
		boolean userInGame = false; 
		try {
			CatanCookie catanCookie = new CatanCookie(cookie, false);
			User user = new User(catanCookie.getName(), 
					catanCookie.getPassword(), catanCookie.getUserID());
			userExists = ServerKernel.sole().userExists(user);
			int gameID = catanCookie.getGameID();
			if(ServerKernel.sole().gameExists(gameID)) {
				userInGame = ServerKernel.sole().userIsInGame(gameID, user);
			}
		} catch (UserException | CookieException | ServerAccessException e) {
			//FOR DEBUG ONLY
			//System.err.println(e.getMessage());
		}
		
		return userExists && userInGame;
	}
	
}
