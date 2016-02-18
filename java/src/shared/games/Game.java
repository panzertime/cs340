package shared.games;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;

public class Game {

	private String title;
	private int id;
	private List<SimplePlayer> players;
	
	public Game(JSONObject jsonGame) throws BadJSONException {
		String title = (String) jsonGame.get("title");
		Long id = (Long) jsonGame.get("id");
		
		if(title == null) {
			throw new BadJSONException("Null Game Title");
		} else if (id == null) {
			throw new BadJSONException("Null Game ID");
		}
		
		this.title = title;
		this.id = id.intValue();
		
		JSONArray players = (JSONArray) jsonGame.get("players");
		this.players = new ArrayList<SimplePlayer>();
		for(Object player : players) {
			JSONObject jsonPlayer = (JSONObject) player;
			SimplePlayer simplePlayer = new SimplePlayer(jsonPlayer);
			this.players.add(simplePlayer);
		}
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		result.put("title", title);
		result.put("id", id);
		JSONArray players = playersToJSON();
		result.put("players", players);
		return result;
	}

	private JSONArray playersToJSON() {
		JSONArray result = new JSONArray();
		for(SimplePlayer player : players) {
			result.add(player.toJSON());
		}
		return result;
	}
}
