package client.data;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.exceptions.BadJSONException;

public class Games {
	private List<GameInfo> games;
	private static Games SINGLETON;
	private Integer joinedGame;
	
	private List<GamesObserver> gamesObservers;
	private boolean hasChanged;
	
	public void registerObserver(GamesObserver gamesObservers) {
		this.gamesObservers.add(gamesObservers);
	}
	
	public void notifyObservers() {
		for(GamesObserver gamesObs : gamesObservers) {
			gamesObs.update();
		}
	}
	
	public void loadJSONGames(JSONArray games) throws BadJSONException {
		List<GameInfo> oldGames = this.games;
		this.games = new ArrayList<GameInfo>();
		for(Object game : games) {
			JSONObject jsonGame = (JSONObject) game;
			GameInfo newGame = new GameInfo(jsonGame);
			this.games.add(newGame);
		}
		
		if(this.games.equals(oldGames)) {
			this.hasChanged = false;
		} else {
			this.hasChanged = true;
		}
		
		notifyObservers();
	}
	
	public Games() {
		games = null;
		joinedGame = null;
		gamesObservers = new ArrayList<GamesObserver>();
		hasChanged = true;
	}

	public static Games sole() {
		if(SINGLETON == null) {
			SINGLETON = new Games();
		}
		return SINGLETON;
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

	public GameInfo[] getGameArray() {
		GameInfo[] result = null;
			result = games.toArray(new GameInfo[0]);
		return result;
	}

	public void getGamesFromServer() {
		try {
			JSONArray jsonGames = (JSONArray) 
					ServerFacade.get_instance().getGames();
			loadJSONGames(jsonGames);
		} catch (ServerException e) {
			System.err.println("Poller failed to get new games in Join "
					+ "Game State: " + e.getMessage());
			e.printStackTrace();
		} catch (BadJSONException e) {
			System.err.println("Poller failed to set new games in Join "
					+ "Game State: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setJoinedGame(int gameID) {
		joinedGame = gameID;
	}

	public boolean hasChanged() {
		return hasChanged;
	}
}
