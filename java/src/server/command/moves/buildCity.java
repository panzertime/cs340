package server.command.moves;

import org.json.simple.JSONObject;

import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;

public class buildCity extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;

		return result;
	}

}
