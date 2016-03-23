package server.command.moves;

import org.json.simple.JSONObject;

import server.command.ICommand;
import server.data.ServerKernel;
import server.data.User;
import server.exception.ServerAccessException;
import server.exception.UserException;
import server.utils.CatanCookie;
import server.utils.CookieException;
import shared.model.Model;
import shared.model.board.edge.EdgeDirection;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;

/**
 * Class for keeping common move command functionality in the same place
 *
 */
public abstract class MovesCommand implements ICommand {

	//TODO fix duplication in GameCommand
	/**
	 * Uses the passed string to check the database to see if the cookie
	 * parameters are valid
	 * @pre the cookie passed in has username cookie and game cookie
	 * @post the cookie is determined to be legitimate or not
	 * @param cookie the cookie string passed in the request
	 * @return whether or not the username, password, ID and gameID are valid
	 */
	public boolean validCookie(String cookie) {
		boolean userExists = false;
		boolean userInGame = false; 
		try {
			CatanCookie catanCookie = new CatanCookie(cookie, false);
			User user = new User(catanCookie.getName(), 
					catanCookie.getPassword(), catanCookie.getUserID());
			userExists = ServerKernel.sole().userExists(user);
			int gameID = catanCookie.getGameID();
			if(ServerKernel.sole().gameExists(gameID)) {
				userInGame = ServerKernel.sole().userIsInGame(gameID, user);
			}
		} catch (UserException | CookieException | ServerAccessException e) {
			//FOR DEBUG ONLY
			//System.err.println(e.getMessage());
		}
		
		return userExists && userInGame;
	}
	
	/**
	 * Gets a game from the given cookie
	 * @pre Cookie has already been validated
	 * @post nothing
	 * @param cookie cookie passed in from client
	 * @return Model corresponding the id in the cookie
	 */
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
	
	/**
	 * Gets a user from the given cookie
	 * @pre Cookie has already been validated
	 * @post nothing
	 * @param cookie cookie passed in from client
	 * @return User corresponding to the info in the cookie
	 */
	public User getUserFromCookie(String cookie) {
		User user = null;
		try {
			CatanCookie catanCookie = new CatanCookie(cookie, false);
			user = new User(catanCookie.getName(), 
					catanCookie.getPassword(), catanCookie.getUserID());
		} catch (CookieException e) {
			System.err.println("Tried to create a user from an invalid cookie."
					+ " Check pre-conditions.");
		}
		return user;
	}
	
	/**
	 * Used to check the args to see if they match the generic moves arguments.
	 * Note that some commands may have additionally parameters that need to be
	 * checked individually
	 * @pre none
	 * @post none
	 * @param args JSONObject of the args passed in
	 * @param command name of the command(not take from args)
	 * @return Whether or not this function
	 */
	public boolean validMovesArguments(JSONObject args, String command) {
		boolean result = false;
		try {
			String argsCommand = (String) args.get("type");
			Long index = (Long) args.get("playerIndex");
			if(argsCommand != null &&
					index != null &&
					command.equals(argsCommand) &&
					index >= 0 &&
					index <= 3) {
				result = true;
			}
		} catch(Exception e) {
			//Primarily for Casting errors - however, of type Exception for
			//unexpected corner cases. If it hits here, result is already
			//defaulted for the expected value
		}

		return result;
	}
	
	/**
	 * Makes a vertexLocation from the passed in vertexLocation JSONObject
	 * @pre params are valid VertexLocation params
	 * @post VertexLocation matching JSON params is created
	 * @param vertLoc JSONObject
	 * @return corresponding JSONObject
	 * @throws ServerAccessException parameter invalid
	 */
	public VertexLocation makeVertexLocation(JSONObject vertLoc) 
			throws ServerAccessException {
		VertexLocation result = null;
		try {
			int x = ((Long) vertLoc.get("x")).intValue();
			int y = ((Long) vertLoc.get("y")).intValue();
			String dir = (String) vertLoc.get("direction");
			HexLocation hexLoc = new HexLocation(x, y);
			VertexDirection vertDir = VertexDirection.fromJSON(dir);
			result = new VertexLocation(hexLoc, vertDir);
		} catch (Exception e) {
			throw new ServerAccessException("Invalid Parameters: "
					+ "vertexLocation");
		}
		return result;
	}
	
	/**
	 * Converts a JSONObject into an EdgeLocation
	 * @pre all params are valid EdgeLocation params
	 * @post EdgeLocation is created from values
	 * @param roadLoc location to build to new road
	 * @return corresponding EdgeLocation
	 * @throws ServerAccessException invalid EdgeLocation params
	 */
	public EdgeLocation makeEdgeLocation(JSONObject roadLoc) 
			throws ServerAccessException{
		EdgeLocation result = null;
		try {
			int x = ((Long) roadLoc.get("x")).intValue();
			int y = ((Long) roadLoc.get("y")).intValue();
			String dir = (String) roadLoc.get("direction");
			HexLocation hexLoc = new HexLocation(x, y);
			EdgeDirection edgeDir = EdgeDirection.fromJSON(dir);
			result = new EdgeLocation(hexLoc, edgeDir);
		} catch (Exception e) {
			throw new ServerAccessException("Invalid Parameters: "
					+ "edgeLocation");
		}
		return result;
	}
}
