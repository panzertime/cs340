package shared.models;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.logger.Log;
import shared.models.board.Board;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
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
		JSONObject[] playerList = (JSONObject[])jsonMap.get("players");
		if (playerList == null) throw new BadJSONException();
		for (JSONObject p: playerList)
		{
			players.add(new Player(p));
		}
		board = new Board((JSONObject)jsonMap.get("map"));
		version = (Integer)jsonMap.get("version");
		if (version == null) throw new BadJSONException();
		Integer w = (Integer)jsonMap.get("winner");
		if (w == null) throw new BadJSONException();
		try {
			winner = GameModel.whichPlayer(w);
		} catch (BadPlayerIndexException e) {
			// TODO Auto-generated catch block
			Log.exception(e);
		}
		
		JSONObject turnTracker = (JSONObject)jsonMap.get("TurnTracker");
		if (turnTracker == null) throw new BadJSONException();

		currentTurn = (Integer)turnTracker.get("currentTurn");
		if (currentTurn == null) throw new BadJSONException();

		try {
			turn = GameModel.whichPlayer(this.currentTurn);
		} catch (BadPlayerIndexException e) {
			// TODO Auto-generated catch block
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
		Integer lR = (Integer)turnTracker.get("longestRoad");
		Integer lA = (Integer)turnTracker.get("largestArmy");
		if (lR == null || lA == null) throw new BadJSONException();
		try {
			achievements = new Achievements(GameModel.whichPlayer(lR),GameModel.whichPlayer(lA));
		} catch (BadPlayerIndexException e) {
			// TODO Auto-generated catch block
			Log.exception(e);
		}
		if ((JSONObject)jsonMap.get("tradeOffer") == null)
		{}
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
		return (this.getStatus().equalsIgnoreCase("Discarding") && this.getActivePlayer().canDiscardCard() && this.getActivePlayer().hasCards(resourceList));
	}
	
//	Preconditions
//	It is your turn
//	The client model’s status is ‘Rolling’
	public Boolean canRollNumber()
	{
		this.getStatus().equalsIgnoreCase("Rolling");
		//check turn
		return null;
	}
	
	public Boolean canOfferTrade(Map<String, Object> resourceList) throws BadJSONException
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		Boolean canOfferTrade = false;
		canOfferTrade = getActivePlayer().hasCards(resourceList);
		return canOfferTrade;
	}
	
	public Boolean canMaritimeTrade(int ratio, ResourceType type)
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		//check Player is on correct ratio port
		Boolean canMaritimeTrade = false;
		try {
			canMaritimeTrade = getActivePlayer().hasResource(type, ratio);
		} catch (BadResourceTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return canMaritimeTrade;
	}
	
	public Boolean canRobPlayer(HexLocation location, int playerIndex)
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		//robber has different location
		//player being robbed has resource cards
		return null;
	}
	
	public Boolean canFinishTurn()
	{
		this.getStatus().equalsIgnoreCase("Playing");
				//check turn
		return null;
	}
	
	public Boolean canBuyDevCard()
	{
		this.getStatus().equalsIgnoreCase("Playing");
				//check turn
		//Bank.getDevCards.size > 0
		this.getActivePlayer().hasDevelopmentCost();
		return null;
	}
	
	public Boolean canUseSoldier(HexLocation newRobberLocation, int playerIndex)
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasKnightToUse();
		canRobPlayer(newRobberLocation, playerIndex);
		return null;
	}
	
	public Boolean canUseYearOfPlenty(ResourceType one, ResourceType two)
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasYearOfPlentyToUse();
		try {
			this.getBank().getHand().hasResource(one, 1);
			this.getBank().getHand().hasResource(two, 1);
		} catch (BadResourceTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean canUseRoadBuilding(EdgeLocation one, EdgeLocation two)
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasRoadBuildingToUse();
		//canBuildRoad
		//canBuildRoad (also on first location)
		return null;
	}
	
	public Boolean canUseMonopoly(ResourceType type)
	{
		this.getStatus().equalsIgnoreCase("Playing");
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasMonopolyToUse();
		return null;
	}
	
	public Boolean canUseMonument()
	{
		boolean result = false;
		if(this.status.equals("Playing")) {
			//check turn
			//enough monuments to win game
		}
		return result;
	}
	
	public Boolean canBuildRoad(EdgeLocation edge)
	{
		boolean result = false;
		if(this.status.equals("Playing")) {
			//check turn
			//check if can Build
		}
		this.getActivePlayer().hasRoadCost();

		return null;
	}
	
	public Boolean canBuildSettlement(VertexLocation vertex)
	{
		boolean result = false;
		if(this.status.equals("Playing")) {
			//check turn
			//check if can Build
		}
		this.getActivePlayer().hasSettlementCost();
		return result;
	}
	
	/**
	 * 
	 * @param vertex
	 * @return
	 */
	public Boolean canBuildCity(VertexLocation vertex)
	{
		boolean result = false;
		if(this.status.equals("Playing")) {
			//TODO: Check turn
			result = this.getActivePlayer().hasCityCost();
			if(result) {
				//TODO: check if can Build
			}
		}
		return null;
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
		boolean result = false;
		//TODO add current player
		Player receiver = this.tradeModel.getReceiver();
			result = receiver.hasCards(this.tradeModel.getResources());
		return result;
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

}
