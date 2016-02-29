package client.join;

import java.util.List;

import client.base.*;
import client.data.GameInfo;
import client.data.Games;
import client.data.GamesObserver;
import client.main.ClientPlayer;
import client.modelfacade.ModelFacade;
import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements 
	IPlayerWaitingController, GamesObserver {
	
	private GameInfo curGame;
	private IAction waitAction;
	String[] aiChoices;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		curGame = null;
		Games.sole().registerObserver(this);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}
	
	public IAction getWaitAction() {
		return waitAction;
	}
	
	public void setWaitAction(IAction value) {
		waitAction = value;
	}

	@Override
	public void start() {
		setCurrentGame();
		if(curGame.isFull()) {
			curGame.setPlayerIndex();
			if(getView().isModalShowing()){
				getView().closeModal();
			}
			ModelFacade.getModelFromServer();
		} else {
			try {
				List aiList = ServerFacade.get_instance().listAI();
				aiChoices = makeAIList(aiList); 
				getView().setAIChoices(aiChoices);
				getView().setPlayers(curGame.getPlayerArray());
				if(!getView().isModalShowing()) {
					getView().showModal();
				}
			} catch (ServerException e) {
				System.err.println("Server could not get AI List: " +
						e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void checkIfFull() {
		if(curGame.isFull()) {
			curGame.setPlayerIndex();
			if(getView().isModalShowing()){
				getView().closeModal();
			}
			ModelFacade.getModelFromServer();
		} else {
			getView().setAIChoices(aiChoices);
			getView().setPlayers(curGame.getPlayerArray());
			if(!getView().isModalShowing()) {
				getView().showModal();
			}
		}
	}


	private String[] makeAIList(List aiList) {
		int listSize = aiList.size();
		String[] result = new String[listSize];
		for(int i = 0; i < listSize; i++) {
			String tmpStr = (String) aiList.get(i);
			result[i] = tmpStr;
		}
		
		return result;
	}

	private void setCurrentGame() {
		//Assume since we joined a game it is in our list
		List<GameInfo> games = Games.sole().getGames();
		
		Integer curGameID = ClientPlayer.sole().getGameID();
		for(GameInfo game : games) {
			int tmpGameID = game.getId();
			if(tmpGameID == curGameID) {
				curGame = game;
				break;
			}
		}
	}

	@Override
	public void addAI() {
		//TODO Add five players here
		//Avoid by having poller running pues
		String aiType = getView().getSelectedAI();
		try {
			ServerFacade.get_instance().addAI(aiType);
		} catch (ServerException e) {
			System.err.println("Could not add AI to current game."
					+ "Message from Server Proxy: " + e.getMessage());
			e.printStackTrace();
		}
		checkIfFull();
	}

	@Override
	public void update() {
		setCurrentGame();
		checkIfFull();
	}
}

