package server.persistance.files;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IGamesDAO;
import shared.model.Model;

public class FileGamesDAO implements IGamesDAO {

	@Override
	public void saveGame(IConnection connection, Model model) throws DatabaseException {
		try {
			PrintWriter writer = new PrintWriter("data/game/" + model.getID() + ".txt", "UTF-8");
			writer.println(model.toJSON().toJSONString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new DatabaseException();
		}

	}

	@Override
	public List<Model> getGames(IConnection connection) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
