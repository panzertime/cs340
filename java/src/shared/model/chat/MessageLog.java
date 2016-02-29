package shared.model.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.communication.LogEntry;
import shared.model.exceptions.BadJSONException;

public class MessageLog {
	private ArrayList<Message> messageList;
	
	public MessageLog()
	{
		messageList = new ArrayList<Message>();
	}

	public MessageLog(JSONArray messageLine) throws BadJSONException
	{
		messageList = new ArrayList<Message>();
		for (int i = 0; i < messageLine.size(); i++)
		{
			messageList.add(new Message((JSONObject)messageLine.get(i)));
		}
	}
	
	public boolean equalsJSON(JSONArray messageLine) {
		if (messageLine.size() != messageList.size()) return false;
		for (int i = 0; i < messageLine.size(); i++)
		{
			if (!messageList.get(i).equalsJSON((JSONObject)messageLine.get(i))) return false;
		}
		return true;
	}
	
	
	public int getSize()
	{
		return messageList.size();
	}
	
	public ArrayList<Message> getMessageList() {
		return messageList;
	}
	
	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}

	public List<LogEntry> toLogEntryList() {
		List<LogEntry> result = new ArrayList<LogEntry>();
		for(Message message : messageList) {
			LogEntry logList = message.toLogEntry();
			result.add(logList);
		}
		return result;
	}
	
}
