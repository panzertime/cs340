package client.main;

import client.data.PlayerInfo;
import shared.model.definitions.CatanColor;

public class ClientPlayer {
	private static ClientPlayer sole;
	private PlayerInfo clientInfo;
	private Integer curGameID;
	
	private ClientPlayer() {
		clientInfo = new PlayerInfo();
		curGameID = null;
	}
	
	public static ClientPlayer sole() {
		if(sole == null) {
			sole = new ClientPlayer();
		}
		return sole;
	}
	
	public void setUserID(int userID) {
		clientInfo.setId(userID);
	}
	
	public void setUserIndex(int userIndex) {
		clientInfo.setPlayerIndex(userIndex);
	}
	
	public void setUserColor(CatanColor color) {
		clientInfo.setColor(color);
	}
	
	public void setUserName(String name) {
		clientInfo.setName(name);
	}
	
	public Integer getUserID() {
		return clientInfo.getId();
	}
	
	public Integer getUserIndex() {
		return clientInfo.getPlayerIndex();
	}
	
	public CatanColor getUserColor() {
		return clientInfo.getColor();
	}
	
	public String getUserName() {
		return clientInfo.getName();
	}

	public PlayerInfo getPlayerInfo() {
		return clientInfo;
	}

	public void setGameID(Integer gameID) {
		curGameID = gameID;
	}
	
	public Integer getGameID() {
		return curGameID;
	}

	public boolean hasJoinedGame() {
		boolean result = false;
		if(curGameID != null) {
			result = true;
		}
		
		return result;
	}

	public void updateClientPlayer(PlayerInfo player) {
		CatanColor playerColor = player.getColor();
		CatanColor clientColor = clientInfo.getColor();
		if(!playerColor.equals(clientColor)){
			clientInfo.setColor(playerColor);
		}
	}
}
