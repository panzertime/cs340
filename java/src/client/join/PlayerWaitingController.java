package client.join;

import java.util.List;

import org.json.simple.JSONArray;

import client.base.*;
import client.data.GameInfo;
import client.data.Games;
import client.main.ClientPlayer;
import client.modelfacade.ModelFacade;
import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.exceptions.BadJSONException;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
	
	private GameInfo curGame;
	private IAction waitAction;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		curGame = null;
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
			getView().closeModal();
			ModelFacade.getModelFromServer();
		} else {
			List aiList;
			try {
				aiList = ServerFacade.get_instance().listAI();
				String[] aiChoices = makeAIList(aiList); 
				getView().setAIChoices(aiChoices);
				//getView().setPlayers(players);
				getView().showModal();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String[] makeAIList(List aiList) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setCurrentGame() {
		try {
			List jsonGames = ServerFacade.get_instance().getGames();
			Games translateGames = new Games((JSONArray) jsonGames);	
			List<GameInfo> games = translateGames.getGames();
			
			Integer curGameID = ClientPlayer.sole().getGameID();
			for(GameInfo game : games) {
				int tmpGameID = game.getId();
				if(tmpGameID == curGameID) {
					curGame = game;
					break;
				}
			}
		} catch (ServerException e) {
			System.err.println("Couldn't get games. "
					+ "Message from Server Facade" + e.getMessage());
			e.printStackTrace();
		} catch (BadJSONException e) {
			System.err.println("Badd JSON recieved. Error message:"
					+ e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
	}

}

