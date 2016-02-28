 package client.modelfacade;

 import java.util.Map;

import org.json.simple.JSONObject;

import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.Model;
 import shared.model.exceptions.BadJSONException;

public abstract class ModelFacade {

	protected ModelFacade() {
		Model.registerListener(this);
 	}

	protected Model gameModel;

	public void updateModel(Model model) {
		gameModel = model;
 	}
 	
	protected Boolean hasGameModel() {
		if (gameModel == null)
			return false;
		return true;
	}

	synchronized public static void setModel(JSONObject jsonModel) throws BadJSONException {
		Model.shareNewModel(new Model(jsonModel));
	}
	
	public static void getModelFromServer() {
		try {
			Map jsonModel = ServerFacade.get_instance().getModel(null);
			setModel((JSONObject) jsonModel);
		} catch (ServerException e) {
			System.err.println("Could not get a model of current game"
					+ "from the Server. Server error message:\n"
					+ e.getMessage());
			e.printStackTrace();
		} catch (BadJSONException e) {
			System.err.println("JSON recieced from server was corrupted.");
			e.printStackTrace();
		}
	}
}
