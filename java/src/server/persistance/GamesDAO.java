package server.persistance;

import java.util.List;

import shared.model.Model;

public interface GamesDAO {
	
	/**
	 * @param model model to save in the database
	 * @post model is saved in the database
	 */
	public void saveGame(Model model);
	
	/**
	 * @return List list of all games in the database
	 */
	public List<Model> getGames();

}
