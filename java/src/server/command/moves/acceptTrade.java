package server.command.moves;

import org.json.simple.JSONObject;

import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;

public class acceptTrade extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie)
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			if(validMovesArguments(args, getClass().getSimpleName())) {
				try {
					boolean willAccept = (Boolean) args.get("willAccept");
					Model game = getGameFromCookie(cookie);
					User user = getUserFromCookie(cookie);
					int playerIndex = 
							((Long) args.get("playerIndex")).intValue();
					try {
						game.doAcceptTrade(willAccept, playerIndex);
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					throw new ServerAccessException("Invalid Parameters: "
							+ "willAccept");
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
