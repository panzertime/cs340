package server.command.game;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;

public class model extends GameCommand {

	private int version;
	
	public model() {
		version = -1;
	}
	
	@Override
 	public String execute(JSONObject args, String cookie) 
 			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			Model game = getGameFromCookie(cookie);
			int gameVersion = game.getVersion();
			if(gameVersion == version) {
				result = "\"true\"";
			} else {
				JSONObject jsonGame = game.toJSON();
				result = jsonGame.toJSONString();
			}
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
		return result;
	}
	
	/**
	 * Gets a game from the given cookie
	 * @pre Cookie has already been validated
	 * @post nothing
	 * @param cookie cookie passed in from client
	 * @return Model corresponding the id in the cookie
	 */
	public Model getGameFromCookie(String cookie) {
		Model game = null;
			CatanCookie catanCookie;
			try {
				catanCookie = new CatanCookie(cookie, false);
				int gameID = catanCookie.getGameID();
				game = ServerKernel.sole().getGame(gameID);
			} catch (CookieException | ServerAccessException e) {
				//FOR DEBUG ONLY
				//System.err.println(e.getMessage());
			}
		return game;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}
