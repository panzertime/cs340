package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class maritimeTrade extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		CatanCookie catanCookie;
		try {
			catanCookie = this.makeCatanCookie(cookie);
			if(validCookie(catanCookie)) {
				if(validMovesArguments(args, getClass().getSimpleName())) {
					Model game = getGameFromCookie(cookie);
					int playerIndex = 
							((Long) args.get("playerIndex")).intValue();
					
					try {
						ResourceType input = getResourceType
								(args.get("inputResource"));
						Long longRatio = (Long) args.get("ratio");
						Integer ratio = 
								(longRatio == null) ? 4 : longRatio.intValue();
						ResourceType output = getResourceType
								(args.get("outputResource"));
						game.doMaritimeTrade(ratio, input, output, playerIndex);
						persist(args, catanCookie, game);
						JSONObject resultJSON = game.toJSON();
						result = resultJSON.toJSONString();
					} catch (ViolatedPreconditionException e) {
						throw new ServerAccessException("Unable to "
								+ "perform move");
					}
				} else {
					throw new ServerAccessException("Invalid Parameters");
				}
			} else {
				throw new ServerAccessException("Invalid Cookie");
			}
		} catch (CookieException e1) {
			throw new ServerAccessException("Invalid Cookie");
		}
		return result;
	}

	@Override
	public void reExecute(Model game, JSONObject args) 
			throws ServerAccessException {
		if(validMovesArguments(args, getClass().getSimpleName())) {
			int playerIndex = 
					((Long) args.get("playerIndex")).intValue();
			
			try {
				ResourceType input = getResourceType
						(args.get("inputResource"));
				Long longRatio = (Long) args.get("ratio");
				Integer ratio = 
						(longRatio == null) ? 4 : longRatio.intValue();
				ResourceType output = getResourceType
						(args.get("outputResource"));
				game.doMaritimeTrade(ratio, input, output, playerIndex);
			} catch (ViolatedPreconditionException e) {
				throw new ServerAccessException("Unable to "
						+ "perform move");
			}
		} else {
			throw new ServerAccessException("Invalid Parameters");
		}
	}

	/*
	 * Takes in a string representing the name of a resource. If string is
	 * valid it will return a resource of that type, otherwise it is null. 
	 * @pre none
	 * @post a ResouceType is created
	 * @param resString value representing a string
	 * @return corresponding resource or null
	 */
	/*@Override
	public ResourceType getResourceType(Object resString) {
		ResourceType result = null;
		try {
			result = ResourceType.valueOf(((String) resString).toUpperCase());
		} catch (Exception e) {
			
		}
		
		return result;
	}*/
}
