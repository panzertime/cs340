package client.join;

import java.util.List;

import client.base.Controller;
import client.base.IAction;
import client.data.GameInfo;
import client.data.Games;
import client.data.GamesObserver;
import client.data.PlayerInfo;
import client.main.ClientPlayer;
import client.misc.IMessageView;
import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.definitions.CatanColor;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements 
	IJoinGameController, GamesObserver {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		Games.sole().registerObserver(this);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() {
		Games.sole().getGamesFromServer();
		updateGames();
		getJoinGameView().showModal();
	}
	
	private void updateGames() {
		GameInfo[] games = Games.sole().getGameArray();
		PlayerInfo localPlayer = ClientPlayer.sole().getPlayerInfo();
		getJoinGameView().setGames(games, localPlayer);
	}

	@Override
	public void startCreateNewGame() {
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		getNewGameView().closeModal();
		resetNewGameModal();
	}

	@Override
	public void createNewGame() {
		boolean randHexes = getNewGameView().getRandomlyPlaceHexes();
		boolean randNums = getNewGameView().getRandomlyPlaceNumbers();
		boolean randPorts = getNewGameView().getUseRandomPorts();
		String title = getNewGameView().getTitle();
		
		if(title.isEmpty()) {
			messageView.setTitle("Warning!");
			messageView.setMessage("The game title is empty.");
			messageView.showModal();
		} else {
			try {
				ServerFacade.get_instance().createNewGame(randHexes, randNums,
						randPorts, title);
				addYourselfToTheGameYouJustCreated(title);
				updateGames();
				getNewGameView().closeModal();
				resetNewGameModal();
			} catch (ServerException e) {
				System.err.println("Could not add game");
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void resetNewGameModal() {
		getNewGameView().setRandomlyPlaceHexes(false);
		getNewGameView().setRandomlyPlaceNumbers(false);
		getNewGameView().setUseRandomPorts(false);
		getNewGameView().setTitle("");
	}

	/**
	 * Gets all the games from the server and finds the one with the same
	 * name as the user just created. In the event that someone else just
	 * added a game at the same time, it will double check to make sure that
	 * there are no other users in that game
	 * @pre User just created a game, no other user is creating a game with
	 * the exact same name in the exact same moment as this one
	 * @post User will be added to the new game
	 * @param gameName name of the users game
	 * @throws Exception 
	 */
	private void addYourselfToTheGameYouJustCreated(String gameName) throws Exception {
		boolean added = false;
		Games.sole().getGamesFromServer();
		GameInfo[] games = Games.sole().getGameArray();
		for(int i = 0; i < games.length; i++) {
			String gameTitle = games[i].getTitle();
			if(gameTitle.equals(gameName) && games[i].getPlayers().isEmpty()) {
				int gameID = games[i].getId();
				CatanColor defaultColor = CatanColor.RED;
				try {
					ServerFacade.get_instance().joinGame(gameID, defaultColor);
					added = true;
				} catch (ServerException e) {
					System.err.println("Could not add user to the game he"
							+ "just created");
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		if(!added) {
			throw new Exception("Could not add user to his own game");
		}
	}

	@Override
	public void startJoinGame(GameInfo game) {
		enableAllColors();
		disableColors(game);
		int gameID = game.getId();
		ClientPlayer.sole().setGameID(gameID);
		Games.sole().setJoinedGame(gameID);
		getSelectColorView().showModal();
	}

	private void enableAllColors() {
		for(CatanColor color : CatanColor.values()) {
			getSelectColorView().setColorEnabled(color, true);
		}
	}

	private void disableColors(GameInfo game) {
		List<PlayerInfo> playerList = game.getPlayers();
		PlayerInfo me = ClientPlayer.sole().getPlayerInfo();
		int myID = me.getId();
		for(PlayerInfo player : playerList) {
			int playerID = player.getId();
			if(playerID != myID) {
				CatanColor playerColor = player.getColor();
				getSelectColorView().setColorEnabled(playerColor, false);
			}
		}
	}

	@Override
	public void cancelJoinGame() {
		getJoinGameView().closeModal();
		ClientPlayer.sole().setGameID(null);
	}

	@Override
	public void joinGame(CatanColor color) {
		//TODO: check that color is real?
		int gameID = ClientPlayer.sole().getGameID();
		try {
			ServerFacade.get_instance().joinGame(gameID, color);
			
			// If join succeeded
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			joinAction.execute();
		} catch (ServerException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		if(getJoinGameView().isModalShowing() && Games.sole().hasChanged()) {
			getJoinGameView().closeModal();
			updateGames();
			getJoinGameView().showModal();
		}
	}
}

