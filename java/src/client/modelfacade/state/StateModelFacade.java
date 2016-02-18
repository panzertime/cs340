package client.modelfacade.state;

import java.util.ArrayList;
import java.util.List;

import client.modelfacade.ModelFacade;
import client.modelfacade.state.clientstates.ClientState;
import shared.model.Model;

public class StateModelFacade  extends ModelFacade {

	private StateModelFacade() {
		super();
	}
	
	private static StateModelFacade singleton;
	
	public static StateModelFacade sole() {
		if (singleton == null)
			singleton = new StateModelFacade();
		return singleton;
	}
	
	@Override
	public void updateModel(Model model) {
		super.updateModel(model);
		// TODO state determining
		setState(null /*TODO*/);
	}
	
	private ClientState state;	
	
	public ClientState getState() {
		return state;
	}
	
	private void setState(ClientState state) {
		this.state = state;
		notifyListeners();
	}
	
	
	private static List<ClientStateListener> listeners = new ArrayList<ClientStateListener>();
	
	public static void registerListener(ClientStateListener newListener) {
		listeners.add(newListener);
	}
	
	public void notifyListeners() {
		for (ClientStateListener listener : listeners)
			listener.notify();
	}

}
