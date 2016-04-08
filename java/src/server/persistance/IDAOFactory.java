package server.persistance;

public interface IDAOFactory {
	
	/**
	 * @return UsersDAO DAO to interact with the database implementation containing the users
	 * @throws DatabaseException
	 */
	public IUsersDAO createUsersDAO() throws DatabaseException;
	
	/**
	 * @return GamesDAO DAO to interact with the database implementation containing the games
	 * @throws DatabaseException
	 */
	public IGamesDAO createGamesDAO() throws DatabaseException;
	
	/**
	 * @return CommandsDAO DAO to interact with the database implementation containing the commands
	 * @throws DatabaseException
	 */
	public ICommandsDAO createCommandsDAO() throws DatabaseException;
	
}
