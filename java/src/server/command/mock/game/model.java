package server.command.mock.game;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;

public class model extends GameCommand {

	@Override
 	public String execute(JSONObject args, String cookie) throws ServerAccessException {
		return null;
	}
}
