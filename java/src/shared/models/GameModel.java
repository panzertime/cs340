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
		players = new ArrayList<Player>();
		for (int i = 0; i < playerList.size(); i++)
		{
			JSONObject player = (JSONObject) playerList.get(i);
			players.add(new Player(player));
		}
		board = new Board((JSONObject)jsonMap.get("map"));
		version = ((Long) jsonMap.get("version")).intValue();
		if (version == null) throw new BadJSONException();
		Integer w = ((Long) jsonMap.get("winner")).intValue();
		if (w == null) throw new BadJSONException();
		try {
			winner = GameModel.whichPlayer(w);
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		
		JSONObject turnTracker = (JSONObject)jsonMap.get("turnTracker");
		if (turnTracker == null) throw new BadJSONException();

		currentTurn = ((Long) turnTracker.get("currentTurn")).intValue();
		if (currentTurn == null) throw new BadJSONException();

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
			// TODO Auto-generated catch block
			Log.exception(e);
		}
		Integer lR = ((Long) turnTracker.get("longestRoad")).intValue();
		Integer lA = ((Long) turnTracker.get("largestArmy")).intValue();
		if (lR == null || lA == null) throw new BadJSONException();
		try {
			achievements = new Achievements(GameModel.whichPlayer(lR),GameModel.whichPlayer(lA));
		} catch (BadPlayerIndexException e) {
			// TODO Auto-generated catch block
			Log.exception(e);
		}
		if ((JSONObject)jsonMap.get("tradeOffer") == null)
		{ tradeModel = null; }
		else
			tradeModel = new TradeModel((JSONObject)jsonMap.get("tradeOffer"));
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
		if(status.equals("FirstRound") || status.equals("SecondRound")) {
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
//	The client model�s status is �Rolling�
	public Boolean canRollNumber()
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Rolling");
		//check turn
		return b;
	}
	
	public Boolean canOfferTrade(Map<String, Object> resourceList) throws BadJSONException
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		b = b && getActivePlayer().hasCards(resourceList);
		return b;
	}
	
	public Boolean canMaritimeTrade(int ratio, ResourceType type)
	{
		PortType portType = null;
		if (ratio == 3)
		{
			portType = PortType.THREE;
		}
		else if (ratio == 2)
		{
			switch (type)
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
		//check turn
		try {
			b = b && getActivePlayer().hasResource(type, ratio);
		} catch (BadResourceTypeException e) {

			Log.exception(e);
		}		
		if (portType != null) b = b && getActivePlayer().hasPort(portType);
		return b;
	}
	
	public Boolean canRobPlayer(HexLocation location, int playerIndex)
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		//robber has different location
		Player robbed = null;
		try {
			robbed = GameModel.whichPlayer(playerIndex);
		} catch (BadPlayerIndexException e) {
			Log.exception(e);
		}
		b = b && (robbed.getHandSize() > 0);
		
		return b;
	}
	
	public Boolean canFinishTurn()
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
				//check turn
		return b;
	}
	
	public Boolean canBuyDevCard()
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
				//check turn
		b = b && (this.getBank().getHand().getDevCards().size() > 0);
		b = b && this.getActivePlayer().hasDevelopmentCost();
		return b;
	}
	
	public Boolean canUseSoldier(HexLocation newRobberLocation, int playerIndex)
	{
		Boolean b = this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		b = b && this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasKnightToUse();
		b = b && canRobPlayer(newRobberLocation, playerIndex);
		return b;
	}
	
	public Boolean canUseYearOfPlenty(ResourceType one, ResourceType two)
	{
		Boolean b = true;
		b = b && this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		b = b && this.getActivePlayer().canPlayDevelopmentCard();
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
		//check turn
		b = b && this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasRoadBuildingToUse();
		//canBuildRoad minus cost
		//canBuildRoad (also on first location) minus cost
		return b;
	}
	
	public Boolean canUseMonopoly(ResourceType type)
	{
		Boolean b = true;
		b = b && this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		b = b && this.getActivePlayer().canPlayDevelopmentCard();
		b = b && this.getActivePlayer().hasMonopolyToUse();
		return b;
	}
	
	public Boolean canUseMonument()
	{
		boolean b = true;
		b = b && this.status.equals("Playing");
			//check turn
		b = b && (this.getActivePlayer().getVictoryPointsWithMonuments() >= 10);
		return b;
	}
	
	public Boolean canBuildRoad(EdgeLocation edge)
	{
		boolean b = true;
		b = b && this.status.equals("Playing");
			//check turn

		b = b && this.getActivePlayer().hasRoadCost();
		b = b && this.getActivePlayer().hasRoadPiece();
		b = b && this.getBoard().canBuildRoad(this.getActivePlayer(), edge);
		
		/////IF SECONDROUND....CANNOT BUILD OFF OF SETTLEMENT WITH ROAD

		return b;
	}
	
	public Boolean canBuildSettlement(VertexLocation vertex)
	{
		boolean b = true;
		b = b && this.status.equals("Playing");
			//check turn
		b = b && this.getActivePlayer().hasSettlementCost();
		b = b && this.getActivePlayer().hasSettlementPiece();
		b = b && this.getBoard().canBuildSettlement(this.getActivePlayer(), vertex);
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
		b = b && this.status.equals("Playing");
			//TODO: Check turn
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
			b = receiver.hasCards(this.tradeModel.getResources());
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
		return true;
	}


	public Integer getClientID() {
		return clientID;
	}


	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}
	
	

}
