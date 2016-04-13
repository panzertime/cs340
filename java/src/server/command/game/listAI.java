package server.command.game;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;

public class listAI extends GameCommand {

	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			result = "[\"LargestArmyAI\", \"LongestRoadAI\", \"SettlementsAI\"]";
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
		
		return result;
	}
}
