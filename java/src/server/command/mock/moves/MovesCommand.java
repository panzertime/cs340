package server.command.mock.moves;

import org.json.simple.JSONObject;

import server.command.mock.Mock;

/**
 * Class for keeping common move command functionality in the same place
 *
 */
public abstract class MovesCommand extends Mock {

	private static final String filePath = "moves/";
	
	@Override
	public JSONObject jsonFromFile(String file) {
		return jsonFromFile(filePath, file);
	}
}
