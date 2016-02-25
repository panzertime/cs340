package client.data;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
	private int id;
	private String title;
	private List<PlayerInfo> players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}
	
	public GameInfo(JSONObject jsonGame) throws BadJSONException {
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
		this.players = new ArrayList<PlayerInfo>();
		for(Object player : players) {
			JSONObject jsonPlayer = (JSONObject) player;
			PlayerInfo tmpPlayer = new PlayerInfo(jsonPlayer);
			if(tmpPlayer.exists())
			{
				this.players.add(tmpPlayer);
			} //else, dne, do not add
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
		for(PlayerInfo player : players) {
			result.add(player.toJSON());
		}
		return result;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void addPlayer(PlayerInfo newPlayer)
	{
		players.add(newPlayer);
	}
	
	public List<PlayerInfo> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}

	public boolean isFull() {
		boolean result = false;
		int numOfPlayers = players.size();
		if(numOfPlayers == 4) {
			result = true;
		}
		
		return result;
	}
}

