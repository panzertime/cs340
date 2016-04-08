package server.command.game;

import org.json.simple.JSONObject;

import server.data.AI;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;

public class addAI extends GameCommand {

	private CatanCookie cookie;

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		if(validCookie(cookie))
		{
			User user = new AI();
			ServerKernel.sole().addUser(user);
			try {
				this.cookie = new CatanCookie(user);
			} catch (CookieException e) {
			}	
			;
			//throw new ServerAccessException("Can't Add AI's");
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
		return "Sucess";
	}
}
