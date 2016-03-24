package server.command.games;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.exception.ServerAccessException;

public class list extends GamesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(this.validCookie(cookie)) {
			JSONArray games = ServerKernel.sole().getGames();
			result = games.toJSONString();
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
		
		return result;
	}

}
