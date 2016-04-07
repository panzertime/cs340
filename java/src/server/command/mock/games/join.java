package server.command.mock.games;

import org.json.simple.JSONObject;
import server.utils.*;
import server.exception.ServerAccessException;

public class join extends GamesCommand {

	@Override
	public String execute(JSONObject args, String cookie) throws ServerAccessException {
		return "Success";
	}

		private CatanCookie cookie;
	
	public CatanCookie getCookie() {
		return null;
	}

}
