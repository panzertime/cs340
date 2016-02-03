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
	private final String[] turnTypes = {"Playing", "Robbing", "Discarding", "FirstRound", "SecondRound"};
	private int turnStatus;
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
		this.bank = new Bank((Map<String,Object>)clientModel.get("bank"));
		this.chatModel = new ChatModel((Map<String,Object>)clientModel.get("chat"),(Map<String,Object>)clientModel.get("log"));
		this.board = new Board((Map<String,Object>)clientModel.get("map"));
		this.version = (Integer)clientModel.get("version");
		this.winner = whichPlayer((Integer)clientModel.get("winner"));
		Map<String,Object> turnTracker = (Map<String,Object>)clientModel.get("TurnTracker");
		this.winner = whichPlayer((Integer)turnTracker.get("currentTurn"));
		this.turnStatus = whichTurnStatus((String)turnTracker.get("status"));
		achievements = new Achievements(whichPlayer((Integer)turnTracker.get("longestRoad")),whichPlayer((Integer)turnTracker.get("largestArmy")));
		Map<String,Object>[] playerList = (Map<String,Object>[])clientModel.get("players");
		for (Map<String,Object> p: playerList)
		{
			players.add(new Player(p));
		}
		
		
	}
	
	
	/*public GameModel(Board board, ArrayList <Player> players, Bank bank, Achievements achievements) throws Exception {
		setBoard(board);
		setPlayers(players);
		setBank(bank);
		setAchievements(achievements);
	}*/
	
	
	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}


	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
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
	
	public int whichTurnStatus(String turnType) throws BadTurnStatusException
	{
		for (int i = 0; i < this.turnTypes.length; i++)
		{
			if (turnType.equals(turnTypes[i])) return i;
		}
		throw new BadTurnStatusException();
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
	public Player getTurn() {
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


	public String getTurnStatus(int i) {
		return turnTypes[i];
	}



	public int getTurnStatus() {
		return turnStatus;
	}


	public void setTurnStatus(int turnStatus) {
		this.turnStatus = turnStatus;
	}
	
	public int getPlayerIndex() {
		// TODO Auto-generated method stub
		return 0;
	}


	public void initModel(Map modelFromServer) {
		// TODO Auto-generated method stub
		
	}


	public boolean inSetupMode() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	public Boolean canDiscardCard(Map<String, Object> resourceList)
	{
		//check status Discarding
		return (this.getTurn().getHand().canDiscardCard() && this.getTurn().getHand().hasCards(resourceList));
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
		return this.getTurn().getHand().hasCards(resourceList);
	}
	
	public Boolean canMaritimeTrade(int ratio, ResourceType type)
	{
		//check status Playing
		//check turn
		//check Player is on correct ratio port
		Boolean b = false;
		try {
			 b = this.getTurn().getHand().hasResource(type, ratio);
		} catch (BadResourceTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return b;
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
		this.getTurn().getHand().hasDevelopmentCost();
		return null;
	}
	
	public Boolean canUseSoldier(HexLocation newRobberLocation, int playerIndex)
	{
		//check status Playing
		//check turn
		this.getTurn().canPlayDevelopmentCard();
		this.getTurn().hasKnightToUse();
		canRobPlayer(newRobberLocation, playerIndex);
		return null;
	}
	
	public Boolean canUseYearOfPlenty(ResourceType one, ResourceType two)
	{
		//check status Playing
		//check turn
		this.getTurn().canPlayDevelopmentCard();
		this.getTurn().hasYearOfPlentyToUse();
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
		this.getTurn().canPlayDevelopmentCard();
		this.getTurn().hasRoadBuildingToUse();
		//canBuildRoad
		//canBuildRoad (also on first location)
		return null;
	}
	
	public Boolean canUseMonopoly(ResourceType type)
	{
		//check status Playing
		//check turn
		this.getTurn().canPlayDevelopmentCard();
		this.getTurn().hasMonopolyToUse();
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
		this.getTurn().getHand().hasRoadCost();
		//check if can Build
		return null;
	}
	
	public Boolean canBuildSettlement(VertexLocation vertex)
	{
		//check status Playing
		//check turn
		this.getTurn().getHand().hasSettlementCost();
		//check if can Build
		return null;
	}
	
	public Boolean canBuildCity(VertexLocation vertex)
	{
		//check status Playing
		//check turn
		this.getTurn().getHand().hasCityCost();
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
