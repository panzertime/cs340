package client.modelfacade.get;

import client.modelfacade.state.clientstates.ClientState;

public interface GetModelFacadeListener {

	void notify(ClientState state);

}
