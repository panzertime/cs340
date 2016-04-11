package server.persistance;

import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.data.User;
import server.exception.ServerAccessException;
import shared.model.Model;

public class PersistanceManager {
	
	private IConnection connection;
	private IUsersDAO usersDAO;
	private IGamesDAO gamesDAO;
	private ICommandsDAO commandsDAO;
	
	/**
	 * @param factory to build DAOs
	 * @post PersistanceManager has DAOs initialized
	 */
	public PersistanceManager(IDAOFactory factory) throws DatabaseException {
		usersDAO = factory.createUsersDAO();
		gamesDAO = factory.createGamesDAO();
		commandsDAO = factory.createCommandsDAO();
		connection = factory.createConnection();
	}
	
	/**
	 * @throws DatabaseException
	 * @post Transaction is ready to be attempted
	 */
	public void startTransaction() throws DatabaseException {
		connection.startTransaction();
	}
	
	/**
	 * @throws DatabaseException
	 * @post Transaction is entirely persisted to database
	 */
	public void endTransaction(boolean commit) throws DatabaseException {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (DatabaseException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				connection.safeClose();
				connection = null;
			}
		}
	}

	/**
	 * @pre database is up and available for writing and User is in valid state
	 * @param user
	 * @throws DatabaseException
	 * @post user is persisted to database
	 */
	public void saveUser(User user) throws DatabaseException {
		usersDAO.saveUser(connection, user);
	}
	
	/**
	 * @pre database is up and available for writing and model is in valid state
	 * @param model for database entry
	 * @throws DatabaseException
	 * @post game is persisted to database
	 */
	public void saveGame(Model model) throws DatabaseException {
		gamesDAO.saveGame(connection, model);
		
	}
	
	/**
	 * @pre database is up and available for writing, a game with gameID exists, and command is in valid state
	 * @param gameID for database entry
	 * @param command for database entry
	 * @throws DatabaseException
	 * @post command is persisted to database
	 */
	public void saveCommand(Integer gameID, MovesCommand command) throws DatabaseException {
		commandsDAO.saveCommmand(connection, gameID, command);
	}
	
	/**
	 * @pre database is up and available for reading
	 * @return list of all registered users
	 * @throws DatabaseException
	 */
	public List<User> getUsers() throws DatabaseException {
		return usersDAO.getUsers(connection);
	}
	
	/**
	 * @pre database is up and available for reading
	 * @return List of models
	 * @throws DatabaseException
	 */

	public List<Model> getModel() throws DatabaseException {
		return gamesDAO.getGames(connection);
	}
	
	/**
	 * @pre database is up and available for reading, model exists
	 * @return models
	 * @throws DatabaseException
	 */
	public Model getModel(int gameID) throws DatabaseException {
		//TODO - This function would be really useful
		return null;
	}
	
	/**
	 * @pre database is up and available for reading
	 * @param gameID ID of game to be fetched
	 * @return List of commands not persisted to the gameID
	 * @throws DatabaseException
	 */

	public List<JSONObject> getCommands(Integer gameID) throws DatabaseException {
		return commandsDAO.getCommands(connection, gameID);
	}

	private IConnection getConnection() {
		return connection;
	}

	//ServerKernel will know if it's adding a game or updating and both need
	//quite different information, so we might as well make the two separate
	//functions public
	public void updateGame(Model game, int gameID) {
		// TODO Auto-generated method stub
		
	}
}
