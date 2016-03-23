package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class Year_of_Plenty extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			if(validMovesArguments(args, getClass().getSimpleName())) {
				Model game = getGameFromCookie(cookie);
				int playerIndex = 
						((Long) args.get("playerIndex")).intValue();
				ResourceType resource1 = 
						getResourceType(args.get("resource1"));
				ResourceType resource2 = 
						getResourceType(args.get("resource2"));
				try {
					game.doYear_of_Plenty(resource1, resource2, playerIndex);
					JSONObject resultJSON = game.toJSON();
					result = resultJSON.toJSONString();
				} catch (ViolatedPreconditionException e) {
					throw new ServerAccessException("Unable to "
							+ "perform move");
				} catch (Exception e) {
					throw new ServerAccessException("Invalid Parameter: "
							+ "victimIndex");
				}
			} else {
				throw new ServerAccessException("Invalid Parameters");
			}
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
		return result;		
	}

}
