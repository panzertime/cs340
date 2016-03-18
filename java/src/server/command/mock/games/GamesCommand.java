package server.command.mock.games;

import org.json.simple.JSONObject;

import server.command.mock.Mock;

/**
 * Class for keeping common Games command functionality in the same place
 *
 */
public abstract class GamesCommand extends Mock {

	private static final String filePath = "games/";
	
	@Override
	public JSONObject jsonFromFile(String file) {
		return jsonFromFile(filePath, file);
	}
}
