package server.command.mock.game;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;

public class model extends GameCommand {

	private int version;
	
	public model() {
		version = -1;
	}

	@Override
 	public String execute(JSONObject args, String cookie) throws ServerAccessException {
		return null;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
