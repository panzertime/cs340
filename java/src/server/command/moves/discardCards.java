package server.command.moves;

import java.util.Map;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class discardCards extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			if(validMovesArguments(args, getClass().getSimpleName())) {
				Model game = getGameFromCookie(cookie);
				int playerIndex = 
						((Long) args.get("playerIndex")).intValue();
				Map<ResourceType, Integer> discardedCards = makeResourceList
						(args.get("discardedCards"));
				try {
					game.doDiscardCards(discardedCards, playerIndex);
					JSONObject resultJSON = game.toJSON();
					result = resultJSON.toJSONString();
				} catch (ViolatedPreconditionException e) {
					e.printStackTrace();
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

	@Override
	public void reExecute(Model game) {
		// TODO Auto-generated method stub
		
	}
}
