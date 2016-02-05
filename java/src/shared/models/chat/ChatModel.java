package shared.models.chat;

import java.util.Map;

import org.json.simple.JSONObject;

public class ChatModel {
	private MessageLog chatLog;
	private MessageLog gameLog;
	public ChatModel(JSONObject chatList, JSONObject gameList)
	{
		chatLog = new MessageLog((JSONObject[])chatList.get("lines"));
		gameLog = new MessageLog((JSONObject[])gameList.get("lines"));
	}
	public MessageLog getChatLog() {
		return chatLog;
	}
	public void setChatLog(MessageLog chatLog) {
		this.chatLog = chatLog;
	}
	public MessageLog getGameLog() {
		return gameLog;
	}
	public void setGameLog(MessageLog gameLog) {
		this.gameLog = gameLog;
	}
	
	
}
