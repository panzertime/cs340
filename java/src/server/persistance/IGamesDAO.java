package server.persistance;

import java.util.List;

import shared.model.Model;

public interface IGamesDAO {
	
	/**
	 * @param model model to save in the database
	 * @post model is saved in the database
	 * @throws DatabaseException
	 */
	public void saveGame(Model model) throws DatabaseException;
	
	/**
	 * @return List list of all games in the database
	 * @throws DatabaseException
	 */
	public List<Model> getGames() throws DatabaseException;

}
