package client.communication;

import java.util.List;

import client.base.*;
import client.main.ClientPlayer;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import shared.model.chat.MessageLog;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController,
	GetModelFacadeListener {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		
		int playerIndex = ClientPlayer.sole().getUserIndex();
		try {
			ServerFacade.get_instance().sendChat(playerIndex, message);
		} catch (ServerException e) {
			System.err.println("Failed to send chat. Message from server: \n"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		MessageLog messages = GetModelFacade.sole().getMessages();
		List<LogEntry> entries = messages.toLogEntryList();
		getView().setEntries(entries);
	}

}

