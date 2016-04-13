package server.command.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONObject;


import server.data.AI;
import server.data.LargestArmyAI;
import server.data.LongestRoadAI;
import server.data.ServerKernel;
import server.data.SettlementsAI;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.definitions.CatanColor;

public class addAI extends GameCommand {

	private CatanCookie cookie;

//	// class variable
//	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
//
//	final java.util.Random rand = new java.util.Random();
//
//	// consider using a Map<String,Boolean> to say whether the identifier is being used or not 
//	final Set<String> identifiers = new HashSet<String>();
//
//	public String randomIdentifier() {
//	    StringBuilder builder = new StringBuilder();
//	    while(builder.toString().length() == 0) {
//	        int length = rand.nextInt(5)+5;
//	        for(int i = 0; i < length; i++)
//	            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
//	        if(identifiers.contains(builder.toString())) 
//	            builder = new StringBuilder();
//	    }
//	    return builder.toString();
//	}


	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		if(validCookie(cookie))
		{
			User user;
			if (AISelector.sole() == null) AISelector.initiate();
			String type = (String) args.get("AIType");
			if (type.equals("LongestRoadAI")) {
				user = new LongestRoadAI();
			}
			else if (type.equals("LargestArmyAI")) {
				user = new LargestArmyAI();
			}
			else if (type.equals("SettlementsAI")) {
				user = new SettlementsAI();
			}
			else
			{
				//should never reach
				user = null;
			}
			try {
				Model model = getGameFromCookie(cookie);
				int gameToJoin = model.getID();
				if(ServerKernel.sole().gameExists(gameToJoin)) {
					Model game = ServerKernel.sole().getGame(gameToJoin);
					this.cookie = new CatanCookie(game);
					CatanColor color = AISelector.sole().getNextColor(game.getID());
					int index = game.getNextPlayerIndex();
					user.setUsername(AISelector.sole().getName(index));
					user.setPassword("dummy"); //can't access Users...
					try {
						if (!ServerKernel.sole().userExists(user)) //Is this necessary???
						{
							ServerKernel.sole().addUser(user);
						}
						else {
							if (type.equals("LongestRoadAI")) {
								user = new LongestRoadAI(ServerKernel.sole().getUserByName(user.getUsername()));
							}
							else if (type.equals("LargestArmyAI")) {
								user = new LargestArmyAI(ServerKernel.sole().getUserByName(user.getUsername()));
							}
							else if (type.equals("SettlementsAI")) {
								user = new SettlementsAI(ServerKernel.sole().getUserByName(user.getUsername()));
							}
							else
							{
								//should never reach
								user = null;
							}
						}
					} catch (UserException e1) {
					}

					game.joinGame(user.getID(), user.getUsername(), color);
					game.registerAIListener((AI) user, index);
					AISelector.sole().addToColorsUsed(game.getID(), color, index);
					
				} else {
					throw new ServerAccessException("Invalid Game");
				}
			} catch (Exception e) {
				throw new ServerAccessException("Invalid ID");
			}
			//throw new ServerAccessException("Can't Add AI's");
		} else {
			throw new ServerAccessException("Invalid Cookie");
		}
		return "Sucess";
	}
	
	public Model getGameFromCookie(String cookie) {
		Model game = null;
			CatanCookie catanCookie;
			try {
				catanCookie = new CatanCookie(cookie, false);
				int gameID = catanCookie.getGameID();
				game = ServerKernel.sole().getGame(gameID);
			} catch (CookieException | ServerAccessException e) {
				//FOR DEBUG ONLY
				//System.err.println(e.getMessage());
			}
		return game;
	}
}
