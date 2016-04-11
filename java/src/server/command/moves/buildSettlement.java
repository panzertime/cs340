package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.ViolatedPreconditionException;

public class buildSettlement extends MovesCommand {

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
					VertexLocation vertexLocation = makeVertexLocation
							(args.get("vertexLocation"));
					try {
						boolean free = (boolean) args.get("free");
						game.doBuildSettlement(free, vertexLocation, playerIndex);
						persist(args, catanCookie, game);
						JSONObject resultJSON = game.toJSON();
						result = resultJSON.toJSONString();
					} catch (ViolatedPreconditionException e) {
						throw new ServerAccessException("Unable to "
								+ "perform move");
					} catch (Exception e) {
						throw new ServerAccessException("Invalid Parameter: "
								+ "free");
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
			VertexLocation vertexLocation = makeVertexLocation
					(arguments.get("vertexLocation"));
			try {
				boolean free = (boolean) arguments.get("free");
				game.doBuildSettlement(free, vertexLocation, playerIndex);
			} catch (ViolatedPreconditionException e) {
				throw new ServerAccessException("Unable to "
						+ "perform move");
			} catch (Exception e) {
				throw new ServerAccessException("Invalid Parameter: "
						+ "free");
			}
		} else {
			throw new ServerAccessException("Invalid Parameters");
		}
	}
}
