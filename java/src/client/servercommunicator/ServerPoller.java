package client.servercommunicator;

import shared.models.ModelFacade;
import shared.models.exceptions.*;
import org.json.simple.JSONObject;

/**
 * Periodically pings the server to see if updates need to be synced to the client model
 */
public class ServerPoller extends Thread {
	
	private ServerFacade outbound;
	private ModelFacade inbound;

	// for testing, no UI to propagate exceptions to yet 
	private boolean hasFailed;

	/**
	 * constructs new serverpoller
	 */
	public ServerPoller(ServerFacade outbound, ModelFacade inbound){
		super();

		this.inbound = inbound;
		this.outbound = outbound;

		hasFailed = false;
	}

	public boolean getFailed(){
	// for debugging, really
		return hasFailed;
	}

	/**
	 * periodically polls server
	 * @pre serverproxy is properly set up
	 */
	public synchronized JSONObject poll(){
		// SF can't return a bad JSON or anything like that;
		// anything other than "you have latest" or "here is latest"
		// results in a HTTP 400, which becomes a ServerException.
		// Later we will probably propagate all the way to UI.
		try { 
			JSONObject model = new JSONObject(outbound.getModel(0));
			return model;
		}
		catch(Exception e){ 
			hasFailed = true;
			return null; 
		}
	}

	@Override
	public void run(){
		while(true){
			try {
				this.wait(1500);
				JSONObject result = poll();
				if(result != null){
					inbound = new ModelFacade(result, outbound.get_player_id());	
				}
			}
			catch(InterruptedException e){
				// do nothing, we will simply skip this poll and wait for the next one
			}
			catch(BadJSONException e){
				// still do nothing, we should never get bad JSON from the server
			}
		}
	}
				
}
