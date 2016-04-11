package server.persistance.mySQL;

import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IDAOFactory;

public class SQLDAOFactory implements IDAOFactory {
	
	/**
	 * @post database can be written to via DAOs
	 * @throws DatabaseException
	 */
	public SQLDAOFactory() throws DatabaseException {
		
	}

	/** 
	 * @return SQLUsersDAO DAO for the Users table of MySQL database
	 * @throws DatabaseException
	 */
	@Override
	public SQLUsersDAO createUsersDAO() throws DatabaseException {
		return new SQLUsersDAO();
	}
	
	/**
	 * @return SQLGamesDAO DAO for the Games table of MySQL database
	 * @throws DatabaseException
	 */
	@Override
	public SQLGamesDAO createGamesDAO() throws DatabaseException {
		return new SQLGamesDAO();
	}

	/**
	 * @return SQLCommandsDAO DAO for the Commands table of MySQL database
	 * @throws DatabaseException
	 */
	@Override
	public SQLCommandsDAO createCommandsDAO() throws DatabaseException {
		return new SQLCommandsDAO();
	}

	@Override
	public IConnection createConnection() throws DatabaseException {
		return new SQLConnection();
	}

}
