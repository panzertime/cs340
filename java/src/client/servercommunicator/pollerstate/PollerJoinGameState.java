package client.servercommunicator.pollerstate;

import client.servercommunicator.ServerFacade;

public class PollerJoinGameState implements IPollerState {
	
	@Override
	public synchronized void run() {
		ServerFacade.get_instance().updateGamesList();
	}

	@Override
	public boolean getHasFailed() {
		return false;
	}

}
