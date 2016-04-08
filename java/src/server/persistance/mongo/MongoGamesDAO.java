package server.persistance.mongo;

import java.util.List;

import server.persistance.DatabaseException;
import server.persistance.IGamesDAO;
import shared.model.Model;

public class MongoGamesDAO implements IGamesDAO {

	@Override
	public void saveGame(Model model) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Model> getGames() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
