package shared.models;

import java.util.ArrayList;
import java.util.Map;

import shared.models.board.Board;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.chat.ChatModel;
import shared.models.exceptions.BadPlayerIndexException;
import shared.models.exceptions.BadTurnStatusException;
import shared.models.exceptions.EmptyPlayerListException;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.BadResourceTypeException;

public class GameModel {
	
	private Board board;
	private ArrayList <Player> players;
	private Bank bank;
	private Achievements achievements;
	private Boolean setup;
	private Player turn;
	private Player winner;
	private int version;
	private String status;
	private int currentTurn;
	private ChatModel chatModel;
	
	/**
	 * @param board the board to  initialize
	 * @param players the players to initialize
	 * @param bank the bank to initialize
	 * @param achievements the achievements to initialize
	 * @throws BadPlayerIndexException 
	 * @throws BadTurnStatusException 
	 * @throws Exception
	 */
	
	public GameModel(Map<String, Object> clientModel) throws BadPlayerIndexException, BadTurnStatusException
	{
		this.bank = new Bank((Map<String,Object>)clientModel.get("bank"), (Map<String,Object>)clientModel.get("deck"));
		this.chatModel = new ChatModel((Map<String,Object>)clientModel.get("chat"),(Map<String,Object>)clientModel.get("log"));
		Map<String,Object>[] playerList = (Map<String,Object>[])clientModel.get("players");
		for (Map<String,Object> p: playerList)
		{
			players.add(new Player(p));
		}
		this.board = new Board((Map<String,Object>)clientModel.get("map"));
		this.version = (Integer)clientModel.get("version");
		this.winner = whichPlayer((Integer)clientModel.get("winner"));
		Map<String,Object> turnTracker = (Map<String,Object>)clientModel.get("TurnTracker");
		this.currentTurn = (Integer)turnTracker.get("currentTurn");
		this.turn = whichPlayer(this.currentTurn);
		this.status = (String)turnTracker.get("status");
		achievements = new Achievements(whichPlayer((Integer)turnTracker.get("longestRoad")),whichPlayer((Integer)turnTracker.get("largestArmy")));
		
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
	
	
	public Player whichPlayer(int index) throws BadPlayerIndexException
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
	
	
	
	public Boolean canDiscardCard(Map<String, Object> resourceList) throws BadResourceTypeException
	{
		//check status Discarding
		return (this.getActivePlayer().canDiscardCard() && this.getActivePlayer().hasCards(resourceList));
	}
	
	public Boolean canRollNumber()
	{
		//check status Rolling
		//check turn
		return null;
	}
	
	public Boolean canOfferTrade(Map<String, Object> resourceList)
	{
		//check status Playing
		//check turn
		Boolean canOfferTrade = false;
		try {
			canOfferTrade = getActivePlayer().hasCards(resourceList);
		} catch (BadResourceTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return canOfferTrade;
	}
	
	public Boolean canMaritimeTrade(int ratio, ResourceType type)
	{
		//check status Playing
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
		//check status Playing
		//check turn
		//robber has different location
		//player being robbed has resource cards
		return null;
	}
	
	public Boolean canFinishTurn()
	{
		//check status Playing
				//check turn
		return null;
	}
	
	public Boolean canBuyDevCard()
	{
		//check status Playing
				//check turn
		//Bank.getDevCards.size > 0
		this.getActivePlayer().hasDevelopmentCost();
		return null;
	}
	
	public Boolean canUseSoldier(HexLocation newRobberLocation, int playerIndex)
	{
		//check status Playing
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasKnightToUse();
		canRobPlayer(newRobberLocation, playerIndex);
		return null;
	}
	
	public Boolean canUseYearOfPlenty(ResourceType one, ResourceType two)
	{
		//check status Playing
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
		//check status Playing
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasRoadBuildingToUse();
		//canBuildRoad
		//canBuildRoad (also on first location)
		return null;
	}
	
	public Boolean canUseMonopoly(ResourceType type)
	{
		//check status Playing
		//check turn
		this.getActivePlayer().canPlayDevelopmentCard();
		this.getActivePlayer().hasMonopolyToUse();
		return null;
	}
	
	public Boolean canUseMonument()
	{
		//check status Playing
		//check turn
		//enough monuments to win game
		return null;
	}
	
	public Boolean canBuildRoad(EdgeLocation edge)
	{
		//check status Playing
		//check turn
		this.getActivePlayer().hasRoadCost();
		//check if can Build
		return null;
	}
	
	public Boolean canBuildSettlement(VertexLocation vertex)
	{
		//check status Playing
		//check turn
		this.getActivePlayer().hasSettlementCost();
		//check if can Build
		return null;
	}
	
	public Boolean canBuildCity(VertexLocation vertex)
	{
		//check status Playing
		//check turn
		this.getActivePlayer().hasCityCost();
		//check if can Build
		return null;
	}
	
	public Boolean canAcceptTrade()
	{
		
		return null;
	}
	
	public Boolean canSendChat()
	{	
		return null;
	}

}
