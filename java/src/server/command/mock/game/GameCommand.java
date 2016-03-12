package server.command.mock.game;

import org.json.simple.JSONObject;

import server.command.mock.Mock;

/**
 * Class to keep similar functions used by the game commands in one location to
 * avoid code duplication.
 *
 */
public abstract class GameCommand extends Mock {
	
	private static final String filePath = "game/";
	
	@Override
	public JSONObject jsonFromFile(String file) {
		return jsonFromFile(filePath, file);
	}
}
