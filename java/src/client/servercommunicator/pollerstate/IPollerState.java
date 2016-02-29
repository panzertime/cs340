package client.servercommunicator.pollerstate;

import org.json.simple.JSONObject;

public interface IPollerState {

	public void run();

	public boolean getHasFailed();

}
