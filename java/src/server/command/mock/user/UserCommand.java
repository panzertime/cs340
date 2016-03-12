package server.command.mock.user;

import org.json.simple.JSONObject;

import server.command.mock.Mock;

/**
 * Class for keeping functions shared by login and register commands
 * 
 * @author Joshua
 *
 */
public abstract class UserCommand extends Mock {

	private static final String filePath = "user/";
	
	@Override
	public JSONObject jsonFromFile(String file) {
		return jsonFromFile(filePath, file);
	}
}
