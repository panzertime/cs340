package server.data;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.exception.ServerAccessException;
import server.exception.UserException;
import shared.model.Model;

/**
 * Interface for the commands to use when interacting with the servers data.
 * All other functions in ServerData should be private.
 *
 */
public class ServerKernel {

	/**
	 * GameID, Model
	 */
	private Map<Integer, Model> games;
	/**
	 * Username, User
	 */
	private Map<String, User> users;

	private int numOfGames;
	
	private static ServerKernel _instance;
	
	/**Initializes the database for the server instance
	 * @pre none
	 * @post games and users are setup as hashmaps
	 */
	private ServerKernel() {
		this.games = new HashMap<Integer, Model>();
		this.users = new HashMap<String, User>();
		this.numOfGames = 0;
	}
	
	/**
	 * Returns the sole instance of server data
	 * @pre none
	 * @post the ServerData instance is created, if it wasn't already, and 
	 * accessible
	 * @return The only instance of server data is accessed
	 */
	public static ServerKernel sole() {
		if(_instance == null) {
			_instance = new ServerKernel();
		}
		
		return _instance;
	}
	
	/**
	 * Attempts to add the created user to the list of users on the server
	 * @pre User is not null and contains all fields filled out except ID
	 * @post the passed User object will be assigned a valid ID if he does not
	 * yet exist. Otherwise he will be deleted and this function will return 
	 * false
	 * @param user User object created from passed in credentials
	 * @throws ServerAccessException username already exists
	 */
	public void addUser(User user) throws ServerAccessException {
		try {
			if(userExists(user)) {
				throw new ServerAccessException("Username already exists"
						+ " on server.");
			} else {
				user.setUserID();
				this.users.put(user.getUsername(), user);
			}
		} catch (UserException e) {
			throw new ServerAccessException("User Already Had an ID");
		}
	}
	
	/**
	 * Checks the username against the key in the
	 * registered users list to see if it is a valid username.
	 * @pre user is not null, userlist has been created
	 * @post result of user check in userlist
	 * @param user the user to be checked for in the userlist
	 * @return Whether or not the given user is in the userlist
	 * @throws UserException user's credentials are invalid
	 */
	public boolean userExists(User user) throws UserException {
		if(user.hasValidCrendentials()) {
			if(this.users.containsKey(user.getUsername()) ||
					this.users.containsValue(user)) {
				return true;
			}
		} else {
			throw new UserException("User is missing username or password"
					+ "information.");
		}
		
		return false;
	}
	
	
	public User getUserByPassword(String username, String password) {
		User user = users.get(username);
		if (user == null)
			return null;
		if (user.getPassword().equals(password))
			return user;
		return null;
		
	}
	
	/**
	 * Give the caller a list of all the games available 
	 * @pre games list has been created, user had been validated
	 * @post none
	 * @return list of all the games on server
	 */
	public JSONArray getGames() {
		JSONArray gamesList = new JSONArray();
		
		for(Map.Entry<Integer,Model> model : this.games.entrySet()) {
			JSONObject gameInfo = model.getValue().getGamesList();
			gamesList.add(gameInfo);
		}
		return gamesList;
	}
	
	/**
	 * Checks to see if the given user is in the given game
	 * @pre user is not null, gamelist and userlist have been created already,
	 * gameID is for a valid game
	 * @post true if the user in game already, false if not, game exists
	 * @param gameID Which game to use in the game list
	 * @param user User to use when checking if he is inside a game 
	 * @return true if the user in game already, false if not
	 */
	public boolean userIsInGame(int gameID, User user) throws 
		ServerAccessException {
		boolean result = false;
		Model game = this.games.get(gameID);
		if(game == null) {
			throw new ServerAccessException("Game does not exist");
		} else {
			result = game.isPlayerInGame(user.getUsername(), user.getID());
		}
		return result;
	}
	
	/**
	 * The games list is checked for the given game
	 * @pre the games list has been created already
	 * @post true if the gameID is in the games list, false otherwise
	 * @param gameID ID of the game to check for
	 * @return true if the gameID is in the games list, false otherwise
	 */
	public boolean gameExists(int gameID) {
		return this.games.containsKey(gameID);
	}
	
	/**
	 * Gets the requested game
	 * @pre user has been validated, games exists and the user is in it
	 * @post the game is returned and able to be modified
	 * @param gameID ID of the game to be gotten
	 * @return the requested game(can be null)
	 */
	public Model getGame(int gameID) throws ServerAccessException {
		Model game = this.games.get(gameID);
		if(game == null) {
			throw new ServerAccessException("Game does not exist");
		}
		return game;
	}
	
	/**
	 * This function is only to be used when a user creates a new game NOT
	 * when a game has been accessed through the getGame function - The game
	 * that is modified that way is also modified in the list. This function
	 * adds a new game to the game list
	 * @pre game list has been created. Model is a valid version of a new game
	 * @post the given game is added to the gameslist
	 * @param newModel The new game to be added to the games list
	 * @return the new id assigned to the game
	 */
	public int putGame(Model newModel) {
		int newGameID = newGameID();
		this.games.put(newGameID, newModel);
		newModel.setID(newGameID);
		
		return newGameID;
	}

	/**
	 * This function is to be used to create a new ID for every added new game.
	 * This function should be used when adding a game to assure that a unique
	 * id is created.
	 * @pre none
	 * @post numOfGames is incremented 
	 * @return an incremented newGameID
	 */
	private int newGameID() {
		return numOfGames++;
	}
	
	public int getNumOfGames() {
		return this.numOfGames;
	}
	
	//TESTING AND DEBUGGING SECTION
	
	/**
	 * FOR DEBUGGING AND TESTING ONLY:
	 * @pre none
	 * @post will reset all fields to zero
	 */
	public void reinitAll() {
		reinitGames();
		reinitUsers();
	}
	
	/**
	 * FOR DEBUGGING AND TESTING ONLY:
	 * @pre none
	 * @post will reset all games related fields to zero
	 */
	public void reinitGames() {
		this.games = new HashMap<Integer, Model>();
		this.numOfGames = 0;
	}
	
	/**
	 * FOR DEBUGGING AND TESTING ONLY:
	 * @pre none
	 * @post will reset Users to zero
	 */
	public void reinitUsers() {
		this.users = new HashMap<String, User>();
		User.resetIDs();
	}
	private int persistFrequency;
	

	/**
	 * @pre The game and the user exists
	 * @post The user is added as part of the game, and the game is persisted
	 * @param u - The user that will be added to the game
	 * @param gameID - The ID of game that the user is being added to
	 */
	public void addUserToGame(User u, int gameID) {
		
	}
	
	/**
	 * @pre the game exists and the command is valid with the correct command parameters
	 * @post The command is persisted to the specific game
	 * @param gameID The game to which the command should be persisted
	 * @param cmd The command that needs to be persisted
	 */
	public void persistCommand(int gameID, MovesCommand cmd) {
		
	}
	
	/**
	 * @pre the Game is valid
	 * @post Loads the game from memory and updates it according to the commands and re-persists it
	 * @param gameID The index of the game in the map
	 * 
	 */
	private void updateGame(int gameID) {
		
	}
}
