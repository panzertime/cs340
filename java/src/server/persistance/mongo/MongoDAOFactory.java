package server.persistance.mongo;

import java.net.UnknownHostException;

import server.persistance.DatabaseException;
import server.persistance.IConnection;
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

	@Override
	public MongoCommandsDAO createCommandsDAO() throws DatabaseException {
		return new MongoCommandsDAO();
	}

	@Override
	public IConnection createConnection() throws DatabaseException {
		try {
			return new MongoConnection();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new DatabaseException();
		}
	}

}
