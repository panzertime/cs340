package server.command.moves;

import java.util.Map;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.board.hex.HexLocation;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class robPlayer extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie)
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
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
					JSONObject resultJSON = game.toJSON();
					result = resultJSON.toJSONString();
				} catch (ViolatedPreconditionException e) {
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
		return result;
	}
}
