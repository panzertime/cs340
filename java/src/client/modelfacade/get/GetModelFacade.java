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
import shared.model.board.piece.PieceType;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCardType;

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
	
	
	//J.R.'s section//////////////////////////////////////////////////////////////////////////////////////////
	//client only
	public boolean hasDevCardEnabled(DevCardType type)
	{
		return gameModel.hasDevCardEnabled(type);
	}
	
	//client only
	public int getDevCardAmount(DevCardType type)
	{
		return gameModel.getDevCardAmount(type);
	}
	
	//client only
	public int getResourceAmount(ResourceType type)
	{
		return gameModel.getResourceAmount(type);
	}
	
	public ArrayList<Integer> getPlayerIndices()
	{
		return gameModel.getPlayerIndices();
	}
	
	public int getPoints(int playerIndex)
	{
		return gameModel.getPoints(playerIndex);
	}
	
	public boolean isTurn(int playerIndex)
	{
		return gameModel.isTurn(playerIndex);
	}
	public boolean isLargestArmy(int playerIndex)
	{
		return gameModel.isLargestArmy(playerIndex);
	}
	public boolean isLongestRoad(int playerIndex)
	{
		return gameModel.isLongestRoad(playerIndex);
	}
	public PieceType getPlayerColor(int playerIndex)
	{
		return gameModel.getPlayerColor(playerIndex);
	}
	public String getPlayerName(int playerIndex)
	{
		return gameModel.getPlayerName(playerIndex);
	}
	
	public boolean isGameOver()
	{
		return gameModel.isGameOver();
	}
	
	public String getWinnerName()
	{
		return gameModel.getWinnerName();
	}
	
	public boolean isClientWinner()
	{
		return gameModel.isClientWinner();
	}
	
	//client only
	public int getFreeRoads()
	{
		return gameModel.getFreeRoads();
	}
	
	//client only
	public int getFreeSettlements()
	{
		return gameModel.getFreeSettlements();
	}
	
	//client only
	public int getFreeCities()
	{
		return gameModel.getFreeCities();
	}
	
	//client only
	public int getSoldiers()
	{
		return gameModel.getSoldiers();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////

}