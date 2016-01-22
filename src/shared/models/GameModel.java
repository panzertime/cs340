package shared.models;

import java.util.ArrayList;

import shared.models.board.Board;

public class GameModel {
	
	private Board board;
	private ArrayList <Player> players;
	
	/**
	 * @param board the board to  initialize
	 * @param players the players to initialize
	 * @throws Exception
	 */
	public GameModel(Board board, ArrayList <Player> players) throws Exception {
		setBoard(board);
		setPlayers(players);
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

}
