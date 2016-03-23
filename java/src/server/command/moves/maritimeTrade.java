package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class maritimeTrade extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			if(validMovesArguments(args, getClass().getSimpleName())) {
				Model game = getGameFromCookie(cookie);
				int playerIndex = 
						((Long) args.get("playerIndex")).intValue();
				
				try {
					ResourceType input = getResourceType
							((String) args.get("inputResource"));
					Integer ratio = (Integer) args.get("ratio");
					ratio = (ratio == null) ? 4 : ratio;
					ResourceType output = getResourceType
							((String) args.get("outputResource"));
					game.doMaritimeTrade(ratio, input, output, playerIndex);
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
		return result;
	}

	/**
	 * Takes in a string representing the name of a resource. If string is
	 * valid it will return a resource of that type, otherwise it is null. 
	 * @pre none
	 * @post a ResouceType is created
	 * @param resString value representing a string
	 * @return corresponding resource or null
	 */
	@Override
	public ResourceType getResourceType(String resString) {
		ResourceType result = null;
		try {
			result = ResourceType.valueOf(resString);
		} catch (Exception e) {
			
		}
		
		return result;
	}
}
