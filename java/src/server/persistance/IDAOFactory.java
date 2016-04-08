package server.persistance;

public interface IDAOFactory {
	
	/**
	 * @return UsersDAO DAO to interact with the database implementation containing the users
	 * @throws DatabaseException
	 */
	public IUsersDAO createUsersDAO(PersistanceManager pm) throws DatabaseException;
	
	/**
	 * @return GamesDAO DAO to interact with the database implementation containing the games
	 * @throws DatabaseException
	 */
	public IGamesDAO createGamesDAO(PersistanceManager pm) throws DatabaseException;
	
	/**
	 * @return CommandsDAO DAO to interact with the database implementation containing the commands
	 * @throws DatabaseException
	 */
	public ICommandsDAO createCommandsDAO(PersistanceManager pm) throws DatabaseException;
	
}
