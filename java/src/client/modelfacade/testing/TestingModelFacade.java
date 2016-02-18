package client.modelfacade.testing;

import org.json.simple.JSONObject;

import client.modelfacade.ModelFacade;
import shared.model.Model;

public class TestingModelFacade extends ModelFacade {


	private TestingModelFacade() {
		super();
	}
	
	private static TestingModelFacade singleton;
	
	public static TestingModelFacade sole() {
		if (singleton == null)
			singleton = new TestingModelFacade();
		return singleton;
	}

	
	public Boolean hasModel() {
		if (gameModel == null)
			return false;
		return true;
	}

	public boolean equalsJSON(JSONObject jsonMap) {
		return this.gameModel.equalsJSON(jsonMap);
	}
	
	public void emptyModel() {
		Model.shareNewModel(null);
	}

}
