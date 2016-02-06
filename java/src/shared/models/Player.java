package shared.models;

import java.util.Map;

import org.json.simple.JSONObject;

import shared.models.board.edge.Edge;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.tiles.water.PortType;
import shared.models.board.piece.City;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.piece.Settlement;
import shared.models.board.vertex.Vertex;
import shared.models.definitions.CatanColor;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.BuildException;
import shared.models.exceptions.NoDevCardFoundException;
import shared.models.hand.Hand;
import shared.models.hand.ResourceType;
import shared.models.hand.development.DevCard;
import shared.models.hand.development.DevCardType;
import shared.models.hand.exceptions.BadResourceTypeException;
import shared.models.hand.exceptions.ResourceException;

public class Player {

	private GameModel game;
	private Hand hand;
	private Settlement[] settlements;
	private Road[] roads;
	private City[] cities;
	private String userName;
	private Integer userIndex;
	private CatanColor userColor;
	private Integer armies;
	private Integer monuments;
	private Integer points;
	private Boolean playedDevelopmentCard;
	private Boolean hasDiscarded;
	private Integer playerID;

	public Player(JSONObject player) throws BadJSONException {
		if (player == null) throw new BadJSONException();
		String c = (String) player.get("color");
		if (c == null) throw new BadJSONException();
		userColor = getColor(c);
		userName = (String) player.get("name");
		if (userName == null) throw new BadJSONException();
		userIndex = (Integer) player.get("playerIndex");
		if (userIndex == null) throw new BadJSONException();		
		armies = (Integer) player.get("soldiers");
		if (armies == null) throw new BadJSONException();		
		monuments = (Integer) player.get("monuments");
		if (monuments == null) throw new BadJSONException();		
		playedDevelopmentCard = (Boolean) player.get("playedDevCard");
		if (playedDevelopmentCard == null) throw new BadJSONException();		
		hand = new Hand((JSONObject) player.get("resources"), (JSONObject) player.get("oldDevCards"),
				(JSONObject) player.get("newDevCards"));
		hasDiscarded = (Boolean) player.get("discarded");
		if (hasDiscarded == null) throw new BadJSONException();		
		playerID = (Integer) player.get("playerID");
		if (playerID == null) throw new BadJSONException();		
		settlements = new Settlement[5];
		cities = new City[4];
		roads = new Road[15];
	}

	public CatanColor getColor(String s) {
		s = s.toUpperCase();
		switch (s) {
		case "RED":
			return CatanColor.RED;
		case "ORANGE":
			return CatanColor.ORANGE;
		case "YELLOW":
			return CatanColor.YELLOW;
		case "BLUE":
			return CatanColor.BLUE;
		case "GREEN":
			return CatanColor.GREEN;
		case "PURPLE":
			return CatanColor.PURPLE;
		case "PUCE":
			return CatanColor.PUCE;
		case "WHITE":
			return CatanColor.WHITE;
		case "BROWN":
			return CatanColor.BROWN;
		default:
			return null;
		}
	}

	/*
	 * public Player(String userName, int userIndex, GameModel game) { this.game
	 * = game; try { for (int i = 0; i < 5; i++) settlements[i] = new
	 * Settlement(this); for (int i = 0; i < 4; i++) cities[i] = new City(this);
	 * for (int i = 0; i < 15; i++) roads[i] = new Road(this); } catch
	 * (NullPlayerException e) { //TODO log
	 * "no player passed to building or Road Constructors at" print stack trace
	 * } setUserName(userName); }
	 */

	/**
	 * @param type
	 *            the type of resource to add
	 * @param num
	 *            the number of resource to be added
	 * @throws ResourceException
	 * @post player now has addition resources of type and num
	 */
	public void receiveResource(ResourceType type, Integer num) throws ResourceException {
		hand.receiveResource(type, num);
	}

	/**
	 * @param type
	 *            the type of resource to take
	 * @param num
	 *            the number of resource to be taken
	 * @throws ResourceException
	 * @post player now has less resources of type and num
	 */
	public void sendResource(ResourceType type, Integer num) throws ResourceException {
		hand.sendResource(type, num);
	}

	public void discardCard(ResourceType type, Integer num) throws ResourceException {
		hand.sendResource(type, num);
		game.getBank().receiveResource(type, num);
	}

	/**
	 * @return true if player has a City piece available
	 */
	public Boolean hasCityPiece() {

		for (City city : cities) {
			if (!city.isPlaced())
				return true;
		}
		return false;
	}

	/**
	 * @return true if player has a Settlement piece available
	 */
	public Boolean hasSettlementPiece() {
		for (Settlement settlement : settlements) {
			if (!settlement.isPlaced())
				return true;
		}
		return false;
	}

	/**
	 * @return true if player has a Road piece available
	 */
	public Boolean hasRoadPiece() {
		for (Road road : roads) {
			if (!road.isPlaced())
				return true;
		}
		return false;
	}

	/**
	 * @throws ResourceException
	 * @pre hasSettlementCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 * @post wheat = wheat - 1
	 * @post sheep = sheep - 1
	 * 
	 */
	public void buySettlement() throws ResourceException {

		game.getBank().receiveResource(ResourceType.WOOD, 1);
		game.getBank().receiveResource(ResourceType.BRICK, 1);
		game.getBank().receiveResource(ResourceType.WHEAT, 1);
		game.getBank().receiveResource(ResourceType.SHEEP, 1);
		sendResource(ResourceType.WOOD, 1);
		sendResource(ResourceType.BRICK, 1);
		sendResource(ResourceType.WHEAT, 1);
		sendResource(ResourceType.SHEEP, 1);
	}

	/**
	 * 
	 * @throws ResourceException
	 * @pre hasCityCost
	 * @post wheat = wheat - 2
	 * @post ore = ore - 3
	 */
	public void buyCity() throws ResourceException {

		game.getBank().receiveResource(ResourceType.ORE, 3);
		game.getBank().receiveResource(ResourceType.WHEAT, 2);
		sendResource(ResourceType.ORE, 3);
		sendResource(ResourceType.WHEAT, 2);
	}

	/**
	 * @throws ResourceException
	 * @pre hasRoadCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 */
	public void buyRoad() throws ResourceException {
		game.getBank().receiveResource(ResourceType.WOOD, 1);
		game.getBank().receiveResource(ResourceType.BRICK, 1);
		sendResource(ResourceType.WOOD, 1);
		sendResource(ResourceType.BRICK, 1);
	}

	/**
	 * @throws ResourceException
	 * @pre hasDevelopmentCost
	 * @post wheat = wheat - 1
	 * @post sheep = sheep - 1
	 * @post ore = ore - 1
	 * 
	 */
	public void buyDevelopment() throws ResourceException {
		game.getBank().receiveResource(ResourceType.WHEAT, 1);
		game.getBank().receiveResource(ResourceType.SHEEP, 1);
		game.getBank().receiveResource(ResourceType.ORE, 1);
		sendResource(ResourceType.WHEAT, 1);
		sendResource(ResourceType.SHEEP, 1);
		sendResource(ResourceType.ORE, 1);
	}

	/**
	 * @param given
	 *            The type of card being given by the player
	 * @param received
	 *            The type of card being received by the player
	 * @throws ResourceException
	 * @post type given = type - 4
	 * @post type received = type + 1
	 * 
	 */
	public void buyResource(ResourceType given, ResourceType received) throws ResourceException {
		game.getBank().receiveResource(given, 4);
		sendResource(given, 4);
		game.getBank().sendResource(received, 1);
		receiveResource(received, 1);
	}

	/**
	 * @param given
	 *            The type of card being given by the player
	 * @param received
	 *            The type of card being received by the player
	 * @throws ResourceException
	 * @post type given = type - 3
	 * @post type received = type + 1
	 */
	public void buyResourceWith3Port(ResourceType given, ResourceType received) throws ResourceException {

		game.getBank().receiveResource(given, 3);
		sendResource(given, 3);
		game.getBank().sendResource(received, 1);
		receiveResource(received, 1);
	}

	/**
	 * @param given
	 *            The type of card being given by the player
	 * @param received
	 *            The type of card being received by the player
	 * @throws ResourceException
	 * @post type given = type - 2
	 * @post type received = type + 1
	 */
	public void buyResourceWith2Port(ResourceType given, ResourceType received) throws ResourceException {
		game.getBank().receiveResource(given, 2);
		sendResource(given, 2);
		game.getBank().sendResource(received, 1);
		receiveResource(received, 1);
	}

	public Boolean canPlayDevelopmentCard() {
		return playedDevelopmentCard;
	}

	public Boolean hasDiscarded() {
		return hasDiscarded;
	}

	/**
	 * @return true if player has a YearOfPlenty card available and enabled
	 */
	public Boolean hasYearOfPlenty() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.YEAROFPLENTY)
				return true;
		}
		return false;
	}

	/**
	 * @return true if player has a YearOfPlenty card available and enabled
	 */
	public Boolean hasYearOfPlentyToUse() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.YEAROFPLENTY && card.isEnabled())
				return true;
		}
		return false;
	}

	/**
	 * @pre hasYearOfPlenty
	 * @param type1
	 *            - the first resource the player selects
	 * @param type2
	 *            - the second resource the player selects
	 * @throws NoDevCardFoundException
	 * @throws ResourceException
	 * @post type1++ type2++ for player, type1-- type2-- for bank
	 */
	public void playYearOfPlenty(ResourceType type1, ResourceType type2)
			throws NoDevCardFoundException, ResourceException {
		playedDevelopmentCard = true;
		this.game.getBank().sendResource(type1, 1);
		this.game.getBank().sendResource(type2, 1);
		this.receiveResource(type1, 1);
		this.receiveResource(type2, 1);
	}

	/**
	 * @return true if player has a RoadBuilder card available and enabled
	 */
	public Boolean hasRoadBuilding() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.ROADBUILDING)
				return true;
		}
		return false;
	}

	/**
	 * @return true if player has a RoadBuilder card available and enabled
	 */
	public Boolean hasRoadBuildingToUse() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.ROADBUILDING && card.isEnabled())
				return true;
		}
		return false;
	}

	/**
	 * @pre hasRoadBuilder
	 * @throws NoDevCardFoundException
	 * @post buildRoad() has been called twice
	 */
	public void playRoadBuilding() throws NoDevCardFoundException {
		playedDevelopmentCard = true;
	}

	/**
	 * @return true if player has a Monopoly card available and enabled
	 */
	public Boolean hasMonopoly() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.MONOPOLY)
				return true;
		}
		return false;
	}

	/**
	 * @return true if player has a Monopoly card available and enabled
	 */
	public Boolean hasMonopolyToUse() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.MONOPOLY && card.isEnabled())
				return true;
		}
		return false;
	}

	/**
	 * @pre hasMonopoly()
	 * @param type
	 *            The type of resource the player will monopolize
	 * @throws NoDevCardFoundException
	 * @throws ResourceException
	 * @post the player increases in the quantity of type that the others
	 *       players combined all have and each other player's type = 0
	 */
	public void playMonopoly(ResourceType type) throws NoDevCardFoundException, ResourceException {
		playedDevelopmentCard = true;
		for (Player p : this.game.getPlayers()) {
			if (p.hand.hasResource(type, 1)) {
				int n = p.hand.getResourceAmount(type);
				p.sendResource(type, n);
				this.receiveResource(type, n);
			}
		}
	}

	/**
	 * @return true if player has a Knight card available and enabled
	 */
	public Boolean hasKnight() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.KNIGHT)
				return true;
		}
		return false;
	}

	/**
	 * @return true if player has a Knight card available and enabled
	 */
	public Boolean hasKnightToUse() {
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.KNIGHT && card.isEnabled())
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @throws NoDevCardFoundException
	 * @post armies++
	 */
	public void playKnight() throws NoDevCardFoundException {
		playedDevelopmentCard = true;
	}

	/**
	 * @pre p has > 0 resources
	 * @param p
	 *            The player to be stolen from
	 * @throws ResourceException
	 * @post Player type++; p type--
	 */
	public void steal(Player p) {
		try {
			this.receiveResource(p.drawRandomResourceCard(), 1);
		} catch (ResourceException e) {
			//TODO Log something about stealing failed because of no cards
			e.printStackTrace();
		}
	}

	/**
	 * @return The number of Victory Points a player has.
	 */
	public int getVictoryPoints() {
		int points = 0;
		/*for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.MONUMENT)
				points++;
		}*/
		if (game.getAchievements().isLargestArmy(this))
			points += 2;
		if (game.getAchievements().isLongestRoad(this))
			points += 2;
		for (City c : cities) {
			if (c.getVertex() != null)
				points += 2;
		}
		for (Settlement s : settlements) {
			if (s.getVertex() != null)
				points++;
		}

		return points;
	}
	
	public int getVictoryPointsWithMonuments() {
		int points = 0;
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.MONUMENT)
				points++;
		}
		points += this.getVictoryPoints();

		return points;
	}

	/**
	 * @param card
	 *            The card being received from the bank
	 * @post The card is added to the corresponding list in the player's hand
	 */
	public void receiveDevCard(DevCard card) {
		hand.getDevCards().add(card);
	}

	/**
	 * @pre The card is in the player's hand
	 * @param card
	 *            The card being returned
	 * @throws NoDevCardFoundException
	 * @post The card has been deleted from the player's hand and added to the
	 *       bank
	 */
	public void returnDevCard(DevCard card) throws NoDevCardFoundException {
		for (int i = 0; i < hand.getDevCards().size(); i++) {
			if (hand.getDevCards().get(i).equals(card)) {
				game.getBank().giveDevCardToBank(card);
				hand.getDevCards().remove(i);
				return;
			}
		}
		throw new NoDevCardFoundException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userIndex;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (userIndex != other.userIndex)
			return false;
		return true;
	}

	public int getUserIndex() {
		return userIndex;
	}

	public int getRoadLength() {
		// return size of interconnectedRoads
		return 0;
	}

	/**
	 * @return number of played knights
	 */
	public int getArmies() {
		return armies;
	}

	public int getMonuments() {
		return monuments;
	}

	public int getPoints() {
		return points;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	public int getCitiesFree() {
		Integer citiesFree = 0;
		for (City city : cities) {
			if (!city.isPlaced())
				citiesFree++;
		}
		return citiesFree;
	}

	public int getSettlementsFree() {
		Integer settlementsFree = 0;
		for (Settlement settlement : settlements) {
			if (!settlement.isPlaced())
				settlementsFree++;
		}
		return settlementsFree;
	}

	public int getRoadsFree() {
		Integer roadsFree = 0;
		for (Road road : roads) {
			if (!road.isPlaced())
				roadsFree++;
		}
		return roadsFree;
	}
	
	public City getFreeCity() {
		for (City city : cities) {
			if (!city.isPlaced())
				return city;
		}
		return null;
	}

	public Settlement getFreeSettlement() {
		for (Settlement settlement : settlements) {
			if (!settlement.isPlaced())
				return settlement;
		}
		return null;
	}

	public Road getFreeRoad() {
		for (Road road : roads) {
			if (!road.isPlaced())
				return road;
		}
		return null;
	}
	
	public Boolean hasRoadCost() {
		return hand.hasRoadCost();
	}
	
	public Boolean hasCityCost() {
		return hand.hasCityCost();
	}
	
	public Boolean hasSettlementCost() {
		return hand.hasSettlementCost();
	}
	
	public Boolean hasDevelopmentCost() {
		return hand.hasDevelopmentCost();
	}
	
	public ResourceType drawRandomResourceCard() throws ResourceException {
		return hand.drawRandomResourceCard();
	}
	
	public Boolean hasResource(ResourceType type, Integer num) throws BadResourceTypeException {
		return hand.hasResource(type, num);
	}
	
	public Boolean hasCards(Map<String, Object> resourceList) throws BadJSONException {
		return hand.hasCards(resourceList);
	}

	public boolean canDiscardCard() {
		return hand.canDiscardCard();
	}

	public boolean hasPort(PortType portType) {
		// TODO Auto-generated method stub
		return false;
	}

}
