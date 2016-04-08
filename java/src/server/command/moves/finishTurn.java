package server.command.moves;

import org.json.simple.JSONObject;

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
				int playerIndex = ((Long) args.get("playerIndex")).intValue();
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

	@Override
	public void reExecute(Model game) {
		// TODO Auto-generated method stub
		
	}
}