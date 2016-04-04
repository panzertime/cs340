package server.persistance.mySQL;

import server.persistance.DAOFactory;

public class SQLDAOFactory implements DAOFactory {
	
	/**
	 * @post database can be written to via DAOs
	 */
	public SQLDAOFactory() {
		
	}

	/* 
	 * @return SQLUsersDAO DAO for the Users table of MySQL database
	 */
	@Override
	public SQLUsersDAO createUsersDAO() {
		return new SQLUsersDAO();
	}
	
	/* 
	 * @return SQLGamesDAO DAO for the Games table of MySQL database
	 */
	@Override
	public SQLGamesDAO createGamesDAO() {
		return new SQLGamesDAO();
	}

	/* 
	 * @return SQLCommandsDAO DAO for the Commands table of MySQL database
	 */
	@Override
	public SQLCommandsDAO createCommandsDAO() {
		return new SQLCommandsDAO();
	}

}
