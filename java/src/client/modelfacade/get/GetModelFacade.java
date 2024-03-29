package client.modelfacade.get;

import java.util.ArrayList;
import java.util.List;

import client.communication.LogEntry;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.main.ClientPlayer;
import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import client.modelfacade.ModelFacade;
import shared.model.Model;
import shared.model.board.hex.HexLocation;
import shared.model.definitions.CatanColor;
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
		//super.updateModel(model);
		this.gameModel = model;
		notifyListeners();
	}
	
	
	private static List<GetModelFacadeListener> listeners = new ArrayList<GetModelFacadeListener>();
	
	public static void registerListener(GetModelFacadeListener newListener) {
		listeners.add(newListener);
	}
	
	public void notifyListeners() {
		for (GetModelFacadeListener listener : listeners)
			listener.update();
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
	
	public HexLocation getRobberLocation() {
		return gameModel.getRobberLocation();
	}
	
	
	
	public boolean isStateWaiting() {
		if (gameModel.isActivePlayer(ClientPlayer.sole().getUserIndex()))
			return false;
		return true;
	}
	
	public boolean isStatePlaying() {
		if (isStateWaiting())
			return false;
		if (!gameModel.isStatePlaying())
			return false;
		return true;
	}
	
	public boolean isStateSetupRoad() {
		if (isStateWaiting())
			return false;
		return gameModel.canStartSetupRoad(ClientPlayer.sole().getUserIndex());
	}
	
	public boolean isStateSetupSettlement() {
		if (isStateWaiting())
			return false;
		return gameModel.canStartSetupSettlement(ClientPlayer.sole().getUserIndex());
	}
	
	
	public RobPlayerInfo[] getRobbablePlayer(HexLocation robberLoc) {
		return gameModel.getRobbablePlayersAt(robberLoc);
	}
	
	
	//J.R.'s section//////////////////////////////////////////////////////////////////////////////////////////
	//client only
	public boolean hasDevCardEnabled(DevCardType type)
	{
		return gameModel.hasDevCardEnabled(type, ClientPlayer.sole().getUserIndex());
	}
	
	//client only
	public int getDevCardAmount(DevCardType type)
	{
		return gameModel.getDevCardAmount(type, ClientPlayer.sole().getUserIndex());
	}
	
	//client only
	public int getResourceAmount(ResourceType type)
	{
		return gameModel.getResourceAmount(type, ClientPlayer.sole().getUserIndex());
	}
	
	public List<Integer> getPlayerIndices()
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
	public CatanColor getPlayerColor(int playerIndex)
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
		return gameModel.isClientWinner(ClientPlayer.sole().getUserID());
	}
	
	//client only
	public int getFreeRoads()
	{
		return gameModel.getFreeRoads(ClientPlayer.sole().getUserIndex());
	}
	
	//client only
	public int getFreeSettlements()
	{
		return gameModel.getFreeSettlements(ClientPlayer.sole().getUserIndex());
	}
	
	//client only
	public int getFreeCities()
	{
		return gameModel.getFreeCities(ClientPlayer.sole().getUserIndex());
	}
	
	//client only
	public int getSoldiers()
	{
		return gameModel.getSoldiers(ClientPlayer.sole().getUserIndex());
	}
	
	
	public boolean mustDiscard()
	{
		return gameModel.mustDiscard(ClientPlayer.sole().getUserIndex());
	}
	
	/////Trading

	
	public int getTradeResource(ResourceType type)
	{
		return gameModel.getTradeResource(type);

	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	//JOSHUA
	public List<LogEntry> getMessages() {
		return gameModel.getMessages();
	}
	public List<LogEntry> getGameHistory() {
		return gameModel.getGameHistory();
	}
	public String getTradeSenderName() {
		return gameModel.getTradeSenderName();
	}

	public PlayerInfo[] getTradingPartners() {
		return gameModel.getTradingPartner(ClientPlayer.sole().getUserID());
	}

	public boolean hasTradeOffer() {
		return gameModel.hasTradeOffer();
	}

	public boolean isStateRolling() {
		return gameModel.isStateRolling();
	}

	public boolean isStateDiscarding() {
		return gameModel.isStateDiscarding();
	}

	public boolean isStateRobbing() {
		return gameModel.isStateRobbing();
	}

	//////////////////////////////////////////


}
