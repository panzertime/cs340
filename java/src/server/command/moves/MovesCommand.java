package server.command.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import shared.model.hand.ResourceType;

/**
 * Class for keeping common move command functionality in the same place
 *
 */
public abstract class MovesCommand implements ICommand {
	
	protected JSONObject arguments;

	public JSONObject getArguments(){
		return arguments;
	}
	
	/**
	 * Persists the Moves commands
	 * @pre Command has already been executed and all parameters are valid
	 * @post arguments are set and the Server saves the command
	 * @param args JSONObject of args passed in from client
	 * @param catanCookie converted CatanCookie passed in from client
	 */
	@SuppressWarnings("unchecked")
	protected void persist(JSONObject args, CatanCookie catanCookie, Model game) {
		arguments = args;
		int gameID = catanCookie.getGameID();
		int gameVersion = game.getVersion();
		args.put("version", gameVersion);
		ServerKernel.sole().persistCommand(gameID, this);
	}
	
	/**
	 * Turns a cookie String into a CatanCookie
	 * @pre none
	 * @post A CatanCookie is create
	 * @param cookie Cookie String received
	 * @return CatanCookie from the passed string
	 * @throws CookieException invalid data in String
	 */
	public CatanCookie makeCatanCookie(String cookie) throws CookieException {
		CatanCookie catanCookie = new CatanCookie(cookie, false);
		return catanCookie;
	}
	
	/**
	 * Uses the passed string to check the database to see if the cookie
	 * parameters are valid
	 * @pre the cookie passed in has username cookie and game cookie
	 * @post the cookie is determined to be legitimate or not
	 * @param cookie the cookie string passed in the request
	 * @return whether or not the username, password, ID and gameID are valid
	 */
	public boolean validCookie(CatanCookie catanCookie) {
		boolean userExists = false;
		boolean userInGame = false; 
		try {
			User user = new User(catanCookie.getName(), 
					catanCookie.getPassword(), catanCookie.getUserID());
			userExists = ServerKernel.sole().userExists(user);
			int gameID = catanCookie.getGameID();
			if(ServerKernel.sole().gameExists(gameID)) {
				userInGame = ServerKernel.sole().userIsInGame(gameID, user);
			}
		} catch (UserException | ServerAccessException e) {
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
	 * @param vertLocJSON JSONObject
	 * @return corresponding JSONObject
	 * @throws ServerAccessException parameter invalid
	 */
	public VertexLocation makeVertexLocation(Object vertLocJSON) 
			throws ServerAccessException {
		VertexLocation result = null;
		try {
			JSONObject vertLoc = (JSONObject) vertLocJSON;
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
	 * @param roadLocJSON location to build to new road
	 * @return corresponding EdgeLocation
	 * @throws ServerAccessException invalid EdgeLocation params
	 */
	public EdgeLocation makeEdgeLocation(Object roadLocJSON) 
			throws ServerAccessException{
		EdgeLocation result = null;
		try {
			JSONObject roadLoc = (JSONObject) roadLocJSON;
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
	
	/**
	 * Creates a resouceList map from a JSONObject
	 * @pre JSONObject has all of the correct parameters
	 * @post a Map of resource types and values is created
	 * @param resListJSON JSONObject with 5 resources and corresponding values
	 * @return Map of resource types and values
	 * @throws ServerAccessException JSONObject did not include the correct 
	 * resources
	 */
	public Map<ResourceType, Integer> makeResourceList(Object resListJSON) 
			throws ServerAccessException {
		Map<ResourceType, Integer> result = new 
				HashMap<ResourceType, Integer>();
		try {
			JSONObject resList = (JSONObject) resListJSON;
			int brick = ((Long) resList.get("brick")).intValue();
			int ore = ((Long) resList.get("ore")).intValue();
			int sheep = ((Long) resList.get("sheep")).intValue();
			int wheat = ((Long) resList.get("wheat")).intValue();
			int wood = ((Long) resList.get("wood")).intValue();
			
			result.put(ResourceType.BRICK, brick);
			result.put(ResourceType.ORE, ore);
			result.put(ResourceType.SHEEP, sheep);
			result.put(ResourceType.WHEAT, wheat);
			result.put(ResourceType.WOOD, wood);
		} catch(Exception e) {
			throw new ServerAccessException("Invalid Parameters: "
					+ "Resource List");
		}
		return result;
	}
	
	/**
	 * Takes in a string and turns it into a resource
	 * @pre string represents a valid Resource
	 * @post a ResouceType is created
	 * @param resString value representing a string
	 * @return corresponding resource
	 */
	public ResourceType getResourceType(Object resString) 
			throws ServerAccessException {
		ResourceType result = null;
		try {
			result = ResourceType.valueOf(((String) resString).toUpperCase());
		} catch (Exception e) {
			throw new ServerAccessException("Invalid Parameter: Resource");
		}
		
		return result;
	}
	
	/**
	 * Takes a hexLoc in JSON form and converts it to a HexLocation
	 * @pre hexLoc is valid Hex Location
	 * @post HexLocation Object is created
	 * @param hexLocJSON hexLocation as a JSONObject
	 * @return HexLocation representing the JSON
	 * @throws ServerAccessException invalid HexLocation from JSON
	 */
	public HexLocation makeHexLocation(Object hexLocJSON) 
			throws ServerAccessException {
		HexLocation result = null;
		try {
			JSONObject hexLoc = (JSONObject) hexLocJSON;
			int x = ((Long) hexLoc.get("x")).intValue();
			int y = ((Long) hexLoc.get("y")).intValue();
			result = new HexLocation(x, y);
		} catch (Exception e) {
			throw new ServerAccessException("Invalid Parameter: HexLocation");
		}
		return result;
	}

	/**
	 * @pre The parameters of the command have been set and the game Model is valid
	 * @post The command is executed again from the data in memory and the game is updated
	 * @param game - The game to which the command will be performed
	 * @throws ServerAccessException Something in command was unable to be 
	 * redone
	 */
	public abstract void reExecute(Model game) 
			throws ServerAccessException;
	
	/**
	 * Converts the list of JSONCommands into a list of MovesCommands
	 * @param jsonCommands Commands in JSONFormat
	 * @return jsonCommands converted to MovesCommands
	 * @throws ServerAccessException Invalid command
	 */
	public static List<MovesCommand> convertJSONListToCommandList
			(List<JSONObject> jsonCommands)  throws ServerAccessException {
		List<MovesCommand> result = new ArrayList<MovesCommand>();
		for(JSONObject jsonCommand: jsonCommands) {
			MovesCommand newMove = convertJSONToCommand(jsonCommand);
			result.add(newMove);
		}
		return result;
	}
	
	/**
	 * Convert the JSONCommand into a MovesCommand
	 * @param jsonCommand Command in JSONFormat
	 * @return jsonCommand converted to MovesCommands
	 * @throws ServerAccessException Invalid command
	 */
	public static MovesCommand convertJSONToCommand
			(JSONObject jsonCommand)  throws ServerAccessException {
		MovesCommand result = null;
		try {
			String commandName = (String) jsonCommand.get("type");
			commandName = "server.command.moves." + commandName;
				Class<?> genericMoveCommand = Class.forName(commandName);
				result = (MovesCommand) genericMoveCommand.newInstance();
				result.arguments = jsonCommand;
		} catch (ClassNotFoundException | 
				InstantiationException | IllegalAccessException e) { 
			e.printStackTrace();
			throw new ServerAccessException
					("Could not use reflection properly");
		} catch (Exception e1) {
			throw new ServerAccessException
					("Stored command has invalid type");
		}
		return result;
	}
	
}
