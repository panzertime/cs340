package client.modelfacade.state;

import client.modelfacade.state.clientstates.ClientState;

public interface ClientStateListener {

	void notify(ClientState state);

}
