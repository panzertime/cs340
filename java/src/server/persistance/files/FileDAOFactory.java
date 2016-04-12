package server.persistance.files;

import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IConnection;
import server.persistance.IDAOFactory;
import server.persistance.IGamesDAO;
import server.persistance.IUsersDAO;

public class FileDAOFactory implements IDAOFactory {

	@Override
	public IUsersDAO createUsersDAO() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGamesDAO createGamesDAO() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommandsDAO createCommandsDAO() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConnection createConnection() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
