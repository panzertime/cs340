package server.persistance.mongo;

import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;

public class MongoCommandsDAO implements ICommandsDAO {

	@Override
	public void saveCommmand(Integer gameID, MovesCommand movesCommand) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<JSONObject> getCommands(Integer gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
