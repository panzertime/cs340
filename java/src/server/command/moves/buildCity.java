package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.ViolatedPreconditionException;

public class buildCity extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
			if(validMovesArguments(args, getClass().getSimpleName())) {
				Model game = getGameFromCookie(cookie);
				int playerIndex = 
						((Long) args.get("playerIndex")).intValue();
				VertexLocation vertexLocation = makeVertexLocation
						((JSONObject) args.get("vertexLocation"));
				try {
					game.doBuildCity(vertexLocation, playerIndex);
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
}
