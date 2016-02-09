package shared.models;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.logger.Log;
import shared.models.board.Board;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.hex.tiles.water.PortType;
import shared.models.board.vertex.VertexLocation;
import shared.models.chat.ChatModel;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.BadPlayerIndexException;
import shared.models.exceptions.BadStatusException;
import shared.models.exceptions.BadTurnStatusException;
import shared.models.exceptions.EmptyPlayerListException;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.BadResourceTypeException;

public class GameModel {
	
	private Board board;
	private static ArrayList <Player> players;
	private Bank bank;
	private Achievements achievements;
	private Boolean setup;
	private Player turn;
	private Player winner;
	private Integer version;
	private String status;
	private Integer currentTurn;
	private ChatModel chatModel;
	private TradeModel tradeModel;
	private Integer clientID;
	
	/**
	 * @param board the board to  initialize
	 * @param players the players to initialize
	 * @param bank the bank to initialize
	 * @param achievements the achievements to initialize
	 * @throws BadPlayerIndexException 
	 * @throws BadTurnStatusException 
	 * @throws BadStatusException 
	 * @throws Exception
	 */

	public GameModel(JSONObject jsonMap) throws BadJSONException
	{
		if (jsonMap == null) throw new BadJSONException();
		bank = new Bank((JSONObject)jsonMap.get("bank"), (JSONObject)jsonMap.get("deck"));
		chatModel = new ChatModel((JSONObject)jsonMap.get("chat"), (JSONObject)jsonMap.get("log"));
		JSONArray playerList = (JSONArray)jsonMap.get("players");
		if (playerList == null) throw new BadJSONException();
		players = new ArrayList<Player>();
		for (int i = 0; i < playerList.size(); i++)
		{
			JSONObject player = (JSONObject) playerList.get(i);
			players.add(new Player(player, this));
		}
		board = new Board((JSONObject)jsonMap.get("map"), this);
		Long version = ((Long) jsonMap.get("version"));
		if (version == null) throw new BadJSONException();
		this.version = version.intValue();
		Long w = ((Long) jsonMap.get("winner"));
		if (w == null) throw new BadJSONException();
		try {
			winner = GameModel.whichPlayer(w.intValue());
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		
		JSONObject turnTracker = (JSONObject)jsonMap.get("turnTracker");
		if (turnTracker == null) throw new BadJSONException();

		Long currentTurn = ((Long) turnTracker.get("currentTurn"));
		if (currentTurn == null) throw new BadJSONException();
		this.currentTurn = currentTurn.intValue();

		try {
			turn = GameModel.whichPlayer(this.currentTurn);
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		String s = (String)turnTracker.get("status");
		if (s == null) throw new BadJSONException();
		try {
			setStatus(s);
		} catch (BadStatusException e) {
			Log.exception(e);
		}
		Long lR = ((Long) turnTracker.get("longestRoad"));
		Long lA = ((Long) turnTracker.get("largestArmy"));
		if (lR == null || lA == null) throw new BadJSONException();
		try {
			achievements = new Achievements(GameModel.whichPlayer(lR.intValue()),GameModel.whichPlayer(lA.intValue()));
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		if ((JSONObject)jsonMap.get("tradeOffer") == null)
		{ tradeModel = null; }
		else
			tradeModel = new TradeModel((JSONObject)jsonMap.get("tradeOffer"));
	}
	

	public Boolean equalsJSON(JSONObject jsonMap)
	{
		if (jsonMap == null) return false;
		if (bank.equalsJSON((JSONObject)jsonMap.get("bank"), (JSONObject)jsonMap.get("deck")) == false) return false;
		if (chatModel.equalsJSON((JSONObject)jsonMap.get("chat"), (JSONObject)jsonMap.get("log")) == false) return false;
		JSONArray playerList = (JSONArray)jsonMap.get("players");
		if (playerList == null) return false;
		for (int i = 0; i < playerList.size(); i++)
		{
			JSONObject player = (JSONObject) playerList.get(i);
			if (!players.get(i).equalsJSON(player)) return false;
		}
		if (!board.equalsJSON((JSONObject)jsonMap.get("map"), this)) return false;
		Long version = ((Long) jsonMap.get("version"));
		if (version == null) return false;
		if (this.version != version.intValue()) return false;
		Long w = ((Long) jsonMap.get("winner"));
		if (w == null) return false;
			if (w == -1) { if (winner != null) return false;}
			else{
				if (winner == null || winner.getUserIndex() != w.intValue()) return false;
			}		
			
			
		JSONObject turnTracker = (JSONObject)jsonMap.get("turnTracker");
		if (turnTracker == null) return false;
		Long currentTurn = ((Long) turnTracker.get("currentTurn"));
		if (currentTurn == null) return false;
		if (this.currentTurn != currentTurn.intValue()) return false;
		
		
		String s = (String)turnTracker.get("status");
		if (s == null) return false;
		if (!isStatus(s)) return false;
		
		Long lR = ((Long) turnTracker.get("longestRoad"));
		Long lA = ((Long) turnTracker.get("largestArmy"));
		if (lR == null || lA == null) return false;
		if (!achievements.equalsJSON(lR.intValue(),lA.intValue())) return false;
		if ((JSONObject)jsonMap.get("tradeOffer") == null)
		{ if (tradeModel != null) return false; }
		else
			if (!tradeModel.equalsJSON((JSONObject)jsonMap.get("tradeOffer"))) return false;
		
		return true;
	}
	
	private boolean isStatus(String s) {
		if (status.equalsIgnoreCase("Rolling")) 
		{
			return true;
		}
		else if (status.equalsIgnoreCase("Robbing")) 
		{
			return true;
		}
		else if (status.equalsIgnoreCase("Playing")) 
		{
			return true;
		}
		else if (status.equalsIgnoreCase("Discarding")) 
		{
			return true;
		}
		else if (status.equalsIgnoreCase("FirstRound")) 
		{
			return true;
		}
		else if (status.equalsIgnoreCase("SecondRound")) 
		{
			return true;
		}
		return false;
		}


	public void setStatus(String status) throws BadStatusException
	{
		if (status.equalsIgnoreCase("Rolling")) 
		{
			this.status = status;
			return;
		}
		else if (status.equalsIgnoreCase("Robbing")) 
		{
			this.status = status;
			return;
		}
		else if (status.equalsIgnoreCase("Playing")) 
		{
			this.status = status;
			return;
		}
		else if (status.equalsIgnoreCase("Discarding")) 
		{
			this.status = status;
			return;
		}
		else if (status.equalsIgnoreCase("FirstRound")) 
		{
			this.status = status;
			return;
		}
		else if (status.equalsIgnoreCase("SecondRound")) 
		{
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
	 * @param achievements the achievements to set
	 */
	public void setAchievements(Achievements achievements) {
		this.achievements = achievements;
	}


	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 * @throws NullPointerException on null board
	 */
	public void setBoard(Board board) throws NullPointerException {
		if (board == null)
			throw new NullPointerException();
		this.board = board;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 * @throws EmptyPlayerListException on empty list of players
	 * @throws NullPointerException on null list of players
	 */
	public void setPlayers(ArrayList<Player> players) throws NullPointerException, EmptyPlayerListException {
		if (players == null)
			throw new NullPointerException();
		if (players.isEmpty())
			throw new EmptyPlayerListException();
		this.players = players;
	}

	
	
	/**
	 * 
	 * @return int simulating two six-faced die
	 */
	public int getDiceNumber()
	{
		return 0;
		
	}
	
	/**
	 * @post - the methods when the dice is rolled are called
	 */
	public void roll()
	{
		
	}
	
	/**
	 * @param dice - number that will produce
	 * @post - the production methods have been called on the tiles that match dice
	 */
	public void production(int dice)
	{
		
	}
	
	/**
	 * @post - the actions when a seven is rolled have been called 
	 */
	public void sevenRolled()
	{
		
	}
	
	/**
	 * @post turn is set to the Player who has the turn
	 */
	public void getNextTurn()
	{
		
	}
	
	
	public static Player whichPlayer(int index) throws BadPlayerIndexException
	{
		if (index == -1)
		{ return null; }
		for (Player p: players)
		{
			if (p.getUserIndex() == index) return p;
		}
		throw new BadPlayerIndexException();
	}	
	
	/**
	 * @return True if setup
	 */
	public Boolean getSetup() {
		return setup;
	}

	/**
	 * @param setup - True if setup phase
	 */
	public void setSetup(Boolean setup) {
		this.setup = setup;
	}

	/**
	 * @return the player who has the turn
	 */
	public Player getActivePlayer() {
		return turn;
	}

	/**
	 * @param turn - the player who has the current turn
	 */
	public void setTurn(Player turn) {
		this.turn = turn;
	}


	public Player getWinner() {
		return winner;
	}


	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getVersion() {
		return version;
	}


	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public String getActivePlayerStatus() {
		return status;
	}


	public void setTurnStatus(String status) {
		this.status = status;
	}
	
	public int getPlayerIndex() {
		return this.currentTurn;
	}

	public boolean inSetupMode() {
		boolean result = false;
		if(status.equalsIgnoreCase("FirstRound") || status.equalsIgnoreCase("SecondRound")) {
			result = true;
		}
		return result;
	}
	
	
	public String getStatus() {
		return status;
	}

	
//	Preconditions
//	The status of the client model is 'Discarding'
//	You have over 7 cards
//	You have the cards you're choosing to discard
	public Boolean canDiscardCard(Map<String, Object> resourceList) throws BadJSONException
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Discarding");
		b = b && this.getActivePlayer().canDiscardCard();
		b = b && this.getActivePlayer().hasCards(resourceList);
		return b;
	}
	
	//	Preconditions
	//	It is your turn
	//	The client model's status is 'Rolling'
	public Boolean canRollNumber()
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Rolling");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		return b;
	}
	
	public Boolean canOfferTrade(Map<String, Object> resourceList) throws BadJSONException
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && getActivePlayer().hasCards(resourceList);
		return b;
	}
	
	public Boolean canMaritimeTrade(int ratio, ResourceType input, ResourceType output)
	{
		PortType portType = null;
		try {
			if (!this.getBank().getHand().hasResource(output, 1)) return false;
		} catch (BadResourceTypeException e1) {
			// TODO Auto-generated catch block
			Log.exception(e1);		}
		if (ratio == 3)
		{
			portType = PortType.THREE;
		}
		else if (ratio == 2)
		{
			switch (input)
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
			}
		}
		else if (ratio != 4)
		{
			Exception BadPortType = new Exception();
			Log.exception(BadPortType);
		}
		Boolean b = true;
		b = b && this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		try {
			b = b && getActivePlayer().hasResource(input, ratio);
		} catch (BadResourceTypeException e) {

			Log.exception(e);
		}		
		if (portType != null) b = b && getActivePlayer().hasPort(portType);
		return b;
	}
	
	public Boolean canPlaceRobber(HexLocation location)
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && (this.getBoard().canPlaceRobber(location));
		return b;
	}
	
	public Boolean canRobPlayer(HexLocation location, int playerIndex)
	{
		Boolean b = canPlaceRobber(location);
		Player robbed = null;
		try {
			robbed = GameModel.whichPlayer(playerIndex);
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		if (robbed != null) b = b && (robbed.getHandSize() > 0);
		
		return b;
	}
	
	public Boolean canFinishTurn()
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		return b;
	}
	
	public Boolean canBuyDevCard()
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && (this.getBank().getHand().getDevCards().size() > 0);
		b = b && this.getActivePlayer().hasDevelopmentCost();
		return b;
	}
	
	public Boolean canUseSoldier(HexLocation newRobberLocation, int playerIndex)
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && !this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasKnightToUse();
		b = b && canRobPlayer(newRobberLocation, playerIndex);
		return b;
	}
	
	public Boolean canUseYearOfPlenty(ResourceType one, ResourceType two)
	{
		Boolean b = true;
		b = b && this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && !this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasYearOfPlentyToUse();
		try {
			b = b && this.getBank().getHand().hasResource(one, 1);
			b = b && this.getBank().getHand().hasResource(two, 1);
		} catch (BadResourceTypeException e) {
			Log.exception(e);
		}
		return b;
	}
	
	public Boolean canUseRoadBuilding(EdgeLocation one, EdgeLocation two)
	{
		Boolean b = true;
		b = b && this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && !this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasRoadBuildingToUse();
		b = b && this.canBuildRoadWithCard(one);
		if (this.canBuildRoadWithCard(two)  ||
				(this.canBuildTwoRoad(one, two))) ;
			else
				b = false; 
		return b;
	}
	


	public Boolean canUseMonopoly(ResourceType type)
	{
		Boolean b = true;
		b = b && this.getStatus().equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && !this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasMonopolyToUse();
		return b;
	}
	
	public Boolean canUseMonument()
	{
		boolean b = true;
		b = b && this.status.equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && (this.getActivePlayer().getVictoryPointsWithMonuments() >= 10);
		return b;
	}
	
	private boolean canBuildTwoRoad(EdgeLocation one, EdgeLocation two) {
		boolean b = false;
		if ( this.status.equalsIgnoreCase("Playing"))
		{
		b = true;
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());

		b = b && this.getActivePlayer().hasRoadPiece();
		b = b && this.getBoard().canBuildRoadTwo(this.getActivePlayer(), one, two);
		}
		return b;
	}
	

	private Boolean canBuildRoadWithCard(EdgeLocation edge)
	{
		boolean b = false;
		if ( this.status.equalsIgnoreCase("Playing"))
		{
			b = true;
			b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
	
			b = b && this.getActivePlayer().hasRoadPiece();
			b = b && this.getBoard().canBuildRoad(this.getActivePlayer(), edge);
		}
		return b;
	}

	public Boolean canBuildRoad(EdgeLocation edge)
	{
		boolean b = false;
		if ( this.status.equalsIgnoreCase("Playing"))
		{
		b = true;
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());

		b = b && this.getActivePlayer().hasRoadCost();
		b = b && this.getActivePlayer().hasRoadPiece();
		b = b && this.getBoard().canBuildRoad(this.getActivePlayer(), edge);
		}
		else if (this.status.equalsIgnoreCase("FirstRound"))
		{
			b = true;
			b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
			b = b && this.getActivePlayer().hasRoadPiece();
			b = b && this.getBoard().canBuildRoad(this.getActivePlayer(), edge);
		}
		else if (this.status.equalsIgnoreCase("SecondRound"))
		{		/////IF SECONDROUND....CANNOT BUILD OFF OF SETTLEMENT WITH ROAD

			b = true;
			b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
			b = b && this.getActivePlayer().hasRoadPiece();
			b = b && this.getBoard().canBuildSetupRoad(this.getActivePlayer(), edge);
			
		}

		return b;
	}
	
	public Boolean canBuildSettlement(VertexLocation vertex)
	{
		boolean b = true;
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && this.getActivePlayer().hasSettlementPiece();
		if (this.inSetupMode())
		{
		b = b && this.getBoard().canBuildSetupSettlement(this.getActivePlayer(), vertex);
		}
		else
		{
			b = b && this.status.equalsIgnoreCase("Playing");
		b = b && this.getActivePlayer().hasSettlementCost();
		b = b && this.getBoard().canBuildSettlement(this.getActivePlayer(), vertex);
		}
		return b;
	}
	
	/**
	 * 
	 * @param vertex
	 * @return
	 */
	public Boolean canBuildCity(VertexLocation vertex)
	{
		boolean b = true;
		b = b && this.status.equalsIgnoreCase("Playing");
		b = b && (this.getClientID() == this.getActivePlayer().getPlayerID());
		b = b && this.getActivePlayer().hasCityCost();
		b = b && this.getActivePlayer().hasCityPiece();
		b = b && this.getBoard().canBuildCity(this.getActivePlayer(), vertex);
		
		return b;
	}
	
	/**
	 * checks that the person has been offered a trade and that they have the 
	 * resources
	 * @pre none
	 * @post game will run
	 * @return if a given trade can be made
	 * @throws BadJSONException 
	 */
	public Boolean canAcceptTrade() throws BadJSONException
	{
		boolean b = false;
		if (this.tradeModel != null) //you have been offered a trade
		{
		Player receiver = this.tradeModel.getReceiver();
		if (receiver.getPlayerID() == this.getClientID())
			b = receiver.hasCards(this.tradeModel.getResourcesToGive());
		}	
		return b;
	}

	/**
	 * If a game is in state, this will always return true
	 * @post game in state
	 * @post true
	 * @return true
	 */
	public Boolean canSendChat()
	{	
		boolean result = true;
		if(clientID == null) {
			result = false;
		}
		return result;
	}


	public Integer getClientID() {
		return clientID;
	}


	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}
	
	

}
