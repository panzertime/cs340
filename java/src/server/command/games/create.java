package server.command.games;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.exception.ServerAccessException;
import shared.model.Model;

import java.util.logging.*;


public class create extends GamesCommand {
	private Logger logger = Logger.getLogger("big server"); 
	

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		try {
			if(this.validCookie(cookie)) {
				String name = (String) args.get("name");
				Boolean tiles = (Boolean) args.get("randomTiles");
				Boolean numbers = (Boolean) args.get("randomNumbers");
				Boolean ports = (Boolean) args.get("randomPorts");
				if(name == null || 
						name.isEmpty() ||
						tiles == null ||
						numbers == null ||
						ports == null) {
					throw new ServerAccessException("Invalid Parameters");
				} else {
					Model newModel = new Model(tiles, numbers, ports, name);
					int gameID = ServerKernel.sole().putGame(newModel);
					Model finalModel = ServerKernel.sole().getGame(gameID);
					JSONObject jsonResult = finalModel.getGamesList();
					result = jsonResult.toJSONString();
				}
			} else {
				throw new ServerAccessException("Invalid Cookie");
			}
		} catch (Exception e) {
			logger.log(Level.INFO, "Problem in create game: " + e.getMessage());
			e.printStackTrace();
			throw new ServerAccessException(e.getMessage());
		}
		return result;
	}
}
