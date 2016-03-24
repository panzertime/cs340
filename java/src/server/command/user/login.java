package server.command.user;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;

public class login extends UserCommand {

	@Override
	public String execute(JSONObject args, String cookie) throws ServerAccessException {
		String username = (String) args.get("username");
		String password = (String) args.get("password");
		User user = new User(username, password);
		if (!ServerKernel.sole().passwordsMatch(user))
			return "Failed to login - bad username or password.";
		try {
			this.cookie = new CatanCookie(user);
		} catch (CookieException e) {
			throw new ServerAccessException("User has invalid information");
		}
		return "Success";
	}

}
