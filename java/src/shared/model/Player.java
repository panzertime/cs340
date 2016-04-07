package shared.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import client.map.pseudo.PseudoCity;
import client.map.pseudo.PseudoRoad;
import client.map.pseudo.PseudoSettlement;
import shared.model.board.Board;
import shared.model.board.edge.Edge;
import shared.model.board.hex.tiles.water.PortType;
import shared.model.board.piece.Building;
import shared.model.board.piece.City;
import shared.model.board.piece.Road;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.Vertex;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.NoDevCardFoundException;
import shared.model.hand.Hand;
import shared.model.hand.ResourceType;
import shared.model.hand.development.DevCard;
import shared.model.hand.development.DevCardType;
import shared.model.hand.exceptions.NoRemainingResourceException;

public class Player {

	private Model game;
	public Model getGame() {
		return game;
	}

	private Hand hand;
	private Settlement[] settlements;
	private Road[] roads;
	private City[] cities;
	private String userName;
	private Integer playerIndex;
	private CatanColor userColor;
	private Integer armies;
	private Integer monuments = 0;
	private Integer points;
	private Boolean playedDevelopmentCard;
	private Boolean hasDiscarded;
	private Integer playerID;

	public Player(int playerID, int playerIndex, String name, CatanColor color, Model game)
	{
		this.game = game;
		this.playerIndex = playerIndex;
		this.playerID = playerID;
		this.userName = name;
		this.userColor = color;
		this.armies = 0;
		this.points = 0;
		this.playedDevelopmentCard = false;
		this.hasDiscarded = false;
		this.hand = new Hand(false);
		
		initPieces(); 
		
	}
	
	public Player(JSONObject player, Model game) throws BadJSONException {
		this.game = game;
		if (player == null)
			throw new BadJSONException();
		String c = (String) player.get("color");
		if (c == null)
			throw new BadJSONException();
		userColor = determineColor(c);
		String userName = (String) player.get("name");
		if (userName == null)
			throw new BadJSONException();
		this.userName = userName;
		Long userIndex = ((Long) player.get("playerIndex"));
		if (userIndex == null)
			throw new BadJSONException();
		this.playerIndex = userIndex.intValue();
		Long armies = ((Long) player.get("soldiers"));
		if (armies == null)
			throw new BadJSONException();
		this.armies = armies.intValue();
		Long monuments = ((Long) player.get("monuments"));
		if (monuments == null)
			throw new BadJSONException();
		this.monuments = monuments.intValue();
		Long points = ((Long) player.get("victoryPoints"));
		if (points == null)
			throw new BadJSONException();
		this.points = points.intValue();
		Boolean playedDevelopmentCard = (Boolean) player.get("playedDevCard");
		if (playedDevelopmentCard == null)
			throw new BadJSONException();
		this.playedDevelopmentCard = playedDevelopmentCard;
		hand = new Hand((JSONObject) player.get("resources"), (JSONObject) player.get("oldDevCards"),
				(JSONObject) player.get("newDevCards"));
		Boolean hasDiscarded = (Boolean) player.get("discarded");
		if (hasDiscarded == null)
			throw new BadJSONException();
		this.hasDiscarded = hasDiscarded;
		Long playerID = ((Long) player.get("playerID"));
		if (playerID == null)
			throw new BadJSONException();
		this.playerID = playerID.intValue();
		

		initPieces();   
	}

	public boolean equalsJSON(JSONObject player) {
		if (player == null)
			return false;
		String c = (String) player.get("color");
		if (c == null)
			return false;
		if (!userColor.equals(determineColor(c)))
			return false;
		String userName = (String) player.get("name");
		if (userName == null)
			return false;
		if (!this.userName.equals(userName))
			return false;
		Long userIndex = ((Long) player.get("playerIndex"));
		if (userIndex == null)
			return false;
		if (this.playerIndex != userIndex.intValue())
			return false;
		Long armies = ((Long) player.get("soldiers"));
		if (armies == null)
			return false;
		if (this.armies != armies.intValue())
			return false;
		Long monuments = ((Long) player.get("monuments"));
		if (monuments == null)
			return false;
		if (this.monuments != monuments.intValue())
			return false;
		Long points = ((Long) player.get("victoryPoints"));
		if (points == null)
			return false;
		if (this.points != points.intValue())
			return false;
		Boolean playedDevelopmentCard = (Boolean) player.get("playedDevCard");
		if (playedDevelopmentCard == null || playedDevelopmentCard != this.playedDevelopmentCard)
			return false;
		if (!hand.equalsJSON((JSONObject) player.get("resources"), (JSONObject) player.get("oldDevCards"),
				(JSONObject) player.get("newDevCards")))
			return false;
		Boolean hasDiscarded = (Boolean) player.get("discarded");
		if (hasDiscarded == null || hasDiscarded != this.hasDiscarded)
			return false;
		Long playerID = ((Long) player.get("playerID"));
		if (playerID == null)
			return false;
		if (this.playerID != playerID.intValue())
			return false;
		return true;
	}

	public CatanColor determineColor(String s) {
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
	
	public CatanColor getColor()
	{
		return userColor;
	}
	

	private void initPieces() {
		settlements = new Settlement[5];
		cities = new City[4];
		roads = new Road[15];
		for (int i = 0; i < 5; i++)
			settlements[i] = new Settlement(this);
		for (int i = 0; i < 4; i++)
			cities[i] = new City(this);
		for (int i = 0; i < 15; i++)
			roads[i] = new Road(this);
	}

	/**
	 * @param type
	 *            the type of resource to add
	 * @param num
	 *            the number of resource to be added
	 * @post player now has addition resources of type and num
	 */
	public void receiveResource(ResourceType type, Integer num) {
		hand.receiveResource(type, num);
	}

	/**
	 * @param type
	 *            the type of resource to take
	 * @param num
	 *            the number of resource to be taken
	 * @throws NoRemainingResourceException 
	 * @post player now has less resources of type and num
	 */
	public void sendResource(ResourceType type, Integer num) throws NoRemainingResourceException {
		hand.sendResource(type, num);
	}

	public void discardCard(ResourceType type, Integer num) throws NoRemainingResourceException {
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
	 * @throws NoRemainingResourceException 
	 * @pre hasSettlementCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 * @post wheat = wheat - 1
	 * @post sheep = sheep - 1
	 * 
	 */
	public void buySettlement() throws NoRemainingResourceException {

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
	 * @throws NoRemainingResourceException 
	 * @pre hasCityCost
	 * @post wheat = wheat - 2
	 * @post ore = ore - 3
	 */
	public void buyCity() throws NoRemainingResourceException {

		game.getBank().receiveResource(ResourceType.ORE, 3);
		game.getBank().receiveResource(ResourceType.WHEAT, 2);
		sendResource(ResourceType.ORE, 3);
		sendResource(ResourceType.WHEAT, 2);
	}

	/**
	 * @throws NoRemainingResourceException 
	 * @pre hasRoadCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 */
	public void buyRoad() throws NoRemainingResourceException {
		game.getBank().receiveResource(ResourceType.WOOD, 1);
		game.getBank().receiveResource(ResourceType.BRICK, 1);
		sendResource(ResourceType.WOOD, 1);
		sendResource(ResourceType.BRICK, 1);
	}

	/**
	 * @throws NoRemainingResourceException 
	 * @pre hasDevelopmentCost
	 * @post wheat = wheat - 1
	 * @post sheep = sheep - 1
	 * @post ore = ore - 1
	 * 
	 */
	public void buyDevelopment() throws NoRemainingResourceException, NoDevCardFoundException {
		game.getBank().receiveResource(ResourceType.WHEAT, 1);
		game.getBank().receiveResource(ResourceType.SHEEP, 1);
		game.getBank().receiveResource(ResourceType.ORE, 1);
		sendResource(ResourceType.WHEAT, 1);
		sendResource(ResourceType.SHEEP, 1);
		sendResource(ResourceType.ORE, 1);
		this.receiveDevCard(game.getBank().takeDevCardFromBank());
	}

	public Boolean canPlayDevelopmentCard() {
		return !playedDevelopmentCard;
	}

	public Boolean hasDiscarded() {
		return hasDiscarded;
	}
	
	public void setHasDiscard() {
		hasDiscarded = true;
	}
	
	public void clearHasDiscarded() {
		hasDiscarded = false;
	}
	
	public Boolean hasDevCard(DevCardType type)
	{
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == type)
				return true;
		}
		return false;
	}
	

	public Boolean hasDevCardToUse(DevCardType type) {
		if (playedDevelopmentCard)
			return false;
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == type && card.isEnabled())
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
	 * @throws NoRemainingResourceException 
	 * @post type1++ type2++ for player, type1-- type2-- for bank
	 */
	public void playYearOfPlenty(ResourceType type1, ResourceType type2)
			throws NoDevCardFoundException, NoRemainingResourceException{
		playedDevelopmentCard = true;
		this.game.getBank().sendResource(type1, 1);
		this.game.getBank().sendResource(type2, 1);
		this.receiveResource(type1, 1);
		this.receiveResource(type2, 1);
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
	 * @pre hasMonopoly()
	 * @param type
	 *            The type of resource the player will monopolize
	 * @throws NoDevCardFoundException
	 * @throws NoRemainingResourceException 
	 * @post the player increases in the quantity of type that the others
	 *       players combined all have and each other player's type = 0
	 */
	public void playMonopoly(ResourceType type) throws NoDevCardFoundException, NoRemainingResourceException {
		playedDevelopmentCard = true;
		for (Player p : this.game.getPlayers()) {
			if (p.hand.hasResource(type, 1)) {
				int n = p.hand.getResourceAmount(type);
				p.sendResource(type, n);
				receiveResource(type, n);
			}
		}
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
	 * @pre p has less than 0 resources
	 * @param p The player to be stolen from
	 * @throws NoRemainingResourceException 
	 * @post Player type++; p type--
	 */
	public void steal(Player p) throws NoRemainingResourceException {
		receiveResource(p.drawRandomResourceCard(), 1);
	}

	/**
	 * post points holds the number of Victory Points a player has.
	 */
	public int calculateVictoryPoints() {
		int points = 0;
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
		points += this.getMonuments();
		return points;
	}

	public int getVictoryPointsOfMonuments() {
		int points = 0;
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == DevCardType.MONUMENT)
				points++;
		}
		return points;
	}

	/**
	 * @param card The card being received from the bank
	 * @post The card is added to the corresponding list in the player's hand
	 */
	public void receiveDevCard(DevCard card) {
		hand.getDevCards().add(card);
	}

	/**
	 * @pre The card is in the player's hand
	 * @param card The card being returned
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
	
	public DevCard findDevCard(DevCardType type) throws NoDevCardFoundException
	{
		for (int i = 0; i < hand.getDevCards().size(); i++) {
			DevCard card = hand.getDevCards().get(i);
			if (card.getType() == type && card.isEnabled()) 
			{
				return card;
			}
		}
		throw new NoDevCardFoundException();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (playerIndex != other.playerIndex)
			return false;
		return true;
	}

	public Integer getPlayerIndex() {
		return playerIndex;
	}

	public Integer getPlayerID() {
		return playerID;
	}
	
	//recursive road add algorithm
	//add to set - continue left and right until no more
	
	public void addRoadsToSet(Road r, HashSet<Road> set, Board board)
	{
		set.add(r);
		r.setMarked(true);

		for (Vertex v: r.getEdge().getAllVertices())
		{
			v = board.getVertexAt(v.getVertexLocation());
			if (!v.hasBuilding() || v.getBuilding().getOwner().getPlayerIndex() == this.playerIndex)
			{
				for (Edge e: v.getAllEdges()){
					e = board.getEdgeAt(e.getEdgeLocation());
					if (e.hasRoad())
					{
						Road road = e.getRoad();
						if (road.getOwner().getPlayerIndex() == this.playerIndex && !road.isMarked())
							addRoadsToSet(road, set, board);
					}
				}
			}
		}
	}
	
	public void clearMarks()
	{
		for (Road r: roads)
		{
			r.setMarked(false);
		}
	}
	public int findLongestPathInSet(HashSet<Road> set)
	{
		return 0;
	}
	
	public int getRoadLength(Board board) {
		clearMarks();
		HashSet<HashSet<Road>> sets = new HashSet<HashSet<Road>>();
		for (Road r: roads)
		{
			if (r.isPlaced())
			{
				if (!r.isMarked())
				{
					HashSet<Road> set = new HashSet<Road>();
					this.addRoadsToSet(r, set, board);
					sets.add(set);
				}
			}
		}
		int max = 0;
		for (HashSet<Road> set: sets)
		{
			int i = set.size();
			//int i = this.findLongestPathInSet(set);
			if (i > max)
				max = i;
		}
		return max;
		
		
//		To begin with, separate out the roads into distinct sets, where all the road segments in each set are somehow connected. There's various methods on doing this, but here's one:
//
//		1	Pick a random road segment, add it to a set, and mark it
//		2	Branch out from this segment, ie. follow connected segments in both directions that aren't marked (if they're marked, we've already been here)
//		3	If found road segment is not already in the set, add it, and mark it
//		4	Keep going from new segments until you cannot find any more unmarked segments that are connected to those currently in the set
//		5	If there's unmarked segments left, they're part of a new set, pick a random one and start back at 1 with another set
//		
//		Note: As per the official Catan Rules, a road can be broken if another play builds a settlement on a joint between two segments. You need to detect this and not branch past the settlement.
//
//		Note, in the above, and following, steps, only consider the current players segments. You can ignore those other segments as though they weren't even on the map.
//
//		This gives you one or more sets, each containing one or more road segments.
//
//		Ok, for each set, do the following:
//
//		1	Pick a random road segment in the set that has only one connected road segment out from it (ie. you pick an endpoint)
//		2	If you can't do that, then the whole set is looping (one or more), so pick a random segment in this case
//		
//		Now, from the segment you picked, do a recursive branching out depth-first search, keeping track of the length of the current 
//		road you've found so far. Always mark road segments as well, and don't branch into segments already marked. This will allow 
//		the algorithm to stop when it "eats its own tail".
//
//		Whenever you need to backtrack, because there are no more branches, take a note of the current length, and if it is longer 
//		than the "previous maximum", store the new length as the maximum.
//
//		Do this for all the sets, and you should have your longest road.

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
	
	public void setMonuments(int m) {
		monuments = m;
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
	
	public Integer getRoadsPlaced() {
		return 15 - getRoadsFree();
	}
	
	public Integer getSettlementsPlaced() {
		return 5 - getSettlementsFree();
	}
	public Integer getCitiesPlaced() {
		return 4 - getCitiesFree();
	}
	
	public Boolean shouldSetupRoad() {
		if (getRoadsPlaced() > 1)
			return false;
		if (!(getRoadsPlaced() < getSettlementsPlaced()))
			return false;
		return true;
	}
	public Boolean shouldSetupSettlement() {
		if (getSettlementsPlaced() > 1)
			return false;
		if (!(getRoadsPlaced() == getSettlementsPlaced()))
			return false;
		return true;
	}
	
	
	public Boolean hasPort(PortType portType) {
		boolean flag = false;
		for (City city : cities) {
			if (city.isPlaced()){
				if (city.hasPort(portType))
					flag = true;
			}
		}
		for (Settlement settlement : settlements) {
			if(settlement.isPlaced()){
				if (settlement.hasPort(portType)){
					flag = true;
				}
			}
		}
		return flag;
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

	public ResourceType drawRandomResourceCard() throws NoRemainingResourceException {
		return hand.drawRandomResourceCard();
	}

	public Boolean hasResource(ResourceType type, Integer num) {
		return hand.hasResource(type, num);
	}

	public Boolean hasCards(Map<ResourceType, Integer> resources){
		return hand.hasCards(resources);
	}

	public boolean canDiscardCard() {
		return (hand.canDiscardCard() && !hasDiscarded);
	}

	public int getHandSize() {
		return hand.getHandSize();
	}
	
	public int getDevCardAmount(DevCardType type)
	{
		int amount = 0;
		for (DevCard card : hand.getDevCards()) {
			if (card.getType() == type)
				amount++;
		}
		return amount;
	}
	

	public int getResourceAmount(ResourceType type)
	{
		return this.hand.getResourceAmount(type);
	}

	
	public List<PseudoCity> getPseudoCities() {
		List<PseudoCity> pcities = new ArrayList<PseudoCity>();
		for (City city : cities) {
			if (city.getVertex() != null)
				pcities.add(new PseudoCity(city.getVertex().getVertexLocation().copy(), userColor));
		}
		return pcities;
	}
	
	public List<PseudoSettlement> getPseudoSettlements() {
		List<PseudoSettlement> psettlements = new ArrayList<PseudoSettlement>();
		for (Settlement settlement : settlements) {
			if (settlement.getVertex() != null)
				psettlements.add(new PseudoSettlement(settlement.getVertex().getVertexLocation().copy(), userColor));
		}
		return psettlements;
	}
	
	public List<PseudoRoad> getPseudoRoads() {
		List<PseudoRoad> proads = new ArrayList<PseudoRoad>();
		for (Road road : roads) {
			if (road.getEdge() != null)
				proads.add(new PseudoRoad(road.getEdge().getEdgeLocation().copy(), userColor));
		}
		return proads;
	}

	public void doMaritimeTrade(int ratio, ResourceType given, ResourceType received) throws NoRemainingResourceException {
		game.getBank().receiveResource(given, ratio);
		sendResource(given, ratio);
		game.getBank().sendResource(received, 1);
		receiveResource(received, 1);
		
	}

	public Building[] getBuildings() {
		Building[] buildings = new Building[this.getSettlementsPlaced() + this.getCitiesPlaced()];
		int i = 0;
		for (City c: cities)
		{
			if (c.isPlaced())
			{
				buildings[i] = c;
				i++;	
			}
		}
		for (Settlement s: settlements)
		{
			if (s.isPlaced())
			{
				buildings[i] = s;
				i++;	
			}
		}
		
		return buildings;
	}

	public void setPoints(int p) {
		points = p;
	}

	public JSONObject toJSON() {
		JSONObject jsonMap = new JSONObject();
		jsonMap.put("cities", this.getCitiesFree());
		jsonMap.put("color", this.getColor().toString().toLowerCase());
		jsonMap.put("discarded", hasDiscarded);
		jsonMap.put("monuments", monuments);
		jsonMap.put("name", this.getUserName());
		JSONObject newDevCards = new JSONObject();
		JSONObject oldDevCards = new JSONObject();
		newDevCards.put("monopoly", hand.getCards(DevCardType.MONOPOLY, false));
		oldDevCards.put("monopoly", hand.getCards(DevCardType.MONOPOLY, true));
		
		newDevCards.put("monument", hand.getCards(DevCardType.MONUMENT, false));
		oldDevCards.put("monument", hand.getCards(DevCardType.MONUMENT, true));
		
		newDevCards.put("roadBuilding", hand.getCards(DevCardType.ROADBUILDING, false));
		oldDevCards.put("roadBuilding", hand.getCards(DevCardType.ROADBUILDING, true));
		
		newDevCards.put("soldier", hand.getCards(DevCardType.KNIGHT, false));
		oldDevCards.put("soldier", hand.getCards(DevCardType.KNIGHT, true));
		
		newDevCards.put("yearOfPlenty", hand.getCards(DevCardType.YEAROFPLENTY, false));
		oldDevCards.put("yearOfPlenty", hand.getCards(DevCardType.YEAROFPLENTY, true));
		
		jsonMap.put("newDevCards", newDevCards);
		jsonMap.put("oldDevCards", oldDevCards);
		jsonMap.put("playerIndex", this.getPlayerIndex());
		jsonMap.put("playedDevCard", this.playedDevelopmentCard);
		jsonMap.put("playerID", this.playerID);
		JSONObject resourceList = new JSONObject();
		resourceList.put("wood", this.hand.getWood());
		resourceList.put("brick", this.hand.getBrick());
		resourceList.put("sheep", this.hand.getSheep());
		resourceList.put("wheat", this.hand.getWheat());
		resourceList.put("ore", this.hand.getOre());
		jsonMap.put("resources", resourceList);
		jsonMap.put("roads", this.getRoadsFree());
		jsonMap.put("settlements", this.getSettlementsFree());
		jsonMap.put("soldiers", this.getArmies());
		jsonMap.put("victoryPoints", this.getPoints());
		return jsonMap;
	}

	public void updateDevCards() {
		this.playedDevelopmentCard = false;
		for (DevCard card: this.hand.getDevCards())
			card.setEnabled(true);
	}
	
	public Settlement[] getSettlements() {
		return settlements;
	}

	public Road[] getRoads() {
		return roads;
	}

	public City[] getCities() {
		return cities;
	}

	public void setUserColor(CatanColor userColor) {
		this.userColor = userColor;
	}

	public void incrementArmies() {
		this.armies++;
	}

	public void playedDevCard() {
		this.playedDevelopmentCard = true;
	}


}
