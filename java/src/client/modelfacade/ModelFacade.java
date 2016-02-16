
package client.modelfacade;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import client.serverfacade.ServerException;
import client.serverfacade.ServerFacade;
import shared.model.GameModel;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.DevCardException;
import shared.model.exceptions.JoinGameException;
import shared.model.exceptions.MessageException;
import shared.model.exceptions.PlayingException;
import shared.model.exceptions.PreGameException;
import shared.model.exceptions.SignInException;
import shared.model.hand.ResourceType;

/**
 * This class sits between the Game Model, the Controllers and Server Facade.
 * Its purpose is to simplify the interactions between all three.
 */
public class ModelFacade {

	private GameModel gameModel;
	private Integer userID;

	/**
	 * Instrcutor with GameModel input
	 * 
	 * @param playerID
	 * @throws BadJSONException
	 */
	public ModelFacade(JSONObject jsonModel, int playerID) throws BadJSONException {
		this.gameModel = new GameModel(jsonModel);
		this.userID = playerID;
	}
	
	public void setModel(JSONObject jsonModel) throws BadJSONException {
		gameModel = new GameModel(jsonModel);
	}

	public ModelFacade() {
		this.gameModel = null;
	}

	
	public Boolean hasModel() {
		if (gameModel == null)
			return false;
		return true;
	}

	public boolean equalsJSON(JSONObject jsonMap) {
		return this.gameModel.equalsJSON(jsonMap);
	}
	
	private Integer getClientIndex() {
		return gameModel.getIndexFromPlayerID(userID);
	}

	// Controller to Server Interactions

	// user
	/**
	 * Uses the given username and password to try and enter the server.
	 * 
	 * @pre Parameter are not null
	 * @post User is logged in if credentials are valid. If not, rejected.
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @throws SignInException
	 *             Pre condition violated
	 */
	public void signIn(String username, String password) throws SignInException {
		try {
			this.userID = ServerFacade.get_instance().login(username, password);
		} catch (ServerException e) {
			throw new SignInException(e);
		}
	}

	/**
	 * Creates a new user name on the server, if the usernamee is not already
	 * taken. Logs them in simultaneously.
	 * 
	 * @pre Params are not null. Username is not already taken.
	 * @post In the case of success, a new user is created on the server and
	 *       logged in. In case of failure, the user is notified.
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @throws SignInException
	 *             Precondition violation
	 */
	public void register(String username, String password) throws SignInException {
		try {
			this.userID = ServerFacade.get_instance().register(username, password);
		} catch (ServerException e) {
			throw new SignInException(e);
		}
	}

	// games (pre-joining)
	/**
	 * Gets a list from the server of all the games available to join
	 * 
	 * @pre User is logged in
	 * @post displays a list of all the games
	 * @throws PreGameException
	 *             User not logged in
	 */
	public void setupJoinGame() throws PreGameException {
		try {
			ServerFacade.get_instance().getGames();
		} catch (ServerException e) {
			throw new PreGameException(e);
		}
	}

	/**
	 * Sends a user's input to the server to create a new game
	 * 
	 * @pre name != null, and the boolean values are set
	 * @post A new game is created on the server
	 * @param name
	 *            Name of the game
	 * @param randomTiles
	 *            Set the tiles randomly?
	 * @param randomNumbers
	 *            Set the numbers randomly?
	 * @param randomPorts
	 *            Set the ports Randomly?
	 * @throws PreGameException
	 *             Precondition violated
	 */
	public JSONObject createNewGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
			throws PreGameException {
		try {
			JSONObject newGame = (JSONObject) ServerFacade.get_instance().createNewGame(randomTiles, randomNumbers,
					randomPorts, name);
			return newGame;
		} catch (ServerException e) {
			throw new PreGameException(e);
		}
	}

	/**
	 * Adds a player to the given game on the server. Receives a gameModel in
	 * return
	 * 
	 * @pre ID is valid, color is unique
	 * @post Player is added to game
	 * @param gameID
	 *            Game to be added to
	 * @param color
	 *            Color to represent player
	 * @throws PreGameException
	 *             Pre condition violation
	 */
	public void joinGame(int gameID, CatanColor color) throws PreGameException {
		try {
			ServerFacade.get_instance().joinGame(gameID, color);
		} catch (ServerException e) {
			throw new PreGameException(e);
		}
	}

	// games (game you're in)
	/**
	 * adds an AI to the given game on the server
	 * 
	 * @pre user is logged in and has joined a game. There is space for another
	 *      player, and the ai is LARGEST_ARMY
	 * @param aiType
	 *            LARGEST_ARMY
	 * @throws JoinGameException
	 *             precondition violation
	 */
	public void addAI(String aiType) throws JoinGameException {
		try {
			ServerFacade.get_instance().addAI(aiType);
		} catch (ServerException e) {
			throw new JoinGameException(e);
		}
	}

	/**
	 * Checks with the server for the different AI types available
	 * 
	 * @pre user is logged in and has joined a game. This is space for another
	 *      player
	 * @post a map of AI players is returned
	 * @return List of AI players
	 * @throws JoinGameException
	 */
	public JSONObject getAI() throws JoinGameException {
		try {
			return (JSONObject) ServerFacade.get_instance().listAI();
		} catch (ServerException e) {
			throw new JoinGameException(e);
		}
	}

	// moves
	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in and in a game
	 * @post The chat now contains the given message. If there was an error it
	 *       tries again
	 * @param message
	 *            The message to be sent
	 * @throws MessageException
	 *             User is not a member of a game
	 */
	public void sendMessage(String message) throws MessageException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().sendChat(userID, message);
			gameModel = new GameModel(modelFromServer);
		} catch (ServerException e) {
			throw new MessageException(e);

		} catch (BadJSONException e) {
			throw new MessageException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, its his turn, the client is 'playing',
	 *      all params are valid, the robber is not kept in the same location
	 * @post robber is placed in the new location, player being robbed gives
	 *       away one of his resource cards randomly.
	 * @param location
	 *            New location of robber
	 * @param victimIndex
	 *            PlayerID of robbed person
	 * @throws PlayingException
	 *             Pre-conditions violated
	 */
	public void placeRobber(HexLocation location, int victimIndex) throws PlayingException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().robPlayer(userID, victimIndex,
					location);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, it his turn, and the client is
	 *      'playing'
	 * @post cards in new dev hand are transfered to old dev card hand and the
	 *       next player has his turn.
	 * @throws PlayingException
	 *             Pre-condition violation
	 */
	public void endTurn() throws PlayingException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().finishTurn(userID);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in and in a game, it's his turn, the client model's
	 *      state is 'playing', he has the resources and there are devcards left
	 * @post If the user is eligible to buy a dev card, he will receive one at
	 *       random
	 * @return Whether or not the user received a card
	 * @throws PlayingException
	 */
	public void buyDevCard() throws PlayingException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().buyDevCard(userID);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, on his turn, client model is
	 *      'playing', he has not yet used a non-monument dev card, the two
	 *      specified dev cards are in the bank.
	 * @post User gains two specified resources
	 * @param resource1
	 *            first desired resource
	 * @param resource2
	 *            second desired resource
	 * @throws DevCardException
	 *             Violation of preconditions
	 */
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws DevCardException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().yearOfPlenty(userID, resource1,
					resource2);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new DevCardException(e);
		} catch (ServerException e) {
			throw new DevCardException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre It is the users turn, the client model status is playing, The user
	 *      has this card, the user has not played any other dev card, the first
	 *      and second location are connected to one of the user's roads,
	 *      neither road location is on water, the user has at least two unused
	 *      roads.
	 * @post The user has two fewer roads, two new roads appear on the map at
	 *       the given location and, if applicable, the user receives the
	 *       longest road award
	 * @param spot1
	 *            edge Location 1
	 * @param spot2
	 *            Edge Location 2
	 * @throws DevCardException
	 *             this card cannot be played
	 */
	public void roadBuilding(EdgeLocation spot1, EdgeLocation spot2) throws DevCardException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().roadBuilding(userID, spot1, spot2);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new DevCardException(e);
		} catch (ServerException e) {
			throw new DevCardException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, it is her turn, client model status is
	 *      'playing', she has not yet played non-monument dev card. Robber is
	 *      not being kept in the same location, player being robbed (if exists)
	 *      has resource cards.
	 * @post robber has new location, robbed player loses cards, current player
	 *       gains them, largest army is awarded - if applicable, you are no
	 *       longer able to play more development cards.
	 * @param location
	 *            new location of the robber
	 * @param victimIndex
	 *            player you robbing (-1 for no one)
	 * @throws DevCardException
	 *             pre condition violation
	 */
	public void playSoldierCard(HexLocation location, int victimIndex) throws DevCardException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().soldier(userID, victimIndex,
					location);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new DevCardException(e);
		} catch (ServerException e) {
			throw new DevCardException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre You are logged in and currently have a game. It is your turn, the
	 *      client model status is 'playing', you have a monopoly card, you have
	 *      not yet played a
	 * @post You receive every other players resource card of the same type
	 * @throws DevCardException
	 */
	public void playMonopolyCard(ResourceType resource) throws DevCardException {
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().monopoly(resource, userID);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new DevCardException(e);
		} catch (ServerException e) {
			throw new DevCardException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre You are logged in, have joined a game, it is your turn, the client
	 *      model status is 'playing', you have a monument card
	 * @post you gain a victory point
	 * @throws DevCardException
	 */
	public void playMonumentCard() throws DevCardException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().monument(getClientIndex());
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new DevCardException(e);
		} catch (ServerException e) {
			throw new DevCardException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre It is the users turn, the client model status is playing, the road
	 *      location is open, connected to another road owned by the player, not
	 *      on water, the user has the necessary resources and, if in setup
	 *      round, the road must be placed by settlement owned by the player
	 *      with no adjacent road.
	 * @post The user loses the resources necessary to build the road, the road
	 *       is on the map at the given spot, and, if applicable, the user
	 *       receives the longestRoad award
	 * @param roadLocation
	 *            Where to place the road
	 * @throws PlayingException
	 *             Play could not be made
	 */
	public void buildRoad(EdgeLocation roadLocation) throws PlayingException {
		boolean free = this.gameModel.inSetupRounds();

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().buildRoad(getClientIndex(), roadLocation,
					free);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, its his turn, the client is 'playing',
	 *      params are valid, settlement location is open, not on water, not
	 *      adjacent to another settlement, connected to one of the users roads
	 *      during setup, user has required resources.
	 * @post User loses resource to build, settlement is place on map
	 * @param setupMode
	 *            Is this setup?
	 * @param vertLoc
	 *            Location of settlement
	 * @throws PlayingException
	 *             Pre condition violation
	 */
	public void placeSettlement(boolean setupMode, VertexLocation vertLoc) throws PlayingException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().buildSettlement(getClientIndex(), vertLoc,
					setupMode);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, in their turn, the client model is
	 *      'playing', the city is a valid location and the user has the
	 *      necessary resources.
	 * @post User loses the necessary resources, city is placed on the map and
	 *       the user is returned a settlement.
	 * @param vertLoc
	 *            Location to build city
	 * @throws PlayingException
	 *             invalid pre condition
	 */
	public void buildCity(VertexLocation vertLoc) throws PlayingException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().buildCity(getClientIndex(), vertLoc);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Sends a trade offer to the server to be processed
	 * 
	 * @pre User is logged in, in a game, in their turn, the client is
	 *      'playing', user has the offered resources
	 * @post trade gets offered to the other player(on the server model)
	 * @param offer
	 *            Resources and their amounts to be given (-numbers mean the
	 *            user gets those cards)
	 * @param receiver
	 *            ID of the recipient of the cards
	 * @throws PlayingException
	 *             Pre Condition failure
	 */
	public void sendTradeOffer(Map<ResourceType, Integer> offer, int receiver) throws PlayingException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().offerTrade(getClientIndex(), offer,
					receiver);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre user is logged in, in a game, been offered a domestic trade, and has
	 *      the required resources
	 * @post If trade is accepted, resources are swapper, other wise there are
	 *       no trades. Trade offer is removed.
	 * @param willAccept
	 *            Whether or not the user accepts the trade
	 * @throws PlayingException
	 *             Pre condition not met
	 */
	public void acceptTrade(boolean willAccept) throws PlayingException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().acceptTrade(getClientIndex(), willAccept);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}

	/**
	 * Initially it sends the message to the server facade to update the server.
	 * If that was successful, it will then update the model to match the newly
	 * updated version handed from the server.
	 * 
	 * @pre User is logged in, in a game, its his turn, the client is 'playing',
	 *      params are valid, user has resources, resources less than four are
	 *      located at the correct port
	 * @post Bank and user trade resources
	 * @param ratio
	 *            2,3 or 4
	 * @param inputResource
	 *            What user gives
	 * @param outputResource
	 *            What user gets
	 * @throws PlayingException
	 *             Pre-condition violation
	 */
	public void makeMaritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource)
			throws PlayingException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().maritimeTrade(getClientIndex(), ratio,
					inputResource, outputResource);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}
	
	/**
	 * Initially it sends the message to the server facade to 
	 * update the server. If that was successful, it will then update the
	 * model to match the newly updated version handed from the server.
	 * @pre User is logged in, has a game, client model is 'discarding',
	 * user has over 7 cards, user has the cards to discard
	 * @post user loses specified cards, client model changes to 'robbing' if
	 * they are the last to discard
	 * @param discardedCards
	 * @throws PlayingException Pre-conditions not met
	 */
	public void discard(List<ResourceType> discardedCards) throws PlayingException {

		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().discard(getClientIndex(), discardedCards);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}
	
	//Controller to Game Model and Server Interactions
	/**
	 * Simulates two die, sends the result to the model and server and 
	 * compares the result models.
	 * @pre User is logged in, in a game, model state is 'rolling', it's
	 * the user's turn
	 * @post client state now becomes 'discarding', 'robbing' or 'playing'
	 * @throws PlayingException pre condition violation
	 */
	public void rollDice() throws PlayingException {
		int number = this.gameModel.getDiceNumber();
		try {
			JSONObject modelFromServer = (JSONObject) ServerFacade.get_instance().rollNumber(getClientIndex(), number);
			gameModel = new GameModel(modelFromServer);
		} catch (BadJSONException e) {
			throw new PlayingException(e);
		} catch (ServerException e) {
			throw new PlayingException(e);
		}
	}
	
	//Controller to Game Model Interactions
	
	public boolean canSendChat() throws NullPointerException {
		return this.gameModel.canSendChat(userID);
	}

	/**
	 * Checks the model to see if the current player can roll die
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canRollNumber() throws NullPointerException {
		return this.gameModel.canRollNumber(userID);
	}

	/**
	 * Asks the inspector if the robber may be placed on the given hex
	 * @pre hexLoc is valid, game is in state to move robber
	 * @post Robbers ability to move on the map is shown
	 * @param hexLoc Location of hex to place robber
	 * @param playerIndex 
	 * @return Whether or not a robber can move there
	 * @throws NullPointerException Pre-condition violation
	 */
	public boolean canPlaceRobber(HexLocation hexLoc, int playerIndex) throws NullPointerException {
		return this.gameModel.canRobPlayer(userID, hexLoc, playerIndex);
	}

	/**
	 * Checks the model to see if the current player can finish his turn
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canFinishTurn() throws NullPointerException {
		return this.gameModel.canFinishTurn(userID);
	}

	/**
	 * Checks the model to see if the current player can buy dev card
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canBuyDevCard() throws NullPointerException {
		return this.gameModel.canBuyDevCard(userID);
	}

	/**
	 * Checks the model to see if the current player can use year of plenty
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param one first resource
	 * @param two second resource
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseYearOfPlenty(ResourceType one, ResourceType two) throws NullPointerException {
		return this.gameModel.canUseYearOfPlenty(userID, one, two);
	}

	/**
	 * Checks the model to see if the current player can DiscardCards
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param one Edge to build on
	 * @param two Edge to build on
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseRoadBuilder(EdgeLocation one, EdgeLocation two) throws NullPointerException {
		return this.gameModel.canUseRoadBuilding(userID, one, two);
	}

	/**
	 * Checks the model to see if the current player can use soldier
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param newRobberLocation Change Robber Hex
	 * @param playerIndex Player to Rob
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseSoldier(HexLocation newRobberLocation, int playerIndex) throws NullPointerException {
		return this.gameModel.canUseSoldier(userID, newRobberLocation, playerIndex);
	}

	/**
	 * Checks the model to see if the current player can use monopoly
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param type Resource to Monopolize
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseMonopoly(ResourceType type) throws NullPointerException {
		return this.gameModel.canUseMonopoly(userID, type);
	}

	/**
	 * Checks the model to see if the current player can use monument
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canUseMonument() throws NullPointerException {
		return this.gameModel.canUseMonument(userID);
	}

	/**
	 * Checks to see if a user may place a road at a given edge
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @param edgeLoc location of edge to place road
	 * @return Whether or not a user may place a road
	 * @throws NullPointerException Pre condition violated
	 */
	public boolean canBuildRoad(EdgeLocation edgeLoc) throws NullPointerException {
		return this.gameModel.canBuildRoad(userID, edgeLoc);
	}

	/**
	 * Asks the inspector if a user may place a settlement there.
	 * @pre vertLoc is valid, game is in state where user can place settlement
	 * @post Screen will reflect result
	 * @param vertLoc Location to put the user
	 * @return Whether or not a user may build a settlement there
	 * @throws NullPointerException Pre-Conditions violated
	 */
	public boolean canBuildSettlement(VertexLocation vertLoc) throws NullPointerException {
		return this.gameModel.canBuildSettlement(userID, vertLoc);
	}

	/**
	 * Asks the inspector if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a 
	 * city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc City Location
	 * @return True if user may place city there, false otherwise
	 * @throws NullPointerException Pre conditions violated
	 */
	public boolean canBuildCity(VertexLocation vertLoc) throws NullPointerException {
		return this.gameModel.canBuildCity(userID, vertLoc);
	}
	
	/**
	 * Checks the model through the inspector to see if a user can trade
	 * @pre User is logged in, in a game, in his turn
	 * @post nothing
	 * @param resource Resource to trade
	 * @param amount Amount of resource
	 * @return Whether or not the user can trade
	 * @throws NullPointerException Pre condtion violation
	 */
	public boolean canOfferTrade(Map<String, Object> resource) throws NullPointerException {
		return gameModel.canOfferTrade(userID, resource);
	}
	
	/**
	 * Checks the model to see if the current player can accept trade
	 * @pre none
	 * @post whether or not this operation is valid
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canAcceptTrade() throws NullPointerException {
		return gameModel.canAcceptTrade(userID);
	}

	/**
	 * Checks the model to see if the current player can maritime trade
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param ratio Port
	 * @param input Resource
	 * @param output Resource
	 * @return post
	 * @throws NullPointerException
	 */
	public boolean canMaritimeTrade(int ratio, ResourceType inputType, ResourceType outputType)
			throws NullPointerException {
		return this.gameModel.canMaritimeTrade(userID, ratio, inputType, outputType);
	}

	/**
	 * Checks the model to see if the current player can DiscardCards
	 * @pre none
	 * @post whether or not this operation is valid
	 * @param resourceList resources they have
	 * @return whether or not this operation is valid
	 * @throws NullPointerException
	 * @throws BadResourceTypeException
	 */
	public boolean canDiscardCards(Map<String, Object> resourceList) throws NullPointerException {
		return this.gameModel.canDiscardCard(userID, resourceList);
	}
	
	//Model to Controller Interactions
	/**
	 * Imports the model into the controller/view
	 * @pre none
	 * @post Map view now reflects everything in the model
	 */
	public void initFromModel() {
		
	}
}