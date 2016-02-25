package shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import client.modelfacade.ModelFacade;
import shared.logger.Log;
import shared.model.board.Board;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.tiles.water.PortType;
import shared.model.board.vertex.VertexLocation;
import shared.model.chat.ChatModel;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.BadPlayerIndexException;
import shared.model.exceptions.BadStatusException;
import shared.model.exceptions.BadTurnStatusException;
import shared.model.hand.ResourceType;

public class Model {
	
	private static List<ModelFacade> listeners = new ArrayList<ModelFacade>();
	
	public static void registerListener(ModelFacade newListener) {
		listeners.add(newListener);
	}
	
	public static void shareNewModel(Model model) {
		for (ModelFacade listener : listeners)
			listener.updateModel(model);
	}
 
	private Board board;
	private Map<Integer, Player> players;
	private Bank bank;
	private Achievements achievements;
	private Integer activePlayerIndex;
	private Integer winnerIndex;
	private Integer version;
	private String status;
	private ChatModel chatModel;
	private TradeModel tradeModel;

	/**
	 * @param board
	 *            the board to initialize
	 * @param players
	 *            the players to initialize
	 * @param bank
	 *            the bank to initialize
	 * @param achievements
	 *            the achievements to initialize
	 * @throws BadPlayerIndexException
	 * @throws BadTurnStatusException
	 * @throws BadStatusException
	 * @throws Exception
	 */

	public Model(JSONObject jsonMap) throws BadJSONException {
		if (jsonMap == null)
			throw new BadJSONException();
		bank = new Bank((JSONObject) jsonMap.get("bank"), (JSONObject) jsonMap.get("deck"));
		chatModel = new ChatModel((JSONObject) jsonMap.get("chat"), (JSONObject) jsonMap.get("log"));
		
		JSONArray playerList = (JSONArray) jsonMap.get("players");
		if (playerList == null)
			throw new BadJSONException();
		players = new HashMap<Integer, Player>();
		for (int i = 0; i < playerList.size(); i++) {
			JSONObject jsonPlayer = (JSONObject) playerList.get(i);
			Player player = new Player(jsonPlayer, this);
			players.put(player.getPlayerIndex(), player);
		}
		
		board = new Board((JSONObject) jsonMap.get("map"), this);

		Long version = ((Long) jsonMap.get("version"));
		if (version == null)
			throw new BadJSONException();
		this.version = version.intValue();

		Long winnerID = ((Long) jsonMap.get("winner"));
		if (winnerID == null)
			throw new BadJSONException();
		this.winnerIndex = winnerID.intValue();

		JSONObject turnTracker = (JSONObject) jsonMap.get("turnTracker");
		if (turnTracker == null)
			throw new BadJSONException();

		Long activePlayerID = ((Long) turnTracker.get("currentTurn"));
		if (activePlayerID == null)
			throw new BadJSONException();
		this.activePlayerIndex = activePlayerID.intValue();

		String s = (String) turnTracker.get("status");
		if (s == null)
			throw new BadJSONException();
		try {
			setStatus(s);
		} catch (BadStatusException e) {
			Log.error(e);
			throw new BadJSONException();
		}
		Long longestRoadOwnerID = ((Long) turnTracker.get("longestRoad"));
		Long largestArmyOwnerID = ((Long) turnTracker.get("largestArmy"));
		if (longestRoadOwnerID == null || largestArmyOwnerID == null)
			throw new BadJSONException();
		achievements = new Achievements(getPlayer(longestRoadOwnerID.intValue()),
				getPlayer(largestArmyOwnerID.intValue()));

		if ((JSONObject) jsonMap.get("tradeOffer") == null)
			tradeModel = null;
		else
			tradeModel = new TradeModel((JSONObject) jsonMap.get("tradeOffer"));
	}

	public Boolean equalsJSON(JSONObject jsonMap) {
		if (jsonMap == null)
			return false;
		if (bank.equalsJSON((JSONObject) jsonMap.get("bank"), (JSONObject) jsonMap.get("deck")) == false)
			return false;
		if (chatModel.equalsJSON((JSONObject) jsonMap.get("chat"), (JSONObject) jsonMap.get("log")) == false)
			return false;
		JSONArray playerList = (JSONArray) jsonMap.get("players");
		if (playerList == null)
			return false;
		for (int i = 0; i < playerList.size(); i++) {
			JSONObject player = (JSONObject) playerList.get(i);
			if (!players.get(i).equalsJSON(player))
				return false;
		}
		if (!board.equalsJSON((JSONObject) jsonMap.get("map"), this))
			return false;

		Long version = ((Long) jsonMap.get("version"));
		if (version == null)
			return false;
		if (this.version != version.intValue())
			return false;

		Long winnerID = ((Long) jsonMap.get("winner"));
		if (winnerID == null)
			return false;
		if (winnerID.intValue() != this.winnerIndex)
			return false;

		JSONObject turnTracker = (JSONObject) jsonMap.get("turnTracker");
		if (turnTracker == null)
			return false;
		Long currentTurn = ((Long) turnTracker.get("currentTurn"));
		if (currentTurn == null)
			return false;
		if (activePlayerIndex != currentTurn.intValue())
			return false;

		String s = (String) turnTracker.get("status");
		if (s == null)
			return false;
		if (!isStatus(s))
			return false;

		Long lR = ((Long) turnTracker.get("longestRoad"));
		Long lA = ((Long) turnTracker.get("largestArmy"));
		if (lR == null || lA == null)
			return false;
		if (!achievements.equalsJSON(lR.intValue(), lA.intValue()))
			return false;
		if ((JSONObject) jsonMap.get("tradeOffer") == null) {
			if (tradeModel != null)
				return false;
		} else if (!tradeModel.equalsJSON((JSONObject) jsonMap.get("tradeOffer")))
			return false;

		return true;
	}

	private boolean isStatus(String s) {
		if (status.equalsIgnoreCase("Rolling")) {
			return true;
		} else if (status.equalsIgnoreCase("Robbing")) {
			return true;
		} else if (status.equalsIgnoreCase("Playing")) {
			return true;
		} else if (status.equalsIgnoreCase("Discarding")) {
			return true;
		} else if (status.equalsIgnoreCase("FirstRound")) {
			return true;
		} else if (status.equalsIgnoreCase("SecondRound")) {
			return true;
		}
		return false;
	}

	public void setStatus(String status) throws BadStatusException {
		if (status.equalsIgnoreCase("Rolling")) {
			this.status = status;
			return;
		} else if (status.equalsIgnoreCase("Robbing")) {
			this.status = status;
			return;
		} else if (status.equalsIgnoreCase("Playing")) {
			this.status = status;
			return;
		} else if (status.equalsIgnoreCase("Discarding")) {
			this.status = status;
			return;
		} else if (status.equalsIgnoreCase("FirstRound")) {
			this.status = status;
			return;
		} else if (status.equalsIgnoreCase("SecondRound")) {
			this.status = status;
			return;
		}
		throw new BadStatusException();

	}

	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @return the achievements
	 */
	public Achievements getAchievements() {
		return achievements;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * 
	 * @return int simulating two six-faced die
	 */
	public int getDiceNumber() {
		return 0;

	}

	/**
	 * @post - the methods when the dice is rolled are called
	 */
	public void roll() {

	}

	/**
	 * @param dice
	 *            - number that will produce
	 * @post - the production methods have been called on the tiles that match
	 *       dice
	 */
	public void production(int dice) {

	}

	/**
	 * @post - the actions when a seven is rolled have been called
	 */
	public void sevenRolled() {

	}

	/**
	 * @post turn is set to the Player who has the turn
	 */
	public void getNextTurn() {

	}

	public Player getPlayer(Integer playerID) {
		return players.get(playerID);
	}
	
	/**
	 * @return the player who has the turn
	 */
	private Player getActivePlayer() {
		return players.get(activePlayerIndex);
	}

	public Boolean isActivePlayer(Integer playerID) {
		if (activePlayerIndex.equals(playerID))
			return true;
		return false;
	}
	
	public Integer getIndexFromPlayerID(Integer playerID) {
		for (Player player : getPlayers()) {
			if (player.getPlayerID() == playerID)
				return player.getPlayerIndex();
		}
		return null;
	}

	/**
	 * @param turn
	 *            - the player who has the current turn
	 */
	public void setActivePlayer(Integer playerId) {
		activePlayerIndex = playerId;
	}

	public Player getWinner() {
		return players.get(winnerIndex);
	}

	public void setWinner(Integer playerID) {
		this.winnerIndex = playerID;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public void setTurnStatus(String status) {
		this.status = status;
	}

	public boolean inSetupRounds() {
		if (status.equalsIgnoreCase("FirstRound") || status.equalsIgnoreCase("SecondRound")) {
			return true;
		}
		return false;
	}

	public String getStatus() {
		return status;
	}

	// Preconditions
	// The status of the client model is 'Discarding'
	// You have over 7 cards
	// You have the cards you're choosing to discard
	public Boolean canDiscardCard(Integer playerID, Map<ResourceType, Integer> resources) {
		// this may need to be changed in the future if a non-active player can
		// discard
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Discarding"))
			return false;
		if (!getActivePlayer().canDiscardCard())
			return false;
		if (!getActivePlayer().hasCards(resources))
			return false;
		return true;
	}

	// Preconditions
	// It is your turn
	// The client model�s status is 'Rolling'
	public Boolean canRollNumber(Integer playerID) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Rolling"))
			return false;
		return true;
	}

	public Boolean canOfferTrade(Integer playerID, Map<ResourceType, Integer> resourceList, Integer receiverIndex) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().hasCards(resourceList))
			return false;
		if (players.get(receiverIndex) == null)
			return false;
		return true;
	}

	public Boolean canMaritimeTrade(Integer playerID, int ratio, ResourceType input, ResourceType output)
	{
		if (!getBank().getHand().hasResource(output, 1))
			return false;
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().hasResource(input, ratio))
			return false;
		
		PortType portType = null;
		if (ratio == 2)
			portType = input.getPortType();
		else if (ratio == 3)
			portType = PortType.THREE;
		else if (ratio == 4)
			portType = null;
		else
			return false;
		
		if (!(portType != null && getActivePlayer().hasPort(portType)))
			return false;
		return true;
	}

	public Boolean canPlaceRobber(Integer playerID, HexLocation location) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getBoard().canPlaceRobber(location))
			return false;
		return true;
	}

	public Boolean canRobPlayer(Integer playerID, HexLocation location, Integer targetPlayerID) {
		if (!canPlaceRobber(playerID, location))
			return false;
		Player targetPlayer = getPlayer(targetPlayerID);
		if (targetPlayer == null)
			return false;
		if (targetPlayer.getHandSize() < 1)
			return false;
		return true;
	}

	public Boolean canFinishTurn(Integer playerID) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		return true;
	}

	public Boolean canBuyDevCard(Integer playerID) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (getBank().getHand().getDevCards().size() < 1)
			return false;
		if (!getActivePlayer().hasDevelopmentCost())
			return false;
		return true;
	}

	public Boolean canUseSoldier(Integer playerID, HexLocation newRobberLocation, Integer targetPlayerID) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasKnightToUse())
			return false;
		if (!canRobPlayer(playerID, newRobberLocation, targetPlayerID))
			return false;
		return true;
	}

	public Boolean canUseYearOfPlenty(Integer playerID, ResourceType one, ResourceType two) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasYearOfPlentyToUse())
			return false;
		if (!getBank().getHand().hasResource(one, 1))
			return false;
		if (!getBank().getHand().hasResource(two, 1))
			return false;
		return true;
	}

	public Boolean canUseRoadBuilding(Integer playerID, EdgeLocation one, EdgeLocation two) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasRoadBuildingToUse())
			return false;
		if (!getBoard().canBuildRoad(getActivePlayer(), one))
			return false;
		if (!getBoard().canBuildRoadTwo(getActivePlayer(), one, two))
			return false;
		if (!getActivePlayer().hasRoadPiece())
			return false;
		return true;
	}

	public Boolean canUseMonopoly(Integer playerID, ResourceType type) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasMonopolyToUse())
			return false;
		return true;
	}

	public Boolean canUseMonument(Integer playerID) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (getActivePlayer().getVictoryPointsWithMonuments() < 10)
			return false;
		return true;
	}

	public Boolean canBuildRoad(Integer playerID, EdgeLocation edge) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getActivePlayer().hasRoadPiece())
			return false;
		
		if (status.equalsIgnoreCase("Playing")) {
			if (!getActivePlayer().hasRoadCost())
				return false;
			if (!getBoard().canBuildRoad(getActivePlayer(), edge))
				return false;
		} else if (inSetupRounds()) { 
			if (!getBoard().canBuildSetupRoad(getActivePlayer(), edge))
				return false;
		} else {
			return false;
		}
		return true;
	}

	public Boolean canBuildSettlement(Integer playerID, VertexLocation vertex) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getActivePlayer().hasSettlementPiece())
			return false;
		
		if (status.equalsIgnoreCase("Playing")) {
			if (!getActivePlayer().hasSettlementCost())
				return false;
			if (!getBoard().canBuildSettlement(this.getActivePlayer(), vertex))
				return false;
		} else if (inSetupRounds()) {
			if (!getBoard().canBuildSetupSettlement(this.getActivePlayer(), vertex))
				return false;
		} else {
			return false;
		}
		return true;
	}

	/**
	 * @param vertex
	 * @return
	 */
	public Boolean canBuildCity(Integer playerID, VertexLocation vertex) {
		if (!isActivePlayer(playerID))
			return false;
		if (!getStatus().equalsIgnoreCase("Playing"))
			return false;
		if (!getActivePlayer().hasCityCost())
			return false;
		if (!getActivePlayer().hasCityPiece())
			return false;
		if (!getBoard().canBuildCity(this.getActivePlayer(), vertex))
			return false;
		return true;
	}

	/**
	 * checks that the person has been offered a trade and that they have the
	 * resources
	 * 
	 * @pre none
	 * @post game will run
	 * @return if a given trade can be made
	 * @throws BadJSONException
	 */
	public Boolean canAcceptTrade(Integer playerID) {
		if (tradeModel == null)
			return false;
		if (tradeModel.getReceiverID() != playerID)
			return false;
		if (!getPlayer(tradeModel.getReceiverID()).hasCards(this.tradeModel.getResourcesToGive()))
			return false;
		return true;
	}

	/**
	 * If a game is in state, this will always return true
	 * 
	 * @post game in state
	 * @post true
	 * @return true
	 */
	public Boolean canSendChat(Integer playerID) {
		if (playerID == null) {
			return false;
		}
		return true;
	}

	public ArrayList<Player> getPlayers() {
		return new ArrayList<Player>(players.values());
	}

	public List<PseudoHex> getPseudoHexes() {
		return getBoard().getPseudoHexes();
	}
	
	public List<PseudoCity> getPseudoCities() {
		List<PseudoCity> pcities = new ArrayList<PseudoCity>();
		for (Player player : new ArrayList<Player>(players.values())) {
			pcities.addAll(player.getPseudoCities());
		}
		return pcities;
	}
	
	public List<PseudoSettlement> getPseudoSettlements() {
		List<PseudoSettlement> psettlements = new ArrayList<PseudoSettlement>();
		for (Player player : new ArrayList<Player>(players.values())) {
			psettlements.addAll(player.getPseudoSettlements());
		}
		return psettlements;
	}
	
	public List<PseudoRoad> getPseudoRoads() {
		List<PseudoRoad> proads = new ArrayList<PseudoRoad>();
		for (Player player : new ArrayList<Player>(players.values())) {
			proads.addAll(player.getPseudoRoads());
		}
		return proads;
	}
}
