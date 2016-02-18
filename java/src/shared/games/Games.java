package shared.games;

import java.util.List;

import org.json.simple.JSONObject;

public class Games {
	private List<Game> games;
	
	public Games(JSONObject games) {
		
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
}
