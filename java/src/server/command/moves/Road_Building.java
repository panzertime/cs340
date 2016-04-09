package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.board.edge.EdgeLocation;
import shared.model.exceptions.ViolatedPreconditionException;

public class Road_Building extends MovesCommand {

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
					EdgeLocation spot1 = makeEdgeLocation
							(args.get("spot1"));
					EdgeLocation spot2 = makeEdgeLocation
							(args.get("spot2"));
					try {
						game.doRoad_Building(spot1, spot2, playerIndex);
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
	public void reExecute(Model game, JSONObject args) 
			throws ServerAccessException {
		if(validMovesArguments(args, getClass().getSimpleName())) {
			int playerIndex = 
					((Long) args.get("playerIndex")).intValue();
			EdgeLocation spot1 = makeEdgeLocation
					(args.get("spot1"));
			EdgeLocation spot2 = makeEdgeLocation
					(args.get("spot2"));
			try {
				game.doRoad_Building(spot1, spot2, playerIndex);
			} catch (ViolatedPreconditionException e) {
				throw new ServerAccessException("Unable to "
						+ "perform move");
			}
		} else {
			throw new ServerAccessException("Invalid Parameters");
		}
	}
}
