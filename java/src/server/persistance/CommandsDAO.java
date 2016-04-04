package server.persistance;

import java.util.List;

import server.command.moves.MovesCommand;

public interface CommandsDAO {
	
	/**
	 * @param gameID Id of the related game
	 * @param movesCommand command to be stored in the database
	 * @post movesCommand is stored in the database
	 */
	public void saveCommmands(Integer gameID, MovesCommand movesCommand);
	
	/**
	 * @param gameID gameId to match in returned commands
	 * @return List list of commands run on the related game 
	 */
	public List<MovesCommand> getCommands(Integer gameID);

}
