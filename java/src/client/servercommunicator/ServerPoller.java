package client.servercommunicator;


/**
 * Periodically pings the server to see if updates need to be synced to the client model
 */
public class ServerPoller {
	
	private IServerProxy proxy;

	/**
	 * constructs new serverpoller
	 */
	public ServerPoller(IServerProxy proxy){
		this.proxy = proxy;
	}

	/**
	 * periodically polls server
	 * @pre serverproxy is properly set up
	 */
	public void poll(){
	};
}
