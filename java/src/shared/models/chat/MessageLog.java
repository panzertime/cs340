package shared.models.chat;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.models.exceptions.BadJSONException;

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
		// TODO Auto-generated method stub
		return false;
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
	
}
