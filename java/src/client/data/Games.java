package client.data;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;

public class Games {
	private List<GameInfo> games;
	
	public Games(JSONArray games) throws BadJSONException {
		this.games = new ArrayList<GameInfo>();
		for(Object game : games) {
			JSONObject jsonGame = (JSONObject) game;
			GameInfo newGame = new GameInfo(jsonGame);
			this.games.add(newGame);

		}
	}

	public List<GameInfo> getGames() {
		return games;
	}

	public void setGames(List<GameInfo> games) {
		this.games = games;
	}
	
	public JSONArray toJSON() {
		JSONArray result = new JSONArray();
		for(GameInfo game : games) {
			result.add(game.toJSON());
		}
		return result;
	}
}
