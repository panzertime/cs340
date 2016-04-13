package server.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.exception.ServerAccessException;
import server.exception.UserException;
import server.persistance.DatabaseException;
import server.persistance.IDAOFactory;
import server.persistance.PersistanceManager;
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
		this.persistenceTracker = new HashMap<Integer,Integer>();
		this.numOfGames = 0;
		this.persistFrequency = 5;
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
				addUserToDB(user);
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
	
	public User getUserByName(String username) {
		return users.get(username);
	}
	
	/**
	 * Give the caller a list of all the games available 
	 * @pre games list has been created, user had been validated
	 * @post none
	 * @return list of all the games on server
	 */
	@SuppressWarnings("unchecked")
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
		addGameToDB(newModel);
		this.persistenceTracker.put(newGameID, 0);
		
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
	
	// Phase 4
	private int persistFrequency;
	private PersistanceManager pm;
	private Map<Integer,Integer> persistenceTracker;
	
	/**
	 * 
	 * @param freq
	 * @param df
	 * @throws ServerAccessException
	 */
	public void initPersistence(int freq, IDAOFactory df) 
			throws ServerAccessException {
		try {
			pm = new PersistanceManager(df);
			pm.startTransaction();
			initGamesFromDB();
			initUsersFromDB();
			pm.endTransaction(true);
			persistFrequency = freq;
		} catch (DatabaseException e) {
			try {
				pm.endTransaction(false);
			} catch (DatabaseException e1) {
				System.err.println("Could not end transaction");
				e1.printStackTrace();
			}
			throw new ServerAccessException("Could not initialize database");
		}
	}

	/**
	 * initializes Users from the database
	 * @pre Users has been init, db has valid info
	 * @post Users will include info from the DB
	 * @throws DatabaseException Db could not be accessed properly
	 * @throws ServerAccessException User stored in DB had data error
	 */
	private void initUsersFromDB() throws DatabaseException, 
    ServerAccessException {
	List<User> users = pm.getUsers();
	for(User user : users) {
	    this.users.put(user.getUsername(), user);
	}
		User.updateID(users.size());
	}

	/**
	 * initializes Games from the database
	 * @pre Games has been init, db has valid info, db has started transaction
	 * @post Games will included updated model info from the DB
	 * @throws DatabaseException Db could not be accessed properly
	 * @throws ServerAccessException Game or command stored in DB had error
	 */
	private void initGamesFromDB() throws DatabaseException, 
			ServerAccessException {
		List<Model> games = pm.getModels();
		for(Model game : games) {
			int gameID = game.getID();
			updateGame(gameID, game);
			this.games.put(gameID, game);
			this.persistenceTracker.put(gameID, 0);
		}
		this.numOfGames = games.size();
	}

	private void updateGame(int gameID, Model game) throws DatabaseException, 
			ServerAccessException {
		List<JSONObject> jsonCommands = pm.getCommands(gameID);
		List<MovesCommand> commands = 
				MovesCommand.convertJSONListToCommandList(jsonCommands);
		//Might need to make sure commands are in order
		for(MovesCommand command : commands) {
			command.reExecute(game);
		}
		pm.clearCommands(gameID);
	}

	/**
	 * The given game is updated in the database with the new player
	 * @pre The game ID is valid and represents the given game, the game 
	 * 		includes the updates as if the player were already added 
	 * @post The given game is updated in the database with the new player 
	 * @param game updated game with new Player
	 * @param gameID Corresponding gameID
	 */
	public void addPlayerToGame(Model game, int gameID) {
		if(pm != null) {
			try {
				pm.startTransaction();
				pm.saveGame(game);
				pm.endTransaction(true);
			} catch (DatabaseException e) {
				System.err.println("Could not update game with new player in"
						+ "the database");
				e.printStackTrace();
				try {
					pm.endTransaction(false);
				} catch (DatabaseException e1) {
					System.err.println("Could not end transaction");
					e1.printStackTrace();
				}
			}
		}
	}
	
    /**
     * @pre the game exists and the command is valid with the correct command parameters
     * @post The command is persisted to the specific game
     * @param gameID The game to which the command should be persisted
     * @param cmd The command that needs to be persisted
     * @throws DatabaseException Could not get or save something in the DB
     */
    public void persistCommand(int gameID, MovesCommand cmd) {
    	if(pm != null) {
	        Integer numOfCommands = this.persistenceTracker.get(gameID);
	        try {
	            pm.startTransaction();
	            if(numOfCommands == persistFrequency) {
	                Model model = this.games.get(gameID);
	                pm.saveGame(model);
	                pm.clearCommands(gameID);
	                numOfCommands = 0;
	            } else {
	                pm.saveCommand(gameID, cmd);
	                numOfCommands++;
	            }
	            this.persistenceTracker.replace(gameID, numOfCommands);
	            pm.endTransaction(true);
	        } catch (DatabaseException e) {
	            System.err.println("Could not persist command");
	            e.printStackTrace();
	            try {
	                pm.endTransaction(false);
	            } catch (DatabaseException e1) {
	                System.err.println("Could not end transaction");
	                e1.printStackTrace();
	            }
	        }
    	}
    }

	
	private void addUserToDB(User user) {
		if(pm != null) {
			try {
				pm.startTransaction();
				pm.saveUser(user);
				pm.endTransaction(true);
			} catch (DatabaseException e) {
				System.err.println("Could not save user in database");
				e.printStackTrace();
				try {
					pm.endTransaction(false);
				} catch (DatabaseException e1) {
					System.err.println("Could not end transaction");
					e1.printStackTrace();
				}
			}
		}
	}

	private void addGameToDB(Model newModel) {
		if(pm != null) {
			try {
				pm.startTransaction();
				pm.saveGame(newModel);
				pm.endTransaction(true);
			} catch (DatabaseException e) {
				System.err.println("Could not save game in database");
				e.printStackTrace();
				try {
					pm.endTransaction(false);
				} catch (DatabaseException e1) {
					System.err.println("Could not end transaction");
					e1.printStackTrace();
				}
			}
		}
	}

	
}
