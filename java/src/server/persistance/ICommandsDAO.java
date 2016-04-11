package server.persistance;

import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;

public interface ICommandsDAO {
	
	/**
	 * @param gameID Id of the related game
	 * @param movesCommand command to be stored in the database
	 * @post movesCommand is stored in the database
	 */
	public void saveCommmand(IConnection connection, Integer gameID, MovesCommand movesCommand) throws DatabaseException;
	
	/**
	 * @param gameID gameId to match in returned commands
	 * @return List list of commands run on the related game 
	 */
	public List<JSONObject> getCommands(IConnection connection, Integer gameID) throws DatabaseException;
	
	public void deleteCommand(IConnection connection, Integer gameID) throws DatabaseException;

}
