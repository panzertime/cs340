package server.command.moves;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import shared.model.Model;
import shared.model.board.edge.EdgeLocation;
import shared.model.exceptions.ViolatedPreconditionException;

public class Road_Building extends MovesCommand {

	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		String result = null;
		if(validCookie(cookie)) {
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

	@Override
	public void reExecute(Model game) {
		// TODO Auto-generated method stub
		
	}
}
