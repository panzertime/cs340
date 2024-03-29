package shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.communication.LogEntry;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoHex;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import client.modelfacade.ModelFacade;
import shared.logger.Log;
import shared.model.board.Board;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.tiles.land.ProductionHex;
import shared.model.board.hex.tiles.water.PortType;
import shared.model.board.piece.Building;
import shared.model.board.vertex.VertexLocation;
import shared.model.chat.ChatModel;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.BadStatusException;
import shared.model.exceptions.JoinGameException;
import shared.model.exceptions.ModelAccessException;
import shared.model.exceptions.NoDevCardFoundException;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCardType;
import shared.model.hand.exceptions.NoRemainingResourceException;

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
	private Integer winnerID;
	private Integer version;
	private String status;
	private ChatModel chatModel;
	private TradeModel tradeModel;
	private String gameName;
	private int gameID;
	
	
	public JSONObject getGamesList() {
		HashMap<String, Object> jsonList = new HashMap<String, Object>();
		jsonList.put("title", gameName);
		jsonList.put("id", this.gameID);
		JSONArray jsonPlayers = new JSONArray();
		for(int i = 0; i < 4; i++) {
			HashMap<String, Object> jsonPlayer = new HashMap<String, Object>();
			if(players.containsKey(i)) {
				Player p = players.get(i);
				jsonPlayer.put("color", p.getColor().toString().toLowerCase());
				jsonPlayer.put("name", p.getUserName());
				jsonPlayer.put("id", p.getPlayerID());
			}
			jsonPlayers.add(new JSONObject(jsonPlayer));
		}
		jsonList.put("players", jsonPlayers);
		return new JSONObject(jsonList);
		
	}

	public Model(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName)
	{
		this.bank = new Bank();
		this.board = new Board(randomTiles, randomNumbers, randomPorts, this);
		this.tradeModel = null;
		this.achievements = new Achievements();
		this.chatModel = new ChatModel();
		this.players = new HashMap<Integer, Player>();
		this.winnerID = -1;
		this.activePlayerIndex = 0;
		this.status = "FirstRound";
		this.gameName = gameName;
		this.version = 0;
	}
	
	public void joinGame(int playerID, String playerName, CatanColor color) throws JoinGameException
	{
	Integer i = this.getIndexFromPlayerID(playerID);
	if (i != null)
	{
		this.getPlayerFromIndex(i).setUserColor(color);
	}
	else
	{
		int index = players.size();
		if (index >= 4)
			throw new JoinGameException();
		players.put(index, new Player(playerID, index, playerName, color, this));
	}
	
	}
	

	public boolean isPlayerInGame(String username, Integer ID)
	{
		boolean result = false;
		Integer playerIndex = this.getIndexFromPlayerID(ID);
		if(playerIndex != null) {
			String name = this.getPlayerName(playerIndex);
			result = name.equals(username);
		}
		
		return result;
	}
	
	
	/**
	 * @param jsonMap
	 * @throws BadJSONException
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
		this.winnerID = winnerID.intValue();

		JSONObject turnTracker = (JSONObject) jsonMap.get("turnTracker");
		if (turnTracker == null)
			throw new BadJSONException();

		Long activePlayerIndex = ((Long) turnTracker.get("currentTurn"));
		if (activePlayerIndex == null)
			throw new BadJSONException();
		this.activePlayerIndex = activePlayerIndex.intValue();

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
		achievements = new Achievements(getPlayerFromIndex(longestRoadOwnerID.intValue()),
				getPlayerFromIndex(largestArmyOwnerID.intValue()));

		if ((JSONObject) jsonMap.get("tradeOffer") == null)
			tradeModel = null;
		else
			tradeModel = new TradeModel((JSONObject) jsonMap.get("tradeOffer"));
	}
	
	public JSONObject toJSON()
	{
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("bank", bank.bankToJSON());
		jsonMap.put("deck", bank.deckToJSON());
		jsonMap.put("chat", this.getChatModel().getChatLog().toJSON());
		jsonMap.put("log", this.getChatModel().getGameLog().toJSON());
		jsonMap.put("map", this.board.toJSON());
		JSONArray playersJSON = new JSONArray();
		for (Player p: players.values())
			playersJSON.add(p.toJSON());
		jsonMap.put("players", playersJSON);
		if (tradeModel != null) jsonMap.put("tradeOffer", this.tradeModel.toJSON());
		Map<String, Object> turnTracker = new HashMap<String, Object>();
		turnTracker.put("currentTurn", this.activePlayerIndex);
		turnTracker.put("status", this.status);
		turnTracker.put("longestRoad", this.achievements.getLongestRoad());
		turnTracker.put("largestArmy", this.achievements.getLargestArmy());
		jsonMap.put("turnTracker", turnTracker);
		jsonMap.put("version", this.version);
		jsonMap.put("winner", this.winnerID);
		return jsonMap;
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
		if (winnerID.intValue() != this.winnerID)
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
		} else if (!tradeModel.equalsJSON((JSONObject) jsonMap.get("tradeOffer"))) {
			return false;
		}

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
	public void progressTurn() {
		
		String source = this.getPlayerName(activePlayerIndex);
		this.chatModel.addGameMessage(source + "'s turn just ended", source);
		
		if (status.equals("Playing")) {
			activePlayerIndex++;
			status = "Rolling";
			if (activePlayerIndex > 3)
				activePlayerIndex = 0;
		}
	}
	
	private void progressSetupTurn() {
		if (status.equals("FirstRound")) {
			activePlayerIndex++;
			if (activePlayerIndex > 3) {
				status = "SecondRound";
				activePlayerIndex = 3; 
			}
		} else if (status.equals("SecondRound")) {
			activePlayerIndex--;
			if (activePlayerIndex < 0) {
				status = "Rolling";
				activePlayerIndex = 0;
			}
		}
	}

	public Player getPlayerFromIndex(Integer playerIndex) {
		return players.get(playerIndex);
	}
	
	/**
	 * @return the player who has the turn
	 */
	private Player getActivePlayer() {
		return players.get(activePlayerIndex);
	}

	public Boolean isActivePlayer(Integer playerIndex) {
		if (activePlayerIndex.equals(playerIndex))
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
	 * @param playerId
	 *            - the player who has the current turn
	 */
	public void setActivePlayer(Integer playerId) {
		activePlayerIndex = playerId;
	}

	public Player getWinner() {
		return players.get(getIndexFromPlayerID(winnerID));
	}

	public void setWinner(Integer playerID) {
		this.winnerID = playerID;
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

	private boolean isStateSetup() {
		if (status.equalsIgnoreCase("FirstRound") || status.equalsIgnoreCase("SecondRound"))
			return true;
		return false;
	}
	
	public boolean isStateSetupRoad() {
		if (!isStateSetup())
			return false;
		if (!getActivePlayer().shouldSetupRoad())
			return false;
		return true;
	}
	
	public boolean isStateSetupSettlement() {
		if (!isStateSetup())
			return false;
		if (!getActivePlayer().shouldSetupSettlement())
			return false;
		return true;
	}
	
	public boolean isStatePlaying() {
		if (!status.equalsIgnoreCase("Playing"))
			return false;
		return true;
	}
	
	public boolean isStateRolling() {
		if(!status.equalsIgnoreCase("Rolling"))
			return false;
		return true;
	}

	public boolean isStateDiscarding() {
		if(!status.equalsIgnoreCase("Discarding"))
			return false;
		return true;
	}

	public boolean isStateRobbing() {
		if(!status.equalsIgnoreCase("Robbing"))
			return false;
		return true;
	}

	public boolean isTurn(int playerIndex) {
		return (this.isActivePlayer(playerIndex));
	}
	
	

	public String getStatus() {
		return status;
	}

	// Preconditions
	// The status of the client model is 'Discarding'
	// You have over 7 cards
	// You have the cards you're choosing to discard
	public Boolean canDiscardCard(Map<ResourceType, Integer> resources, Integer playerIndex) {
		// this may need to be changed in the future if a non-active player can
		// discard
		//System.out.println("chekcing canDiscardCards");
		/*if (!isActivePlayer(playerIndex))
			return false;
		System.out.println(playerIndex + " is the active player");*/
		if (!isStateDiscarding())
			return false;
		//System.out.println("State is discarding");
		if (!getPlayerFromIndex(playerIndex).canDiscardCard())
			return false;
		//System.out.println("Player can discard");
		if (!getPlayerFromIndex(playerIndex).hasCards(resources))
			return false;
		//System.out.println("player has the same cards");
		return true;
	}
	
	public boolean shouldDiscard(int userIndex) {
		if (!getActivePlayer().canDiscardCard())
			return false;
		return true;
	}
	
	
	// Preconditions
	// It is your turn
	// The client model�s status is 'Rolling'
	public Boolean canRollNumber(Integer numberRolled, Integer playerIndex) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStateRolling())
			return false;
		if (numberRolled < 2)
			return false;
		if (numberRolled > 12)
			return false;
		return true;
	}

	public Boolean canOfferTrade(Integer playerIndex, Map<ResourceType, Integer> resourceList, Integer receiverIndex) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (!getActivePlayer().hasCards(resourceList))
			return false;
		if (players.get(receiverIndex) == null)
			return false;
		return true;
	}

	public Boolean canMaritimeTrade(Integer playerIndex, int ratio, ResourceType input, ResourceType output)
	{
		if (!getBank().getHand().hasResource(output, 1))
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
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
		
		if(portType != null) {
            if(!getActivePlayer().hasPort(portType)) {
                return false;
            }
        }
		return true;
	}
	
	public int canOfferMaritime (Integer playerIndex, ResourceType inputType)
	{
		int highestTrade = 0;
		PortType portType = null;

		if (!isActivePlayer(playerIndex) || !isStatePlaying())
			return 0;
			
		switch (inputType)
			{
			case WOOD:
				portType = PortType.WOOD;
				break;
			case BRICK:
				portType = PortType.BRICK;
				break;
			case SHEEP:
				portType = PortType.SHEEP;
				break;
			case WHEAT:
				portType = PortType.WHEAT;
				break;
			case ORE:	
				portType = PortType.ORE;
				break;
			default :
				break;
			}
		
		if (this.getActivePlayer().hasPort(portType) && this.getActivePlayer().hasResource(inputType, 2))
			return 2;
		if (this.getActivePlayer().hasPort(PortType.THREE) && this.getActivePlayer().hasResource(inputType, 3))
			return 3;	
		if (this.getActivePlayer().hasResource(inputType, 4))
			return 4;
		
		return highestTrade;
	}

	public boolean canReceiveMaritime(Integer playerIndex, ResourceType outputType) {
		
		if (!getBank().getHand().hasResource(outputType, 1))
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		return true;
		
	}

	public Boolean canPlaceRobber(Integer playerIndex, HexLocation location) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!getBoard().canPlaceRobber(location))
			return false;
		return true;
	}

	public Boolean canRobPlayerFrom(HexLocation robberLoc, Integer playerIndex, Integer targetPlayerIndex) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (playerIndex == targetPlayerIndex)
			return false;
		Player targetPlayer = getPlayerFromIndex(targetPlayerIndex);
		if (targetPlayer == null)
			return true; //WARNING this is important so that you can rob no one
		if (targetPlayer.getHandSize() < 1)
			return false;
		if (!board.couldBeRobbedFrom(robberLoc, targetPlayer.getPlayerIndex()))
			return false;
		return true;
	}

	public Boolean canFinishTurn(Integer playerIndex) {
		if (!isActivePlayer(playerIndex)) {
			return false;
		} else if (isStateSetup()) {
			return true; //Weird check! Be careful to change this*/
		} else if (!isStatePlaying()) {
			return false;
		}
		return true;
	}

	public Boolean canBuyDevCard(Integer playerIndex) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (getBank().getHand().getDevCards().size() < 1)
			return false;
		if (!getActivePlayer().hasDevelopmentCost())
			return false;
		return true;
	}

	public Boolean canUseSoldier(Integer playerIndex, HexLocation newRobberLocation, Integer targetPlayerIndex) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasDevCardToUse(DevCardType.KNIGHT))
			return false;
		if (!canPlaceRobber(playerIndex, newRobberLocation)) 
			return false;
		if (!board.couldBeRobbedFrom(newRobberLocation, targetPlayerIndex))
			return false;
		return true;
	}

	public Boolean canUseYearOfPlenty(Integer playerIndex, ResourceType one, ResourceType two) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasDevCardToUse(DevCardType.YEAROFPLENTY))
			return false;
		if (!getBank().getHand().hasResource(one, 1))
			return false;
		if (!getBank().getHand().hasResource(two, 1))
			return false;
		return true;
	}

	public Boolean canUseRoadBuilding(Integer playerIndex, EdgeLocation one, EdgeLocation two) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasDevCardToUse(DevCardType.ROADBUILDING))
			return false;
		if (!getBoard().canBuildRoad(getActivePlayer(), one))
			return false;
		if (!getBoard().canBuildRoadTwo(getActivePlayer(), one, two))
			return false;
		if (!getActivePlayer().hasRoadPiece())
			return false;
		return true;
	}
	
	public Boolean canUseRoadBuildingSingle(Integer playerIndex, EdgeLocation one) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasDevCardToUse(DevCardType.ROADBUILDING))
			return false;
		if (!getBoard().canBuildRoad(getActivePlayer(), one))
			return false;
		if (!getActivePlayer().hasRoadPiece())
			return false;
		return true;
	}

	public Boolean canUseMonopoly(Integer playerIndex, ResourceType type) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (!getActivePlayer().canPlayDevelopmentCard())
			return false;
		if (!getActivePlayer().hasDevCardToUse(DevCardType.MONOPOLY))
			return false;
		return true;
	}

	public Boolean canUseMonument(Integer playerIndex) {
		if (!isActivePlayer(playerIndex))
			return false;
		if (!isStatePlaying())
			return false;
		if (getActivePlayer().getPoints() + getActivePlayer().getVictoryPointsOfMonuments() < 10)
			return false;
		return true;
	}

	public Boolean canBuildRoad(Integer playerIndex, EdgeLocation edge) {
		if (!isStatePlaying())
			return false;
		if (!this.canBuyRoad(playerIndex))
			return false;
		if (!getBoard().canBuildRoad(getActivePlayer(), edge))
			return false;
		return true;
	}
	
	public Boolean canBuyRoad(Integer playerIndex) {
		if (!isStatePlaying())
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		if (!getActivePlayer().hasRoadPiece())
			return false;
		if (!getActivePlayer().hasRoadCost())
			return false;
		return true;
	}

	public Boolean canStartSetupRoad(Integer playerIndex) {
		if (!isStateSetupRoad())
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		for (Player player : players.values()) {
			if (player.getRoadsPlaced() < getActivePlayer().getRoadsPlaced())
				return false;
		}
		return true;
	}

	public Boolean canSetupRoad(Integer playerIndex, EdgeLocation edge) {
		if (!canStartSetupRoad(playerIndex))
			return false;
		if (!getBoard().canBuildSetupRoad(getActivePlayer(), edge))
			return false;
		return true;
	}

	public Boolean canBuildSettlement(Integer playerIndex, VertexLocation vertex) {
		if (!isStatePlaying())
			return false;
		if (!this.canBuySettlement(playerIndex))
			return false;
		if (!getBoard().canBuildSettlement(this.getActivePlayer(), vertex))
			return false;
		return true;
	}
	
	public Boolean canBuySettlement(Integer playerIndex) {
		if (!isStatePlaying())
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		if (!getActivePlayer().hasSettlementPiece())
			return false;
		if (!getActivePlayer().hasSettlementCost())
			return false;
		return true;
	}

	public Boolean canStartSetupSettlement(Integer playerIndex) {
		if (!isStateSetupSettlement())
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		for (Player player : players.values()) {
			if (player.getSettlementsPlaced() < getActivePlayer().getSettlementsPlaced())
				return false;
		}
		return true;
	}

	public Boolean canSetupSettlement(Integer playerIndex, VertexLocation vertex) {
		if (!canStartSetupSettlement(playerIndex))
			return false;
		if (!getBoard().canBuildSetupSettlement(this.getActivePlayer(), vertex))
			return false;
		return true;
	}

	public Boolean canBuildCity(Integer playerIndex, VertexLocation vertex) {
		if (!canBuyCity(playerIndex))
			return false;
		if (!getBoard().canBuildCity(this.getActivePlayer(), vertex))
			return false;
		return true;
	}
	
	public Boolean canBuyCity(Integer playerIndex) {
		if (!isStatePlaying())
			return false;
		if (!isActivePlayer(playerIndex))
			return false;
		if (!getActivePlayer().hasCityPiece())
			return false;
		if (!getActivePlayer().hasCityCost())
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
	 */
	public Boolean canAcceptTrade(Boolean willAccept, Integer playerIndex) {
		if (tradeModel == null)
			return false;
		if (tradeModel.getReceiverIndex() != playerIndex)
			return false;
		if (!willAccept)
			return true;	// Pay attention, breaking the norm here
		if (!getPlayerFromIndex(tradeModel.getReceiverIndex()).hasCards(this.tradeModel.getResourcesToGive()))
			return false;
		return true;
	}
	
	public Boolean canViewTrade(Integer playerIndex) {
		if (tradeModel == null)
			return false;
		if (tradeModel.getReceiverIndex() != playerIndex)
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
	public Boolean canSendChat(String message, Integer playerIndex) {
		if (playerIndex == null)
			return false;
		if (getPlayerFromIndex(playerIndex) == null)
			return false;
		if (message == null)
			return false;
		if (message.equals(""))
			return false;
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
	
	public HexLocation getRobberLocation() {
		return board.getRobberLocation();
	}

	
	//J.R.'s section/////////////////////////////////////////////////////////////////////////
	
	//client only
	public boolean hasDevCardEnabled(DevCardType type, int userIndex) {
		Player client = getPlayerFromIndex(userIndex);

		return client.hasDevCardToUse(type);
	}

	//client only
	public int getDevCardAmount(DevCardType type, int userIndex) {
		Player client = getPlayerFromIndex(userIndex);
		return client.getDevCardAmount(type);
	}

	//client only
	public int getResourceAmount(ResourceType type, int userIndex) {
		Player client = getPlayerFromIndex(userIndex);
		return client.getResourceAmount(type);
	}

	public List<Integer> getPlayerIndices() {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		indices.addAll(players.keySet());

		return indices;
	}

	public int getPoints(int playerIndex) {
	
		return getPlayerFromIndex(playerIndex).getPoints();
	}
	public boolean isLargestArmy(int playerIndex) {
		Boolean b = this.getAchievements().isLargestArmy(getPlayerFromIndex(playerIndex));
		if (b==null) b = false;
		return b;
	}

	public boolean isLongestRoad(int playerIndex) {
		Boolean b = this.getAchievements().isLongestRoad(getPlayerFromIndex(playerIndex));
		if (b==null) b = false;
		return b;	}

	public CatanColor getPlayerColor(int playerIndex) {
		return getPlayerFromIndex(playerIndex).getColor();
	}

	public String getPlayerName(int playerIndex) {

		return getPlayerFromIndex(playerIndex).getUserName();
	}

	public boolean isGameOver() {
		return this.winnerID != -1;
	}

	public String getWinnerName() {

		return this.getWinner().getUserName();
	}

	//client only
	public boolean isClientWinner(int userID) {
	
		return (this.getWinner().getPlayerID() == userID);
	}

	//client only
	public int getFreeRoads(int userIndex) {
		Player client = getPlayerFromIndex(userIndex);
		return client.getRoadsFree();
	}

	//client only
	public int getFreeSettlements(int userIndex) {
		Player client = getPlayerFromIndex(userIndex);
		return client.getSettlementsFree();
	}

	//client only
	public int getFreeCities(int userIndex) {
		Player client = getPlayerFromIndex(userIndex);
		return client.getCitiesFree();
	}

	//client only
	public int getSoldiers(int userIndex) {
		Player client = getPlayerFromIndex(userIndex);
		return client.getArmies();
	}	
	

	public boolean mustDiscard(int userIndex) {
		if (!getStatus().equalsIgnoreCase("Discarding"))
			return false;
		if (!getPlayerFromIndex(userIndex).canDiscardCard())
			return false;
		return true;
	}	
	
/////Trading
	
	
	public int getTradeResource(ResourceType type)
	{
		return this.tradeModel.getTradeResource(type);
	}

	public String getTradeSenderName() {
		return getPlayerFromIndex(this.tradeModel.getSenderIndex()).getUserName();
	}
	
	public boolean canDomesticTrade(int userID) {
		if (!isActivePlayer(this.getIndexFromPlayerID(userID)))
			return false;
		if (!isStatePlaying())
			return false;
		return true;
	}
	
	public PlayerInfo[] getTradingPartner(int userID) {
		PlayerInfo[] tradingPartners = new PlayerInfo[players.size() - 1];
		int i = 0;
		for (Player p: players.values())
		{
			if (p.getPlayerID() != userID)
			{
				PlayerInfo partner = new PlayerInfo();
				partner.setId(p.getPlayerID());
				partner.setPlayerIndex(p.getPlayerIndex());
				partner.setName(p.getUserName());
				partner.setColor(p.getColor());
				tradingPartners[i] = partner;
				i++;
			}
		}
		return tradingPartners;
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//JOSHUA
	public List<LogEntry> getMessages() {
		return chatModel.getChatLog().toLogEntryList();
	}
	
	public boolean hasTradeOffer() {
		boolean result = false;
		if(this.tradeModel != null) {
			result = true;
		}
		
		return result;
	}	
	///////////////////////

	public List<LogEntry> getGameHistory() {
		return chatModel.getGameLog().toLogEntryList();
	}

	public RobPlayerInfo[] getRobbablePlayersAt(HexLocation robberLoc) {
		ArrayList<RobPlayerInfo> targets = new ArrayList<RobPlayerInfo>();
		for (Player player : players.values()) {
			if (this.canRobPlayerFrom(robberLoc, getActivePlayer().getPlayerIndex(), player.getPlayerIndex()))
				targets.add(new RobPlayerInfo(player.getPlayerID(), player.getPlayerIndex(), player.getUserName(), player.getColor(), player.getHandSize()));
			
		}
		return targets.toArray(new RobPlayerInfo[0]);
	}
	
	
	//////////////////////////////////MISC

	public ChatModel getChatModel() {
		return chatModel;
	}
	
	public boolean stillDiscarding()
	{
		for (Player p: players.values())
		{
			if (p.getHandSize() > 7 && !p.hasDiscarded())
				return true;
		}
		return false;
	}
	
	public void updatePoints()
	{
		for (Player p: players.values())
		{
			p.setPoints(p.calculateVictoryPoints());
		}
	}
	
	public void checkWinner(int playerIndex) //maybe move winner to Achievements
	{
			if (this.getPlayerFromIndex(playerIndex).getPoints() >= 10)
				this.winnerID = 
						this.getPlayerFromIndex(playerIndex).getPlayerID();
	}
	
	//////////////////////////////////SERVER SECTION////////////////////////////////////////////////////////////
	
	public void doSendChat(String message, int playerIndex) throws ViolatedPreconditionException
	{
		if (!this.canSendChat(message, playerIndex))
			throw new ViolatedPreconditionException();
		this.getChatModel().doSendChat(message, this.getPlayerName(playerIndex));
		version++;
	}
	

	public void doAcceptTrade(boolean willAccept, int playerIndex) throws ViolatedPreconditionException
	{
		if (!this.canAcceptTrade(willAccept, playerIndex))
			throw new ViolatedPreconditionException();
		String source = this.getPlayerName(playerIndex);

		if (willAccept)
		{
			this.chatModel.addGameMessage(source + " accepted the trade", source);

			try {
			for (ResourceType type: ResourceType.values())
			{
				int resourceAmount;
				Player sender = this.getPlayerFromIndex(tradeModel.getSenderIndex());
				Player receiver = this.getPlayerFromIndex(tradeModel.getReceiverIndex());
				resourceAmount = this.tradeModel.getResource(type);
				
				receiver.receiveResource(type, resourceAmount);
				sender.sendResource(type, resourceAmount);
			}
			} catch (ModelAccessException | NoRemainingResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			this.chatModel.addGameMessage(source + " rejected the trade", source);

		}
		this.tradeModel = null;
		version++;
	}
	
	public void doDiscardCards(Map<ResourceType, Integer> discardedCards, int playerIndex) throws ViolatedPreconditionException
	{
		if (!canDiscardCard(discardedCards, playerIndex))
			throw new ViolatedPreconditionException();
		for (ResourceType type: ResourceType.values()) {
			try {
				this.getPlayerFromIndex(playerIndex).sendResource(type, discardedCards.get(type));
				this.getBank().receiveResource(type, discardedCards.get(type));
			} catch (NoRemainingResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.getPlayerFromIndex(playerIndex).setHasDiscard();
		
		if (!stillDiscarding()) {
			this.status = "Robbing";
			clearPlayersDiscarding();
		}
		
		version++;
	}
	
	private void clearPlayersDiscarding() {
		for (Player p: players.values())
		{
			p.clearHasDiscarded();
		}
	}

	public void doRollNumber(int roll, int playerIndex) throws ViolatedPreconditionException
	{
		if (!canRollNumber(roll, playerIndex))
			throw new ViolatedPreconditionException();
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " rolled a " + roll, source);
		if (roll == 7) {
			if (stillDiscarding())
				this.status = "Discarding";
			else
				this.status = "Robbing";
		} else {
			for (Player p: players.values()) {
				for (Building b: p.getBuildings()) {
					try {
						b.produce(roll);
					} catch (NoRemainingResourceException e) {
						Log.debug("Bank ran outa its stuff \"No more my pretty!\"");
						//e.printStackTrace();
					}
				}
			}
			status = "Playing";
		}
		version++;
	}
	
	public void doBuildRoad(boolean free, EdgeLocation roadLocation, int playerIndex) throws ViolatedPreconditionException
	{
		if (free) {
			if (!canSetupRoad(playerIndex, roadLocation))
				throw new ViolatedPreconditionException();
		} else {
			if (!canBuildRoad(playerIndex, roadLocation))
				throw new ViolatedPreconditionException();
		}
				
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " built a road", source);

		try {
			if (!free) this.getPlayerFromIndex(playerIndex).buyRoad();
			this.board.buildRoad(this.getPlayerFromIndex(playerIndex).getFreeRoad(), roadLocation);
		} catch (NoRemainingResourceException e) {
			e.printStackTrace();
		}
		if (this.achievements.checkRoads(players, board))
		{
			this.updatePoints();
			this.checkWinner(playerIndex);
		}
		if (this.isStateSetup()) progressSetupTurn();
		version++;
	}

	public void doBuildSettlement(boolean free, VertexLocation vertexLocation, int playerIndex) throws ViolatedPreconditionException
	{
		if (free) {
			if (!canSetupSettlement(playerIndex, vertexLocation))
				throw new ViolatedPreconditionException();
		} else {
			if (!canBuildSettlement(playerIndex, vertexLocation))
				throw new ViolatedPreconditionException();
		}
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " built a settlement", source);
		try {
			if (!free) this.getPlayerFromIndex(playerIndex).buySettlement();
			this.board.buildSettlement(this.getPlayerFromIndex(playerIndex).getFreeSettlement(), vertexLocation);
			if (this.getStatus().equals("SecondRound"))
			{
				Hex[] hexes = this.board.getVertexAt(vertexLocation).getAllHexes();
				for (Hex hex: hexes)
				{
					if (hex instanceof ProductionHex)
					{
						ResourceType type = ResourceType.valueOf(((ProductionHex) hex).getHexType().toString());
						this.bank.sendResource(type, 1);
						this.getPlayerFromIndex(playerIndex).receiveResource(type, 1);
					}
				}
			}
		} catch (NoRemainingResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updatePoints();
		checkWinner(playerIndex);
		version++;
	}
	public void doBuildCity(VertexLocation vertexLocation, int playerIndex) throws ViolatedPreconditionException
	{
		if (!canBuildCity(playerIndex, vertexLocation))
			throw new ViolatedPreconditionException();
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " upgraded to a city", source);
		try {
			this.getPlayerFromIndex(playerIndex).buyCity();
			this.board.buildCity(this.getPlayerFromIndex(playerIndex).getFreeCity(), vertexLocation);
		} catch (NoRemainingResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updatePoints();
		checkWinner(playerIndex);
		version++;
	}
	
	public void doOfferTrade(int receiverIndex, Map<ResourceType, Integer> resourceList, int playerIndex) throws ViolatedPreconditionException
	{
		if (!canOfferTrade(playerIndex, resourceList, receiverIndex))
			throw new ViolatedPreconditionException();
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " offered " + this.getPlayerName(receiverIndex) + " a trade", source);

		this.tradeModel = new TradeModel();
		this.tradeModel.setSenderIndex(playerIndex);
		this.tradeModel.setReceiverIndex(receiverIndex);
		for (ResourceType type: ResourceType.values())
		{
			try {
				this.tradeModel.setResource(type, resourceList.get(type));
			} catch (ModelAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		version++;
	}
	
	public void doMaritimeTrade(int ratio, ResourceType input, ResourceType output, int playerIndex) throws ViolatedPreconditionException
	{
		if(!canMaritimeTrade(playerIndex, ratio, input, output))
			throw new ViolatedPreconditionException();
		try {
			this.getPlayerFromIndex(playerIndex).doMaritimeTrade(ratio, input, output);
		} catch (NoRemainingResourceException e) {
			e.printStackTrace();
		}
		version++;
	}
	
	public void doRobPlayer(HexLocation robLocation, int victimIndex, int playerIndex) throws ViolatedPreconditionException
	{
		if(!canPlaceRobber(playerIndex, robLocation))
			throw new ViolatedPreconditionException();
		if(!canRobPlayerFrom(robLocation, playerIndex, victimIndex))
			throw new ViolatedPreconditionException();
		String victim = "nobody";
		if (victimIndex != -1)
			victim = this.getPlayerName(victimIndex); 
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " moved the robber and robbed " + victim, source);
		this.board.placeRobber(robLocation);
		if (victimIndex != -1)
		{
		try {
			ResourceType rob = this.getPlayerFromIndex(victimIndex).drawRandomResourceCard();
			this.getPlayerFromIndex(playerIndex).receiveResource(rob, 1);
		} catch (NoRemainingResourceException e) {
			
		}
		}
		this.status = "Playing";
		version++;
	}
	
	
	public void doFinishTurn(int playerIndex) throws ViolatedPreconditionException
	{	
		if(!canFinishTurn(playerIndex))
			throw new ViolatedPreconditionException();

		this.getPlayerFromIndex(playerIndex).updateDevCards();
		this.progressTurn();
		
		version++;
	}
	
	public void doBuyDevCard(int playerIndex) throws ViolatedPreconditionException
	{
		if(!canBuyDevCard(playerIndex))
			throw new ViolatedPreconditionException();
		String source = this.getPlayerName(playerIndex);

		try {
			this.getPlayerFromIndex(playerIndex).buyDevelopment();
			this.chatModel.addGameMessage(source + " bought a Development Card", source);
		} catch (NoRemainingResourceException | NoDevCardFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		version++;
	}
	
	public void doSoldier(HexLocation robLocation, int victimIndex, int playerIndex) throws ViolatedPreconditionException
	{
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " used a soldier", source);
		this.getPlayerFromIndex(playerIndex).incrementArmies();
		this.getPlayerFromIndex(playerIndex).playedDevCard();

		this.doRobPlayer(robLocation, victimIndex, playerIndex);
		Player p = this.getPlayerFromIndex(playerIndex);
		try {
			p.returnDevCard(p.findDevCard(DevCardType.KNIGHT));
		} catch (NoDevCardFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.achievements.checkArmies(players))
		{
//here be largest army game model notify
			this.updatePoints();
			this.checkWinner(playerIndex);
		}
		version++;
	}
	
	public void doYear_of_Plenty(ResourceType resource1, ResourceType resource2, int playerIndex) throws ViolatedPreconditionException
	{
		if(!canUseYearOfPlenty(playerIndex, resource1, resource2)) {
			throw new ViolatedPreconditionException();
		}
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " used Year of Plenty and got a " + resource1.toString().toLowerCase() + " and a " + resource2.toString().toLowerCase(), source);
		this.getPlayerFromIndex(playerIndex).playedDevCard();

		try {
			this.getBank().sendResource(resource1, 1);
			this.getBank().sendResource(resource2, 1);
			this.getPlayerFromIndex(playerIndex).receiveResource(resource1, 1);
			this.getPlayerFromIndex(playerIndex).receiveResource(resource2, 1);
		} catch (NoRemainingResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player p = this.getPlayerFromIndex(playerIndex);
		try {
			p.returnDevCard(p.findDevCard(DevCardType.YEAROFPLENTY));
		} catch (NoDevCardFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		version++;
	}
	
	public void doRoad_Building(EdgeLocation spot1, EdgeLocation spot2, int playerIndex) throws ViolatedPreconditionException	
	{
		//TODO - verify that this is correct
		if(spot2 == null) {
			if(!this.canUseRoadBuildingSingle(playerIndex, spot1)) {
				throw new ViolatedPreconditionException();
			}
		} else {
			if(!this.canUseRoadBuilding(playerIndex, spot1, spot2)) {
				throw new ViolatedPreconditionException();
			}
		}
		
		this.board.buildRoad(this.getPlayerFromIndex(playerIndex).getFreeRoad(), spot1);
		this.board.buildRoad(this.getPlayerFromIndex(playerIndex).getFreeRoad(), spot2);
		Player p = this.getPlayerFromIndex(playerIndex);
		try {
			p.returnDevCard(p.findDevCard(DevCardType.ROADBUILDING));
		} catch (NoDevCardFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.achievements.checkRoads(players, board))
		{
			this.updatePoints();
			this.checkWinner(playerIndex);
		}
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " used Road Builder",source);
		this.getPlayerFromIndex(playerIndex).playedDevCard();
		version++;
	}
	
	public void doMonopoly(ResourceType resource, int playerIndex) 
			throws ViolatedPreconditionException
	{
		if(!canUseMonopoly(playerIndex, resource)) {
			throw new ViolatedPreconditionException();
		}
		String source = this.getPlayerName(playerIndex);
		this.chatModel.addGameMessage(source + " used Monopoly and stole everyone's " + resource.toString().toLowerCase(), source);
		this.getPlayerFromIndex(playerIndex).playedDevCard();
		
		for (Player p: players.values())
		{
			if (p.getPlayerIndex() != playerIndex)
			{
				int num = p.getResourceAmount(resource);
				if (num > 0)
				{
					try {
						p.sendResource(resource, num);
						this.getPlayerFromIndex(playerIndex).receiveResource(resource, num);
					} catch (NoRemainingResourceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		Player p = this.getPlayerFromIndex(playerIndex);
		try {
			p.returnDevCard(p.findDevCard(DevCardType.MONOPOLY));
		} catch (NoDevCardFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		version++;
	}
	
	public void doMonument(int playerIndex) throws ViolatedPreconditionException
	{
		if(!this.canUseMonument(playerIndex)) {
			throw new ViolatedPreconditionException();
		}
		Player p = this.getPlayerFromIndex(playerIndex);
		p.setMonuments(p.getVictoryPointsOfMonuments());
		this.updatePoints();
		checkWinner(playerIndex);
		version++;
	}

	public void setID(int gameID) {
		this.gameID = gameID;
	}
	
	public int getID() {
		return this.gameID;
	}
}
