package client.servercommunicator.pollerstate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.data.Games;
import client.main.ClientPlayer;
import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;

public class PollerPlayerWaitingState implements IPollerState {

	@Override
	public synchronized void run() {
			ServerFacade.get_instance().updateGamesList();
	}

	@Override
	public boolean getHasFailed() {
		return false;
	}

}
