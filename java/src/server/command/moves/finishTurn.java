package server.command.moves;

import org.json.simple.JSONObject;

import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;

public class finishTurn extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			if(validMovesArguments(args, getClass().getSimpleName())) {
				Model game = getGameFromCookie(cookie);
				User user = getUserFromCookie(cookie);
				int playerIndex = game.getIndexFromPlayerID(user.getID());
				try {
					game.doFinishTurn(playerIndex);
					JSONObject jsonResult = game.toJSON();
					result = jsonResult.toJSONString();
				} catch (ViolatedPreconditionException e) {
					throw new ServerAccessException("Player cannot end "
							+ "turn");
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
