package client.modelfacade;

import java.util.Map;

import org.json.simple.JSONObject;

import client.main.ClientPlayer;
import client.servercommunicator.ServerFacade;
import shared.logger.Log;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.VertexLocation;
import shared.model.hand.ResourceType;

public class DoModelFacade extends ModelFacade {

	private DoModelFacade() {
		super();
	}
	
	private static DoModelFacade singleton;
	
	public static DoModelFacade sole() {
		if (singleton == null)
			singleton = new DoModelFacade();
		return singleton;
	}

	
	/**
	 * Simulates two die, sends the result to the model and server and 
	 * compares the result models.
	 * @param number 
	 * @pre User is logged in, in a game, model state is 'rolling', it's
	 * the user's turn
	 * @post client state now becomes 'discarding', 'robbing' or 'playing'
	 * @pre condition violation
	 */
	public void doRollDice(int number) {
		try {
			if (!CanModelFacade.sole().canRollDice())
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().rollNumber(ClientPlayer.sole().getUserIndex(), number);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 * @param robberLocation
	 *            New location of robber
	 * @param victimIndex
	 *            PlayerID of robbed person
	 */
	public void doRobPlayer(HexLocation robberLocation, int victimIndex) {
		try {
			if (!CanModelFacade.sole().canPlaceRobber(robberLocation))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().robPlayer(ClientPlayer.sole().getUserIndex(), victimIndex,
					robberLocation);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doBuyDevCard() {
		try {
			if (!CanModelFacade.sole().canBuyDevCard())
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().buyDevCard(ClientPlayer.sole().getUserIndex());
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doUseYearOfPlenty(ResourceType resource1, ResourceType resource2) {
		try {
			if (!CanModelFacade.sole().canUseYearOfPlenty(resource1, resource2))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().yearOfPlenty(ClientPlayer.sole().getUserIndex(), resource1,
					resource2);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 * @param loc1
	 *            edge Location 1
	 * @param loc2
	 *            Edge Location 2
	 */
	public void doUseRoadBuilding(EdgeLocation loc1, EdgeLocation loc2) {
		try {
			if (!CanModelFacade.sole().canUseRoadBuilding(loc1, loc2))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().roadBuilding(ClientPlayer.sole().getUserIndex(), loc1, loc2);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doUseSoldier(HexLocation location, int victimIndex) {
		try {
			/*if (!CanModelFacade.sole().canUseSoldier(location, victimIndex))
				throw new IllegalStateException();*/
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().soldier(ClientPlayer.sole().getUserIndex(), victimIndex,
					location);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doUseMonopoly(ResourceType resource) {
		try {
			if (!CanModelFacade.sole().canUseMonopoly(resource))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().monopoly(resource, ClientPlayer.sole().getUserIndex());
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doUseMonument() {
		try {
			if (!CanModelFacade.sole().canUseMonument())
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().monument(ClientPlayer.sole().getUserIndex());
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doBuildRoad(EdgeLocation roadLocation) {
		try {
			if (!CanModelFacade.sole().canBuildRoad(roadLocation))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().buildRoad(ClientPlayer.sole().getUserIndex(), roadLocation,
					false);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public void doSetupRoad(EdgeLocation roadLocation) {
		try {
			if (!CanModelFacade.sole().canSetupRoad(roadLocation))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().buildRoad(ClientPlayer.sole().getUserIndex(), roadLocation,
					true);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 * @param vertLoc
	 *            Location of settlement
	 */
	public void doBuildSettlement(VertexLocation vertLoc) {
		try {
			if (!CanModelFacade.sole().canBuildSettlement(vertLoc))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().buildSettlement(ClientPlayer.sole().getUserIndex(), vertLoc,
					false);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public void doSetupSettlement(VertexLocation vertLoc) {
		try {
			if (!CanModelFacade.sole().canSetupSettlement(vertLoc))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().buildSettlement(ClientPlayer.sole().getUserIndex(), vertLoc,
					true);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doBuildCity(VertexLocation vertLoc) {
		try {
			if (!CanModelFacade.sole().canBuildCity(vertLoc))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().buildCity(ClientPlayer.sole().getUserIndex(), vertLoc);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 * @param receiverIndex
	 *            Index of the recipient of the cards
	 */
	public void doOfferTrade(Map<ResourceType, Integer> offer, Integer receiverIndex) {
		try {
			if (!CanModelFacade.sole().canOfferTrade(offer, receiverIndex))
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().offerTrade(ClientPlayer.sole().getUserIndex(), offer,
					receiverIndex);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doAcceptTrade(boolean willAccept) {
		try {
			/*if (!CanModelFacade.sole().canAcceptTrade())
				throw new IllegalStateException();*/
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().acceptTrade(ClientPlayer.sole().getUserIndex(), willAccept);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doMaritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource) {
		try {
			/*if (!CanModelFacade.sole().canMaritimeTrade(ratio, inputResource, outputResource))
				throw new IllegalStateException();*/
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().maritimeTrade(ClientPlayer.sole().getUserIndex(), ratio,
					inputResource, outputResource);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 * @param resources
	 */
	public void doDiscard(Map<ResourceType, Integer> resources) {
		try {
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().discard(ClientPlayer.sole().getUserIndex(), resources);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
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
	 */
	public void doEndTurn() {
		try {
			if (!CanModelFacade.sole().canEndTurn())
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().finishTurn(ClientPlayer.sole().getUserIndex());
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
		}
	}

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
	 */
	public void doSendChat(String message) {
		try {
			if (!CanModelFacade.sole().canSendChat())
				throw new IllegalStateException();
			JSONObject jsonModel = (JSONObject) ServerFacade.get_instance().sendChat(ClientPlayer.sole().getUserIndex(), message);
			setModel(jsonModel);
		} catch (Exception e) {
			Log.error(e);
		}
	}
	
}
