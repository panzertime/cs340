package server.persistance.mongo;

import java.util.List;

import org.json.simple.JSONObject;

import server.command.moves.MovesCommand;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IConnection;

public class MongoCommandsDAO implements ICommandsDAO {

	@Override
	public void saveCommmand(IConnection connection, Integer gameID, MovesCommand movesCommand) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<JSONObject> getCommands(IConnection connection, Integer gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
