package shared.models;

import java.util.Map;

import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.piece.PieceType;
import shared.models.board.vertex.VertexLocation;
import shared.models.exceptions.DevCardException;
import shared.models.exceptions.DiscardException;
import shared.models.exceptions.MessageException;
import shared.models.exceptions.NotMoveException;
import shared.models.exceptions.PlayingException;
import shared.models.exceptions.TradeException;
import shared.models.hand.ResourceType;

/**
 * This class sits between the Game Model, the Controllers and Server Facade.
 * Its purpose is to simplify the interactions between all three.
 */
public class ModelFacade {
	
	private Inspector inspector;
	
	/**
	 * Instrcutor with no params
	 */
	public ModelFacade() {
	}
	
	/**
	 * Instrcutor
	 * @param inspector
	 */
	public ModelFacade(Inspector inspector) {
		this.inspector = inspector;
	}
	
	/**
	 * @return the inspector
	 */
	public Inspector getInspector() {
		return inspector;
	}

	/**
	 * @param inspector the inspector to set
	 */
	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}

	/**
	 * Initially it sends the message to the game model. If there were no 
	 * errors it then proceeds to send the message to the server facade to 
	 * update the server. If that was successful, it will then check the new
	 * received model to see if the message indeed was added and will make
	 * any additional changes that might have come with the model.
	 * @pre User is logged in and in a game
	 * @post The chat now contains the given message. If there was an error
	 * it tries again
	 * @param message The message to be sent
	 * @throws MessageException User is not a member of a game
	 */
	public void sendMessage(String message) 
			throws MessageException {
		
	}
	
	/**
	 * Checks with the model to see if user can buy a dev card. If so, it
	 * assigns him one. These changes are then made on the server and the 
	 * result is compared with the current model. 
	 * @pre User is logged in and in a game
	 * @post If the user is eligible to buy a dev card, he will receive one at
	 * random
	 * @return Whether or not the user received a card
	 * @throws DevCardException
	 */
	public boolean buyDevCard() throws DevCardException {
		return false;
	}
	
	/**
	 * Checks the model for pre conditions. Makes the changes in the model.
	 * Makes the changes in the server. Verifies result.
	 * @pre You are logged in and currently have a game. It is your turn, the
	 * client model status is 'playing', you have a monopoly card, you
	 * have not yet played a 
	 * @post You receive every other players resource card of the same type 
	 * @throws DevCardException
	 */
	public void playMonopolyCard(ResourceType resource) 
			throws DevCardException {
		
	}
	
	/**
	 * Tells the model to increment the level of victory of points. Updates
	 * the model on the server. Verifies returned model.
	 * @pre You are logged in, have joined a game, it is your turn, the client
	 * model status is 'playing', you have a monument card
	 * @post you gain a victory point
	 * @throws DevCardException
	 */
	public void playMonumentCard() throws DevCardException {
		
	}
	
	/**
	 * Checks model for validity. Performs operation. Sends update to server.
	 * Compares the received model to its current model.
	 * @pre You are logged in, you are in a game, it is your turn, the client
	 * model status is 'playing', you have not yet played a non-monument dev
	 * card this turn. The first road location is connected to one of your
	 * roads, the second road location is connected to one of your roads
	 * or the first road location (spot2). Neither road location is on water.
	 * You have at least two unused roads. Params are valid.
	 * @post you have two fewer unused roads. Two new roads appear on the map
	 * at the specified locations. If applicable, longest road award has been
	 * awarded
	 * @param spot1 first place to put a road
	 * @param spot2 second place to put a road
	 * @throws DevCardException Pre conditions not met
	 */
	public void playRoadBuildCard(EdgeLocation spot1, EdgeLocation spot2) 
			throws DevCardException {
		
	}
	
	/**
	 * Checks model for validity, performs the operation, updates the server,
	 * compares the result.
	 * @pre User is logged in, in a game, it is her turn, client model status
	 * is 'playing', she has not yet played non-monument dev card. Robber is
	 * not being kept in the same location, player being robbed (if exists)
	 * has resource cards.
	 * @post robber has new location, robbed player loses cards, current player
	 * gains them, largest army is awarded - if applicable, you are no longer
	 * able to play more development cards.
	 * @param location new location of the robber
	 * @param victimIndex player you robbing (-1 for no one)
	 * @throws DevCardException pre condition violation
	 */
	public void playSoldierCard(HexLocation location, int victimIndex)
			throws DevCardException {
		
	}
	
	/**
	 * @pre User is logged in, in a game, on his turn, client model is
	 * 'playing', he has not yet used a non-monument dev card, the two
	 * specified dev cards are in the bank.
	 * @post User gains two specified resources
	 * @param resource1 first desired resource
	 * @param resource2 second desired resource
	 * @throws DevCardException Violation of preconditions
	 */
	public void yearOfPlentyCard(ResourceType resource1, 
			ResourceType resource2)	throws DevCardException {
		
	}
	
	/**
	 * Takes away the listed cards from the user
	 * @pre User is logged in, has a game, client model is 'discarding',
	 * user has over 7 cards, user has the cards to discard
	 * @post user loses specified cards, client model changes to 'robbing' if
	 * they are the last to discard
	 * @param discardedCards
	 * @throws DiscardException Pre-conditions not met
	 */
	public void discard(ResourceType[] discardedCards) 
			throws DiscardException {
		
	}
	
	/**
	 * Checks the model to see if a user can increase a given resource.
	 * @pre user is logged in, in a game, slient model is 'discarding',
	 * user has over 7 cards
	 * @post increase button the gui will reflect limitations
	 * @param resource User's resource to increase
	 * @param amount Amount to increase it by 
	 * @return Whether or not the amount of a user's resource can decrease
	 * @throws DiscardException Pre-conditions not met
	 */
	public boolean increaseAmount(ResourceType resource, int amount) 
			throws DiscardException, TradeException {
		return false;
		
	}
	
	/**
	 * Changes the model depending on what was chosen then it tells the server.
	 * It then compares the model returned by the server to its own. 
	 * @pre user is logged in, in a game, been offered a domestic trade, and
	 * has the required resources
	 * @post If trade is accepted, resources are swapper, other wise there are
	 * no trades. Trade offer is removed.
	 * @param willAccept Whether or not the user accepts the trade
	 * @throws TradeException Pre condition not met
	 */
	public void acceptTrade(boolean willAccept) throws TradeException {
		
	}
	
	/**
	 * Sends a trade offer to the server to be processed
	 * @pre User is logged in, in a game, in their turn, the client is
	 * 'playing', user has the offered resources
	 * @post trade gets offered to the other player(on the server model)
	 * @param offer Resources and their amounts to be given (-numbers mean
	 * the user gets those cards)
	 * @param receiver ID of the recipient of the cards
	 * @throws TradeException Pre Condition failure
	 */
	public void sendTradeOffer(Map<ResourceType, Integer> offer, int receiver)
		throws TradeException {
		
	}
	
	/**
	 * Sends a user's input to the server to create a new game
	 * @pre name != null, and the boolean values are set
	 * @post A new game is created on the server
	 * @param name Name of the game
	 * @param randomTiles Set the tiles randomly?
	 * @param randomNumbers Set the numbers randomly?
	 * @param randomPorts Set the ports Randomly?
	 * @throws NotMoveException Precondition violated
	 */
	public void createNewGame(String name, boolean randomTiles, 
			boolean randomNumbers, boolean randomPorts) 
					throws NotMoveException {
		
	}
	
	/**
	 * Adds a player to the given game
	 * @pre ID is valid, color is unique
	 * @post Player is added to game
	 * @param gameID Game to be added to
	 * @param color Color to represent player
	 * @throws NotMoveException Pre condition violation
	 */
	public void joinGame(int gameID, PieceType color) 
			throws NotMoveException {
		
	}
	
	/**
	 * Gets a list from the server of all the games available to join
	 * @pre User is logged in
	 * @post displays a list of all the games
	 * @throws NotMoveException User not logged in
	 */
	public void setupJoinGame() throws NotMoveException {
		
	}
	
	/**
	 * Uses the given username and password to try and enter the server.
	 * @pre Parameter are not null
	 * @post User is logged in if credentials are valid. If not, rejected.
	 * @param username Username
	 * @param password Password
	 * @throws NotMoveException  Pre condition violated
	 */
	public void signIn(String username, String password) 
			throws NotMoveException {
		
	}
	
	/**
	 * Creates a new user name on the server, if the username is not already
	 * taken. Logs them simultaneously.
	 * @pre Params are not null. Username is not already taken.
	 * @post In the case of success, a new user is created on the server and
	 * logged in. In case of failure, the user is notified. 
	 * @param username Username
	 * @param password Password
	 * @throws NotMoveException Precondition violation
	 */
	public void register(String username, String password) 
			throws NotMoveException {
		
	}
	
	/**
	 * Asks the inspector if a city may be placed in the given vertex
	 * @pre vertLoc is valid, and game is in stater where user can build a city
	 * @post Map shows if a city may be placed there
	 * @param vertLoc
	 * @return True if user may place city there, false otherwise
	 * @throws NotMoveException Pre conditions violated
	 */
	public boolean canPlaceCity(VertexLocation vertLoc) 
			throws NotMoveException {
				return false;
		
	}
	
	/**
	 * Checks to see if a user may place a road at a given edge by calling the
	 * inspector.
	 * @pre edgeLoc is valid, game is in state for user to place road
	 * @post Map reflects valid result
	 * @param edgeLoc location of edge to place road
	 * @return Whether or not a user may place a road
	 * @throws NotMoveException Pre condition violated
	 */
	public boolean canPlaceRoad(EdgeLocation edgeLoc) throws NotMoveException {
		return false;
		
	}
	
	/**
	 * Asks the inspector if the robber may be placed on the given hex
	 * @pre hexLoc is valid, game is in state to move robber
	 * @post Robbers ability to move on the map is shown
	 * @param hexLoc Location of hex to place robber
	 * @return Whether or not a robber can move there
	 * @throws NotMoveException Pre-condition violation
	 */
	public boolean canPlaceRobber(HexLocation hexLoc) throws NotMoveException {
		return false;
		
	}
	
	/**
	 * Asks the inspector if a user may place a settlement there.
	 * @pre vertLoc is valid, game is in state where user can place settlement.
	 * @post Screen will reflect result
	 * @param vertLoc Location to put the user
	 * @return Whether or not a user may build a settlement there
	 * @throws NotMoveException Pre-Conditions violated
	 */
	public boolean canPlaceSettlement(VertexLocation vertLoc) 
			throws NotMoveException {
		return false;
		
	}
	
	/**
	 * Imports the model into the controller/view
	 * @pre none
	 * @post Map view now refelcts everything in the model
	 * @retrun Model parts
	 */
	public void initFromModel() {
		
	}
	
	/**
	 * Tells the model where the user wants to place the city. Then it tells
	 * the server and compares the returned model to its own.
	 * @pre User is logged in, in a game, in their turn, the client model is
	 * 'playing', the city is a valid location and the user has the necessary
	 * resources.
	 * @post User loses the necessary resources, city is placed on the map
	 * and the user is returned a settlement.
	 * @param vertLoc Location to build city
	 * @throws PlayingException invalid pre condition
	 */
	public void placeCity(VertexLocation vertLoc) throws PlayingException {
		
	}
	
	/**
	 * Places the road on the map in the model and then on the server. It
	 * then compares the model returned by the server with its own.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', the road location is valid, open, connected to another road
	 * owned by the player, the location is not on water, the required 
	 * resources are had, and it has no adjacent road in the setup round
	 * @param setupMode Is this happening in setup mode?
	 * @param roadLocation Location of Road
	 * @throws PlayingException Invalid preconditions
	 */
	public void placeRoad(boolean setupMode, EdgeLocation roadLocation) 
			throws PlayingException {
		
	}
	
	/**
	 * Moves the robber to the location on the map in the model and then the
	 * server. The model returned by the server is then compared to its own.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', all params are valid, the robber is not kept in the same
	 * location
	 * @post robber is placed in the new location, player being robbed gives
	 * away one of his resource cards randomly.
	 * @param location New location of robber
	 * @param victimIndex PlayerID of robbed person
	 * @throws PlayingException Pre-conditions violated
	 */
	public void placeRobber(HexLocation location, int victimIndex) 
			throws PlayingException{
		
	}
	
	/**
	 * Tells the model then the server where a user is placing a settlement.
	 * The two models afterwards are compared.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', params are valid, settlement location is open, not on water,
	 * not adjacent to another settlement, connected to one of the users roads
	 * during setup, user has required resources.
	 * @post User loses resource to build, settlement is place on map 
	 * @param setupMode Is this setup?
	 * @param vertLoc Location of settlement
	 * @throws PlayingException Pre condition violation
	 */
	public void placeSettlement(boolean setupMode, VertexLocation vertLoc)
			throws PlayingException {
		
	}
	
	/**
	 * Checks the model to see if a user can trade that much of one resource
	 * @pre User is logged in, in a game, in his turn
	 * @post nothing
	 * @param resource Resource to trade
	 * @param amount Amount of resource
	 * @return Whether or not the user can trade
	 * @throws TradeException Pre condtion violation
	 */
	public boolean canMakeTrade(ResourceType resource, int amount)
		throws PlayingException {
			return false;
		
	}
	
	/**
	 * Tells the model then the server about the trade. Compares the two
	 * resulting models.
	 * @pre User is logged in, in a game, its his turn, the client is 
	 * 'playing', params are valid, user has resources, resources less than
	 * four are located at the correct port
	 * @post Bank and user trade resources
	 * @param ratio 2,3 or 4
	 * @param inputResource What user gives
	 * @param outputResource What user gets
	 * @throws PlayingException Pre-condition violation
	 */
	public void makeTrade(int ratio, ResourceType inputResource,
			ResourceType outputResource) throws PlayingException {
		
	}
	
	/**
	 * adds an AI to the given game on the server
	 * @pre user is logged in and has joined a game. There is space for another
	 * player, and the ai is LARGEST_ARMY
	 * @param aiType LARGEST_ARMY
	 * @throws NotMoveException precondtion violation
	 */
	public void addAI(String aiType) throws NotMoveException {
		
	}
	
	/**
	 * Simulates two die, sends the result to the model and server and compares
	 * the result models.
	 * @pre User is logged in, in a game, model state is 'rolling', it's
	 * the user's turn
	 * @post client state now becomes 'discarding', 'robbing' or 'playing'
	 * @throws PlayingException pre condition violation
	 */
	public void rollDice() throws PlayingException {
		
	}
	
	/**
	 * tells the model that the current state for the player is over and then
	 * the server. The resulting model is then compared.
	 * @pre User is logged in, in a game, it his turn, and the client is
	 * 'playing'
	 * @post cards in new dev hand are transfered to old dev card hand and the
	 * next player has his turn.
	 * @throws PlayingException Pre-condition violation
	 */
	public void endTurn()  throws PlayingException {
		
	}
}