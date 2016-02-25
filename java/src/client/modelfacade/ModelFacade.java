 package client.modelfacade;

 import org.json.simple.JSONObject;
 

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
}
