package shared.models.chat;

import java.util.Map;

public class ChatModel {
	private Log chatLog;
	private Log gameLog;
	public ChatModel(Map<String, Object> chatList, Map<String, Object> gameList)
	{
		chatLog = new Log((Map<String, Object>[])chatList.get("lines"));
		gameLog = new Log((Map<String, Object>[])gameList.get("lines"));
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
