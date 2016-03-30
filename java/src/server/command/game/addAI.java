package server.command.game;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;

public class addAI extends GameCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		if(validCookie(cookie))
		{
			throw new ServerAccessException("Can't Add AI's");
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
	}
}
