package server.persistance.mySQL;

import java.util.List;

import server.command.moves.MovesCommand;
import server.persistance.CommandsDAO;

public class SQLCommandsDAO implements CommandsDAO {

	@Override
	public void saveCommmands(Integer gameID, MovesCommand movesCommand) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MovesCommand> getCommands(Integer gameID) {
		// TODO Auto-generated method stub
		return null;
	}

}
