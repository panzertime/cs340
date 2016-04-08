package server.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.data.User;
import shared.model.Model;

public class PersistanceManager {
	
	private IUsersDAO usersDAO;
	private IGamesDAO gamesDAO;
	private ICommandsDAO commandsDAO;
	
	/**
	 * @param factory to build DAOs
	 * @post PersistanceManager has DAOs initialized
	 */
	public PersistanceManager(IDAOFactory factory) throws DatabaseException {
		usersDAO = factory.createUsersDAO(this);
		gamesDAO = factory.createGamesDAO(this);
		commandsDAO = factory.createCommandsDAO(this);
	}
	
	/**
	 * @throws DatabaseException
	 * @post Transaction is ready to be attempted
	 */
	public void startTransaction() throws DatabaseException {
		
	}
	
	/**
	 * @throws DatabaseException
	 * @post Transaction is entirely persisted to database
	 */
	public void endTransaction() throws DatabaseException {
		
	}

	/**
	 * @pre database is up and available for writing and User is in valid state
	 * @param user
	 * @throws DatabaseException
	 * @post user is persisted to database
	 */
	public void saveUser(User user) throws DatabaseException {
		usersDAO.saveUser(user);
	}
	
	/**
	 * @pre database is up and available for writing and model is in valid state
	 * @param model for database entry
	 * @throws DatabaseException
	 * @post game is persisted to database
	 */
	public void saveGame(Model model) throws DatabaseException {
		gamesDAO.saveGame(model);
		
	}
	
	/**
	 * @pre database is up and available for writing, a game with gameID exists, and command is in valid state
	 * @param gameID for database entry
	 * @param command for database entry
	 * @throws DatabaseException
	 * @post command is persisted to database
	 */
	public void saveCommand(Integer gameID, MovesCommand command) throws DatabaseException {
		commandsDAO.saveCommmand(gameID, command);
	}
	
	/**
	 * @pre database is up and available for reading
	 * @return list of all registered users
	 * @throws DatabaseException
	 */
	public List<User> getUsers() throws DatabaseException {
		return usersDAO.getUsers();
	}
	
	/**
	 * @pre database is up and available for reading
	 * @return List of models
	 * @throws DatabaseException
	 */
	public List<Model> getModel() throws DatabaseException {
		return gamesDAO.getGames();
	}
	
	/**
	 * @pre database is up and available for reading
	 * @param gameID ID of game to be fetched
	 * @return List of commands not persisted to the gameID
	 * @throws DatabaseException
	 */
	public List<JSONObject> getCommands(Integer gameID) throws DatabaseException {
		return commandsDAO.getCommands(gameID);
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void safeClose(PreparedStatement stmt) {
		// TODO Auto-generated method stub
		
	}

	public static void safeClose(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}
}
