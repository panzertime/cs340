package server.persistance;

public interface DAOFactory {
	
	/**
	 * @return UsersDAO DAO to interact with the DB implementation containing the users
	 */
	public UsersDAO createUsersDAO();
	
	/**
	 * @return GamesDAO DAO to interact with the DB implementation containing the games
	 */
	public GamesDAO createGamesDAO();
	
	/**
	 * @return CommandsDAO DAO to interact with the DB implementation containing the commands
	 */
	public CommandsDAO createCommandsDAO();
	
}
