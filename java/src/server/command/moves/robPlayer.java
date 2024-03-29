package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.ViolatedPreconditionException;

public class robPlayer extends MovesCommand {

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
					HexLocation robLocation = 
							makeHexLocation(args.get("location"));
					try {
						int victimIndex = 
								((Long) args.get("victimIndex")).intValue();
						game.doRobPlayer(robLocation, victimIndex, playerIndex);
						persist(args, catanCookie, game);
						JSONObject resultJSON = game.toJSON();
						result = resultJSON.toJSONString();
					} catch (ViolatedPreconditionException e) {
						e.printStackTrace();
						throw new ServerAccessException("Unable to "
								+ "perform move");
					} catch (Exception e) {
						throw new ServerAccessException("Invalid Parameter: "
								+ "receiver");
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
			HexLocation robLocation = 
					makeHexLocation(arguments.get("location"));
			try {
				int victimIndex = 
						((Long) arguments.get("victimIndex")).intValue();
				game.doRobPlayer(robLocation, victimIndex, playerIndex);
			} catch (ViolatedPreconditionException e) {
				e.printStackTrace();
				throw new ServerAccessException("Unable to "
						+ "perform move");
			} catch (Exception e) {
				throw new ServerAccessException("Invalid Parameter: "
						+ "receiver");
			}
		} else {
			throw new ServerAccessException("Invalid Parameters");
		}
	}
}
