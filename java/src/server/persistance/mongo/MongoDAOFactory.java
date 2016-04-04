package server.persistance.mongo;

import server.persistance.DAOFactory;

public class MongoDAOFactory implements DAOFactory {
	
	/**
	 * @post database can be written to via DAOs
	 */
	public MongoDAOFactory() {
		
	}

	/* 
	 * @return MongoUsersDAO DAO for the Users table of MyMongo database
	 */
	@Override
	public MongoUsersDAO createUsersDAO() {
		return new MongoUsersDAO();
	}
	
	/* 
	 * @return MongoGamesDAO DAO for the Games table of MyMongo database
	 */
	@Override
	public MongoGamesDAO createGamesDAO() {
		return new MongoGamesDAO();
	}

	/* 
	 * @return MongoCommandsDAO DAO for the Commands table of MyMongo database
	 */
	@Override
	public MongoCommandsDAO createCommandsDAO() {
		return new MongoCommandsDAO();
	}

}
