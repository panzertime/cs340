package server.persistance.files;

import java.util.List;

import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IGamesDAO;
import shared.model.Model;

public class FileGamesDAO implements IGamesDAO {

	@Override
	public void saveGame(IConnection connection, Model model) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Model> getGames(IConnection connection) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
