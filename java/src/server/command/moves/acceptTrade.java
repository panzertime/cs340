package server.command.moves;

import org.json.simple.JSONObject;

import server.data.ServerKernel;
import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.exceptions.ViolatedPreconditionException;

public class acceptTrade extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie)
			throws ServerAccessException {
		String result = null;
		CatanCookie catanCookie;
		try {
			catanCookie = this.makeCatanCookie(cookie);
			if(validCookie(catanCookie)) {
				if(validMovesArguments(args, getClass().getSimpleName())) {
					try {
						boolean willAccept = (boolean) args.get("willAccept");
						Model game = getGameFromCookie(cookie);
						int playerIndex = 
								((Long) args.get("playerIndex")).intValue();
						try {
							game.doAcceptTrade(willAccept, playerIndex);
							arguments = args;
							int gameID = catanCookie.getGameID();
							ServerKernel.sole().persistCommand(gameID, this);
							JSONObject resultJSON = game.toJSON();
							result = resultJSON.toJSONString();
						} catch (ViolatedPreconditionException e) {
							throw new ServerAccessException("Unable to "
									+ "perform move");
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
		} catch (CookieException e1) {
			throw new ServerAccessException("Invalid Cookie");
		}
		return result;
	}

	@Override
	public void reExecute(Model game) {
		// TODO Auto-generated method stub
		
	}
}
