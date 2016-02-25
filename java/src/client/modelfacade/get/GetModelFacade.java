package client.modelfacade.get;

import java.util.ArrayList;
import java.util.List;

import client.data.PlayerInfo;
import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
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
	

	public List<PseudoHex> getPseudoHexes() {
		if (!hasGameModel())
			return new ArrayList<PseudoHex>();
		return gameModel.getPseudoHexes();
	}
	
	public List<PseudoCity> getPseudoCities() {
		if (!hasGameModel())
			return new ArrayList<PseudoCity>();
		return gameModel.getPseudoCities();
	}
	
	public List<PseudoSettlement> getPseudoSettlements() {
		if (!hasGameModel())
			return new ArrayList<PseudoSettlement>();
		return gameModel.getPseudoSettlements();
	}
	
	public List<PseudoRoad> getPseudoRoads() {
		if (!hasGameModel())
			return new ArrayList<PseudoRoad>();
		return gameModel.getPseudoRoads();
	}
}