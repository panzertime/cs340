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
					System.out.println("Broken - 0");
					Model newModel = new Model(tiles, numbers, ports, name);
					System.out.println("Broken - 1");
					int gameID = ServerKernel.sole().putGame(newModel);
					System.out.println("Broken - 2");
					Model finalModel = ServerKernel.sole().getGame(gameID);
					System.out.println("Broken - 3");
					JSONObject jsonResult = finalModel.getGamesList();
					System.out.println("Broken - 4");
					result = jsonResult.toJSONString();
					System.out.println("Broken - 5");
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
