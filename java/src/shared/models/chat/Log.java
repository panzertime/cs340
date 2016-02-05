package shared.models.chat;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Log {
	private ArrayList<Message> messageList;
	
	public Log(JSONArray jsonArray)
	{
		for (int i = 0; i < jsonArray.size(); i++)
		{
			messageList.add(new Message((JSONObject)jsonArray.get(i)));
		}
	}
	
	public ArrayList<Message> getMessageList() {
		return messageList;
	}
	
	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}
	
	
}
