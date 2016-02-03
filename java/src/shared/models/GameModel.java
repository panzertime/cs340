package shared.models;

import java.util.ArrayList;
import java.util.Map;

import shared.models.board.Board;
import shared.models.chat.ChatModel;
import shared.models.exceptions.BadPlayerIndexException;
import shared.models.exceptions.BadTurnStatusException;
import shared.models.exceptions.EmptyPlayerListException;

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

}
