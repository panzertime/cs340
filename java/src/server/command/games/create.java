package server.command.games;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.exception.ServerAccessException;
import shared.model.Model;


public class create extends GamesCommand { 
	

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
			System.out.println("Making new Model()");
					Model newModel = new Model(tiles, numbers, ports, name);
			System.out.println("setting game ID");
					int gameID = ServerKernel.sole().putGame(newModel);
			System.out.println("getting final model?");
					Model finalModel = ServerKernel.sole().getGame(gameID);
			System.out.println("setting jsonResult");
					JSONObject jsonResult = finalModel.getGamesList();
			System.out.println("setting result");
					result = jsonResult.toJSONString();
				}
			} else {
				throw new ServerAccessException("Invalid Cookie");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerAccessException(e.getMessage());
		}
		return result;
	}
}
