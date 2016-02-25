 package client.modelfacade;

 import org.json.simple.JSONObject;
 

import shared.model.Model;
 import shared.model.exceptions.BadJSONException;

public abstract class ModelFacade {

	protected ModelFacade() {
		Model.registerListener(this);
 	}

	protected Integer userID;
	protected Model gameModel;

	public void updateModel(Model model) {
		gameModel = model;
 	}
 	
	public void setUserID(Integer userID) {
		this.userID = userID; 
 	}

	synchronized public static void setModel(JSONObject jsonModel) throws BadJSONException {
		Model.shareNewModel(new Model(jsonModel));
	}
}
