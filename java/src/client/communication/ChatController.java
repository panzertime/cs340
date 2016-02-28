package client.communication;

import java.util.List;

import client.base.Controller;
import client.main.ClientPlayer;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
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
		DoModelFacade doModelFacade = DoModelFacade.sole();
		doModelFacade.doSendChat(message);
	}

	@Override
	public void update() {
		List<LogEntry> entries = GetModelFacade.sole().getMessages();
		getView().setEntries(entries);
	}

}

