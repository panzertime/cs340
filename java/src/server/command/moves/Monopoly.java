package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class Monopoly extends MovesCommand {

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
					ResourceType resource = getResourceType
							(args.get("resource"));
					try {
						game.doMonopoly(resource, playerIndex);
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
	public void reExecute(Model game) 
			throws ServerAccessException {
		if(validMovesArguments(arguments, getClass().getSimpleName())) {
			int playerIndex = 
					((Long) arguments.get("playerIndex")).intValue();
			ResourceType resource = getResourceType
					(arguments.get("resource"));
			try {
				game.doMonopoly(resource, playerIndex);
			} catch (ViolatedPreconditionException e) {
				throw new ServerAccessException("Unable to "
						+ "perform move");
			}
		} else {
			throw new ServerAccessException("Invalid Parameters");
		}
	}
}
