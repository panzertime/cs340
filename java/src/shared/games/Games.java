package shared.games;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;

public class Games {
	private List<Game> games;
	
	public Games(JSONArray games) throws BadJSONException {
		this.games = new ArrayList<Game>();
		for(Object game : games) {
			JSONObject jsonGame = (JSONObject) game;
			Game newGame = new Game(jsonGame);
			this.games.add(newGame);

		}
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	public JSONArray toJSON() {
		JSONArray result = new JSONArray();
		for(Game game : games) {
			result.add(game.toJSON());
		}
		return result;
	}
}
