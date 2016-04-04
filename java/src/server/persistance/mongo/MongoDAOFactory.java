package server.persistance.mongo;

import server.persistance.DatabaseException;
import server.persistance.IDAOFactory;

public class MongoDAOFactory implements IDAOFactory {
	
	/**
	 * @post database can be written to via DAOs
	 * @throws DatabaseException
	 */
	public MongoDAOFactory() throws DatabaseException {
		
	}

	/** 
	 * @return MongoUsersDAO DAO for the Users table of MyMongo database
	 * @throws DatabaseException
	 */
	@Override
	public MongoUsersDAO createUsersDAO() throws DatabaseException {
		return new MongoUsersDAO();
	}
	
	/** 
	 * @return MongoGamesDAO DAO for the Games table of MyMongo database
	 * @throws DatabaseException
	 */
	@Override
	public MongoGamesDAO createGamesDAO() throws DatabaseException {
		return new MongoGamesDAO();
	}

	/** 
	 * @return MongoCommandsDAO DAO for the Commands table of MyMongo database
	 * @throws DatabaseException
	 */
	@Override
	public MongoCommandsDAO createCommandsDAO() throws DatabaseException {
		return new MongoCommandsDAO();
	}

}
