package server.command.user;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;

public class register extends UserCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		if(validCredentials(args)) {
			String username = (String) args.get("username");
			String password = (String) args.get("password");
			User user = new User(username, password);
			ServerKernel.sole().addUser(user);
			try {
				this.cookie = new CatanCookie(user);
			} catch (CookieException e) {
				
			}
		} else {
			throw new ServerAccessException("Missing Username or password");
		}
		return "Success";
	}

	public boolean validCredentials(JSONObject args) {
		boolean result = false;
		try {
			String username = (String) args.get("username");
			String password = (String) args.get("password");
			if(username != null && !username.isEmpty() &&
					password != null && !password.isEmpty()) {
				result = true;
			}
		} catch (Exception e) {
			
		}
		return result;
	}

}
