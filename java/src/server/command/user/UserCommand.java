package server.command.user;

import server.command.ICommand;
import server.utils.CatanCookie;

/**
 * Class for keeping functions shared by login and register commands 
 * @author Joshua
 *
 */
public abstract class UserCommand implements ICommand {
	
	protected CatanCookie cookie;
	
	public CatanCookie getCookie() {
		return cookie;
	}
}
