package server.command.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONObject;

import server.command.games.AISelector;
import server.data.AI;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
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
			if (AISelector.sole() == null) AISelector.initiate();
			User user = new AI();
			user.setUsername(AISelector.sole().getNextName());
			user.setPassword("dummy"); //can't access Users...
			ServerKernel.sole().addUser(user);
			try {
				Model model = getGameFromCookie(cookie);
				int gameToJoin = model.getID();
				if(ServerKernel.sole().gameExists(gameToJoin)) {
					Model game = ServerKernel.sole().getGame(gameToJoin);
					this.cookie = new CatanCookie(game);
					CatanColor color = AISelector.sole().getNextColor();
					int index = game.joinGame(user.getID(), user.getUsername(), color);
					((AI)user).setIndex(index);
					game.registerAIListener((AI) user);
					AISelector.sole().addToColorsUsed(color);
					
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
