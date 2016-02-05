package shared.models.chat;

import java.util.Map;

import org.json.simple.JSONObject;

public class ChatModel {
	private Log chatLog;
	private Log gameLog;
	public ChatModel(JSONObject chatList, JSONObject gameList)
	{
		chatLog = new Log((JSONObject[])chatList.get("lines"));
		gameLog = new Log((JSONObject[])gameList.get("lines"));
	}
	public Log getChatLog() {
		return chatLog;
	}
	public void setChatLog(Log chatLog) {
		this.chatLog = chatLog;
	}
	public Log getGameLog() {
		return gameLog;
	}
	public void setGameLog(Log gameLog) {
		this.gameLog = gameLog;
	}
	
	
}
