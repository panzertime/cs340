package server.persistance.mongo;

import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IDAOFactory;
import server.persistance.IGamesDAO;
import server.persistance.IUsersDAO;
import server.persistance.PersistanceManager;

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
	public MongoUsersDAO createUsersDAO(PersistanceManager pm) throws DatabaseException {
		return new MongoUsersDAO();
	}
	
	/** 
	 * @return MongoGamesDAO DAO for the Games table of MyMongo database
	 * @throws DatabaseException
	 */
	@Override
	public MongoGamesDAO createGamesDAO(PersistanceManager pm) throws DatabaseException {
		return new MongoGamesDAO();
	}

	@Override
	public MongoCommandsDAO createCommandsDAO(PersistanceManager pm) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
