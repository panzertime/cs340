package client.servercommunicator;

public class FalseProxy implements IServerProxy {

	@Override
	public boolean loginUser(JSONObject credentials) throws ServerProxyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerUser(JSONObject credentials) throws ServerProxyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONObject listGames() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject createGame(JSONObject createGameRequest) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(JSONObject joinGameRequest) throws ServerProxyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveGame(JSONObject saveGameRequest) throws ServerProxyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONObject loadGame(JSONObject loadGameRequest) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getModel(JSONObject versionNumber) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject reset() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getCommands() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject executeCommands(JSONObject listOfCommands) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAI(JSONObject addAIRequest) throws ServerProxyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONObject listAI() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject sendChat(JSONObject sendChat) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject rollNumber(JSONObject rollNumber) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject robPlayer(JSONObject robPlayer) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject finishTurn(JSONObject JSONObject) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject buyDevCard(JSONObject buyDevCard) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject yearOfPlenty(JSONObject yearOfPlenty) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject roadBuilding(JSONObject roadBuilding) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject soldier(JSONObject soldier) throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject monopoly() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject monument() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject buildRoad() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject buildSettlement() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject buildCity() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject offerTrade() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject acceptTrade() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject maritimeTrade() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject discardCards() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject changeLogLevel() throws ServerProxyException {
		// TODO Auto-generated method stub
		return null;
	}

}
