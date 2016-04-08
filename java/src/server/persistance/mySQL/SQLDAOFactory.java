package server.persistance.mySQL;

import server.persistance.DatabaseException;
import server.persistance.IDAOFactory;
import server.persistance.PersistanceManager;

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
	public SQLUsersDAO createUsersDAO(PersistanceManager pm) throws DatabaseException {
		return new SQLUsersDAO(pm);
	}
	
	/**
	 * @return SQLGamesDAO DAO for the Games table of MySQL database
	 * @throws DatabaseException
	 */
	@Override
	public SQLGamesDAO createGamesDAO(PersistanceManager pm) throws DatabaseException {
		return new SQLGamesDAO(pm);
	}

	/**
	 * @return SQLCommandsDAO DAO for the Commands table of MySQL database
	 * @throws DatabaseException
	 */
	@Override
	public SQLCommandsDAO createCommandsDAO(PersistanceManager pm) throws DatabaseException {
		return new SQLCommandsDAO(pm);
	}

}
