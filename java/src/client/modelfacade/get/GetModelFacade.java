package client.modelfacade.get;

import java.util.ArrayList;
import java.util.List;

import client.data.PlayerInfo;
import client.modelfacade.ModelFacade;
import shared.model.Model;

public class GetModelFacade  extends ModelFacade {

	private GetModelFacade() {
		super();
	}
	
	private static GetModelFacade singleton;
	
	public static GetModelFacade sole() {
		if (singleton == null)
			singleton = new GetModelFacade();
		return singleton;
	}
	
	@Override
	public void updateModel(Model model) {
		super.updateModel(model);
		notifyListeners();
	}
	
	
	private static List<GetModelFacadeListener> listeners = new ArrayList<GetModelFacadeListener>();
	
	public static void registerListener(GetModelFacadeListener newListener) {
		listeners.add(newListener);
	}
	
	public void notifyListeners() {
		for (GetModelFacadeListener listener : listeners)
			listener.notify();
	}
	
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
}