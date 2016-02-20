package client.servercommunicator;

import org.json.simple.JSONObject;

import client.modelfacade.ModelFacade;
import shared.model.exceptions.BadJSONException;

/**
 * Periodically pings the server to see if updates need to be synced to the client model
 */
public class ServerPoller extends Thread {
	
	private ServerFacade outbound;

	// for testing, no UI to propagate exceptions to yet 
	private boolean hasFailed;

	/**
	 * constructs new serverpoller
	 */
	public ServerPoller(ServerFacade outbound){
		super();
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
					System.out.println("POLLER says: received null input");
				}
				else {
					System.out.println("POLLER says: setting game model");
					ModelFacade.setModel(result);
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
				
}
