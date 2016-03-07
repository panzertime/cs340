package client.servercommunicator.pollerstate;

import org.json.simple.JSONObject;

import client.modelfacade.ModelFacade;
import client.servercommunicator.ServerFacade;
import shared.model.exceptions.BadJSONException;

public class PollerPlayingState implements IPollerState {

	private Integer version;
	private boolean hasFailed;
	
	public PollerPlayingState() {
		version = null;
		hasFailed = false;
	}
	
	/**
	 * periodically polls server
	 * @pre serverproxy is properly set up
	 */	
	//Used in Playing State
	public synchronized JSONObject poll(){
		// SF can't return a bad JSON or anything like that;
		// anything other than "you have latest" or "here is latest"
		// results in a HTTP 400, which becomes a ServerException.
		// Later we will probably propagate all the way to UI.
		try { 
			JSONObject model = new JSONObject(ServerFacade.get_instance().getModel(version));
			return model;
		}
		catch(Exception e){ 	
		//	System.out.println("Poller exception: " + e.toString());

			hasFailed = true;
			return null; 
		}
	}
	

	@Override
	public void run(){
		while(true){
			try {
				Thread.sleep(1500);
				JSONObject result = poll();
				if(result == null){
					//Means no update
					//System.out.println("POLLER says: received null input");
				}
				else {
					//means update
					//System.out.println("POLLER says: setting game model");
					Long winner = (Long) result.get("winner");
					Integer intWinner = winner.intValue();
					if(intWinner == -1) {
						Long newVersion = (Long) result.get("version");
						Integer newIntVersion = newVersion.intValue();
						version = newIntVersion;
						ModelFacade.setModel(result);
					} else {
						ServerFacade.get_instance().setPollerJoinGameState();
						break;
					}
				}
			}
			catch(InterruptedException e){
				System.out.println("POLLER says: dying");
				System.out.println("Poller stopped by interrupt");
				return;
			}
			catch(BadJSONException e){
				System.out.println("Poller exception: " + e.toString());
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean getHasFailed() {
		return hasFailed;
	}

}
