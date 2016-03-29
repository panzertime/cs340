package server.command.games;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import shared.model.Model;
import shared.model.definitions.CatanColor;

public class join extends GamesCommand {
	
	private CatanCookie cookie;
	
	public CatanCookie getCookie() {
		return cookie;
	}

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(this.validCookie(cookie)) {
			CatanColor color = makeCatanColor(args.get("color"));
			User user = this.getUserFromCookie(cookie);
			try {
				int gameToJoin = ((Long) args.get("id")).intValue();
				if(ServerKernel.sole().gameExists(gameToJoin)) {
					Model game = ServerKernel.sole().getGame(gameToJoin);
					this.cookie = new CatanCookie(game);
					game.joinGame(user.getID(), user.getUsername(), color);
					result = "Success";
				} else {
					throw new ServerAccessException("Invalid Game");
				}
			} catch (Exception e) {
				throw new ServerAccessException("Invalid Cookie");
			}
		}
		
		return result;
	}

	private CatanColor makeCatanColor(Object jsonColor) 
			throws ServerAccessException {
		CatanColor result = null;
		try{
			String colorString = (String) jsonColor;
			result = CatanColor.valueOf(colorString.toUpperCase());
		} catch (Exception e) {
			throw new ServerAccessException("Invalid Parameter: color");
		}
		return result;
	}
}
