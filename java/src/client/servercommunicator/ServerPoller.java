package client.servercommunicator;

import org.json.simple.JSONObject;

import client.modelfacade.ModelFacade;
import client.servercommunicator.pollerstate.IPollerState;
import client.servercommunicator.pollerstate.PollerJoinGameState;
import client.servercommunicator.pollerstate.PollerPlayingState;
import shared.model.exceptions.BadJSONException;

/**
 * Periodically pings the server to see if updates need to be synced to the client model
 */
public class ServerPoller extends Thread {
	
	//private ServerFacade outbound;
	private IPollerState state;

	// for testing, no UI to propagate exceptions to yet 
	//private boolean hasFailed;

	/**
	 * constructs new serverpoller
	 */
	public ServerPoller(/*ServerFacade outbound*/){
		super();
		//this.outbound = outbound;
		//hasFailed = false;
		state = null;
	}

	public boolean getFailed(){
	// for debugging, really
		return state.getHasFailed();
	}

	@Override
	synchronized public void run(){
		while(true){
			try {
				Thread.sleep(1500);
				state.run();
			} catch(InterruptedException e){
				System.out.println("POLLER says: dying");
				System.out.println("Poller stopped by interrupt");
				return;
			}
		}
	}

	public void setPollerJoinGameState() {
		state = new PollerJoinGameState();
	}

	public void setPollerPlayingState() {
		state = new PollerPlayingState();
	}
				
}
