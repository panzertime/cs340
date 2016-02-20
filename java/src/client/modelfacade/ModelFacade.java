package client.modelfacade;

import org.json.simple.JSONObject;

import client.data.PlayerInfo;
import shared.model.Model;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;

public abstract class ModelFacade {
	
	protected ModelFacade() {
		Model.registerListener(this);
	}

	protected PlayerInfo playerInfo;

	protected Model gameModel;
	
	public void updateModel(Model model) {
		gameModel = model;
	}
	
	public void setUserID(Integer userID) {
		this.playerInfo.setId(userID); 
	}
	
	public void setUserIndex(Integer userIndex) {
		this.playerInfo.setPlayerIndex(userIndex);
	}
	
	public void setUserColor(CatanColor color) {
		this.playerInfo.setColor(color);
	}
	
	public void setUserName(String name) {
		this.playerInfo.setName(name);
	}
		
	synchronized public static void setModel(JSONObject jsonModel) throws BadJSONException {
		Model.shareNewModel(new Model(jsonModel));
	}

}
