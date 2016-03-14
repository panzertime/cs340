package server.data;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import server.exception.ServerAccessException;
import server.exception.UserException;
import shared.model.Model;

/**
 * Interface for the commands to use when interacting with the servers data.
 * All other functions in ServerData should be private.
 *
 */
public class ServerData {

	/**
	 * GameID, Model
	 */
	private Map<Integer, Model> games;
	/**
	 * Username, User
	 */
	private Map<String, User> users;
	
	private static ServerData _instance;
	
	/**Initializes the database for the server instance
	 * @pre none
	 * @post games and users are setup as hashmaps
	 */
	private ServerData() {
		this.games = new HashMap<Integer, Model>();
		this.users = new HashMap<String, User>();
	}
	
	/**
	 * Returns the sole instance of server data
	 * @pre none
	 * @post the ServerData instance is created, if it wasn't already, and 
	 * accessible
	 * @return The only instance of server data is accessed
	 */
	public static ServerData sole() {
		if(_instance == null) {
			_instance = new ServerData();
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
	 * @return Whether or not the operation was successful
	 * @throws UserException user's credentials are invalid
	 * @throws ServerAccessException username already exists
	 */
	public void addUser(User user) throws UserException, 
		ServerAccessException {
		/*if(user.hasValidCrendentials()) {
			String newUserName = user.getUsername();
			if(this.users.containsKey(newUserName)) {
				throw new ServerAccessException("Username already exists"
						+ " on server.");
			} else {
				user.setUserID();
				this.users.put(newUserName, user);
			}
		} else {
			throw new UserException("User is missing username or password"
					+ "information.");
		}*/
		if(userExists(user)) {
			throw new ServerAccessException("Username already exists"
					+ " on server.");
		} else {
			user.setUserID();
			this.users.put(user.getUsername(), user);
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
		boolean result = false;
		if(user.hasValidCrendentials()) {
			if(this.users.containsKey(user.getUsername())) {
				result = true;
			}
		} else {
			throw new UserException("User is missing username or password"
					+ "information.");
		}
		
		return result;
	}
	
	/**
	 * Give the caller a list of all the games available 
	 * @pre games list has been created, user had been validated
	 * @post none
	 * @return list of all the games on server
	 */
	public JSONObject getGames() {
		return null;
		
	}
	
	/**
	 * Checks to see if the given user is in the given game
	 * @pre user is not null, gamelist and userlist have been created already
	 * @post true if the user in game already, false if not, game exists
	 * @param gameID Which game to use in the game list
	 * @param user User to use when checking if he is inside a game 
	 * @return true if the user in game already, false if not
	 */
	public boolean userIsInGame(int gameID, User user) {
		return false;
		
	}
	
	/**
	 * The games list is checked for the given game
	 * @pre the games list has been created already
	 * @post true if the gameID is in the games list, false otherwise
	 * @param gameID ID of the game to check for
	 * @return true if the gameID is in the games list, false otherwise
	 */
	public boolean gameExists(int gameID) {
		return false;
		
	}
	
	/**
	 * Gets the requested game
	 * @pre user has been validated, games exists and the user is in it
	 * @post the game is returned and able to be modified
	 * @param gameID ID of the game to be gotten
	 * @return the requested game
	 */
	public Model getGame(int gameID) {
		return null;
		
	}
	
	/**
	 * This function is only to be used when a user creates a new game NOT
	 * when a game has been accessed through the getGame function - The game
	 * that is modified that way is also modified in the list. This function
	 * adds a new game to the game list
	 * @pre game list has been created. Model is a new version of the game
	 * @post the given game is added to the gameslist
	 * @param newModel The new game to be added to the games list
	 */
	public void putGame(Model newModel) {
		
	}
}
