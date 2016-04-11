package server.command.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONObject;

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

	// class variable
	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

	final java.util.Random rand = new java.util.Random();

	// consider using a Map<String,Boolean> to say whether the identifier is being used or not 
	final Set<String> identifiers = new HashSet<String>();

	public String randomIdentifier() {
	    StringBuilder builder = new StringBuilder();
	    while(builder.toString().length() == 0) {
	        int length = rand.nextInt(5)+5;
	        for(int i = 0; i < length; i++)
	            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
	        if(identifiers.contains(builder.toString())) 
	            builder = new StringBuilder();
	    }
	    return builder.toString();
	}


	@Override
	public String execute(JSONObject args, String cookie) 
			throws ServerAccessException {
		if(validCookie(cookie))
		{
			User user = new AI();
			user.setUsername(this.randomIdentifier());
			user.setPassword("dummy");
			ServerKernel.sole().addUser(user);
			try {
				int gameToJoin = ((Long) args.get("id")).intValue();
				if(ServerKernel.sole().gameExists(gameToJoin)) {
					Model game = ServerKernel.sole().getGame(gameToJoin);
					this.cookie = new CatanCookie(game);
					game.joinGame(user.getID(), user.getUsername(), CatanColor.GREEN);
					
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
}
