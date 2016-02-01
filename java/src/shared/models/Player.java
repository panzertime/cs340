package shared.models;

import java.util.ArrayList;

import shared.models.board.edge.Edge;
import shared.models.board.piece.City;
import shared.models.board.piece.NullPlayerException;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.piece.Settlement;
import shared.models.board.vertex.Vertex;
import shared.models.definitions.CatanColor;
import shared.models.exceptions.BadResourceTypeException;
import shared.models.exceptions.NoDevCardFoundException;
import shared.models.hand.Hand;
import shared.models.hand.ResourceType;
import shared.models.hand.development.DevCard;
import shared.models.hand.development.DevCardType;
import shared.models.hand.exceptions.ResourceException;

public class Player {
	
	private GameModel game;
	private Hand hand;
	private Settlement[] settlements = new Settlement[5];
	private City[] cities = new City[4];
	private Road[] roads = new Road[15];
	private String userName;
	private int userIndex;
	private CatanColor userColor;
	private int armies;
	private Boolean playedDevelopmentCard;
	
	public Player(String userName, int userIndex, GameModel game) {
		this.game = game;
		try {
			for (int i = 0; i < 5; i++)
				settlements[i] = new Settlement(this);
			for (int i = 0; i < 4; i++) 
				cities[i] = new City(this);
			for (int i = 0; i < 15; i++)
				roads[i] = new Road(this);
		} catch (NullPlayerException e) {
			//TODO log "no player passed to building or Road Constructors at" print stack trace
		}
		setUserName(userName);
	}
	
	
	/**
	 * @param type the type of resource to add
	 * @param num the number of resource to be added
	 * @throws ResourceException
	 * @post player now has addition resources of type and num 
	 */
	public void receiveResource(ResourceType type, Integer num) throws ResourceException {
		hand.receiveResource(type, num);
	}

	

	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws ResourceException
	 * @post player now has less resources of type and num 
	 */
	public void sendResource(ResourceType type, Integer num) throws ResourceException {
		hand.sendResource(type, num);
	}
	
	public void discardCard(ResourceType type, Integer num) throws ResourceException
	{
		hand.sendResource(type, num);
		game.getBank().receiveResource(type, num);
	}
	


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	/**
	 * @return the hand
	 */
	public Hand getHand() {
		return hand;
	}


	/**
	 * @return true if player has a City piece available
	 */
	public Boolean hasCityPiece() {
		
		for (City c: cities)
		{
			if (c.getVertex() != null) return true;
		}
		return false;
		}

	/**
	 * @return true if player has a Settlement piece available
	 */
	public Boolean hasSettlementPiece() {
		for (Settlement s: settlements)
		{
			if (s.getVertex() != null) return true;
		}
		return false;
		}
	
	/**
	 * @return true if player has a Road piece available
	 */
	public Boolean hasRoadPiece() {
		for (Road r: roads)
		{
			if (r.getEdge() != null) return true;
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
	
		this.game.getBank().receiveResource(ResourceType.WOOD, 1);
		this.game.getBank().receiveResource(ResourceType.BRICK, 1);
		this.game.getBank().receiveResource(ResourceType.WHEAT, 1);
		this.game.getBank().receiveResource(ResourceType.SHEEP, 1);
		this.sendResource(ResourceType.WOOD, 1);
		this.sendResource(ResourceType.BRICK, 1);
		this.sendResource(ResourceType.WHEAT, 1);
		this.sendResource(ResourceType.SHEEP, 1);
	}
	
	
	/**
	 * 
	 * @throws ResourceException 
	 * @pre hasCityCost
	 * @post wheat = wheat - 2
	 * @post ore = ore - 3
	 */
	public void buyCity() throws ResourceException {

		
		this.game.getBank().receiveResource(ResourceType.ORE, 3);
		this.game.getBank().receiveResource(ResourceType.WHEAT, 2);
		this.sendResource(ResourceType.ORE, 3);
		this.sendResource(ResourceType.WHEAT, 2);
	}
	
	
	/**
	 * @throws ResourceException 
	 * @pre hasRoadCost
	 * @post wood = wood - 1
	 * @post brick = brick - 1
	 */
	public void buyRoad() throws ResourceException {
		this.game.getBank().receiveResource(ResourceType.WOOD, 1);
		this.game.getBank().receiveResource(ResourceType.BRICK, 1);
		this.sendResource(ResourceType.WOOD, 1);
		this.sendResource(ResourceType.BRICK, 1);
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
		this.game.getBank().receiveResource(ResourceType.WHEAT, 1);
		this.game.getBank().receiveResource(ResourceType.SHEEP, 1);
		this.game.getBank().receiveResource(ResourceType.ORE, 1);
		this.sendResource(ResourceType.WHEAT, 1);
		this.sendResource(ResourceType.SHEEP, 1);
		this.sendResource(ResourceType.ORE, 1);
		}
	
	
	/**
	 * @param given The type of card being given by the player
	 * @param received The type of card being received by the player
	 * @throws ResourceException 
	 * @post type given = type - 4
	 * @post type received = type + 1
	 * 
	 */
	public void buyResource(ResourceType given, ResourceType received) throws ResourceException {
		this.game.getBank().receiveResource(given, 4);
		this.sendResource(given, 4);
		this.game.getBank().sendResource(received, 1);
		this.receiveResource(received, 1);
	}
	
	
	/**
	 * @param given The type of card being given by the player
	 * @param received The type of card being received by the player
	 * @throws ResourceException 
	 * @post type given = type - 3
	 * @post type received = type + 1
	 */
	public void buyResourceWith3Port(ResourceType given, ResourceType received) throws ResourceException {
		
		this.game.getBank().receiveResource(given, 3);
		this.sendResource(given, 3);
		this.game.getBank().sendResource(received, 1);
		this.receiveResource(received, 1);
	}

	/**
	 * @param given The type of card being given by the player
	 * @param received The type of card being received by the player
	 * @throws ResourceException 
	 * @post type given = type - 2
	 * @post type received = type + 1
	 */
	public void buyResourceWith2Port(ResourceType given, ResourceType received) throws ResourceException {
		this.game.getBank().receiveResource(given, 2);
		this.sendResource(given, 2);
		this.game.getBank().sendResource(received, 1);
		this.receiveResource(received, 1);	
	}
	
	public Boolean canPlayDevelopmentCard()
	{
		return playedDevelopmentCard;
	}

	
	/**
	 * @return true if player has a YearOfPlenty card available and enabled
	 */
	public Boolean hasYearOfPlenty() {
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.YEAROFPLENTY) return true;
		}
		return false;}
	/**
	 * @return true if player has a YearOfPlenty card available and enabled
	 */
	public Boolean hasYearOfPlentyToUse() {
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.YEAROFPLENTY && card.isEnabled()) return true;
		}
		return false;}

	
	/**
	 * @pre hasYearOfPlenty
	 * @param type1 - the first resource the player selects
	 * @param type2 - the second resource the player selects
	 * @throws NoDevCardFoundException
	 * @throws ResourceException 
	 * @post type1++ type2++ for player, type1-- type2-- for bank
	 */
	public void playYearOfPlenty(ResourceType type1, ResourceType type2) throws NoDevCardFoundException, ResourceException 
	{
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
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.ROADBUILDING) return true;
		}
		return false;}

	/**
	 * @return true if player has a RoadBuilder card available and enabled
	 */
	public Boolean hasRoadBuildingToUse() {
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.ROADBUILDING && card.isEnabled()) return true;
		}
		return false;}
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
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.MONOPOLY) return true;
		}
		return false;}

	/**
	 * @return true if player has a Monopoly card available and enabled
	 */
	public Boolean hasMonopolyToUse() {
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.MONOPOLY && card.isEnabled()) return true;
		}
		return false;}
	/**
	 * @pre hasMonopoly()
	 * @param type The type of resource the player will monopolize
	 * @throws NoDevCardFoundException
	 * @throws ResourceException 
	 * @post the player increases in the quantity of type that the others players combined all have and each other player's type = 0
	 */
	public void playMonopoly(ResourceType type) throws NoDevCardFoundException, ResourceException {
		playedDevelopmentCard = true;
		for (Player p: this.game.getPlayers())
		{
			if (p.getHand().hasResource(type, 1))
			{
				int n = p.getHand().getResourceAmount(type);
				p.sendResource(type, n);
				this.receiveResource(type, n);
			}
		}
	}
	
	/**
	 * @return true if player has a Knight card available and enabled
	 */
	public Boolean hasKnight() {
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.KNIGHT) return true;
		}
		return false;}

	/**
	 * @return true if player has a Knight card available and enabled
	 */
	public Boolean hasKnightToUse() {
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.KNIGHT && card.isEnabled()) return true;
		}
		return false;}
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
	 * @param p The player to be stolen from
	 * @throws ResourceException 
	 * @post Player type++; p type--
	 */
	public void steal(Player p) throws ResourceException {
		this.receiveResource(p.getHand().drawRandomCard(), 1);
		}
	
	
	/**
	 * 
	 * @param v - A vertex to be checked
	 * @return True if a settlement can be build
	 */
	public Boolean canBuildSettlement(Vertex v) {
		if (v.getBuilding() != null) return false;
		if (!v.isBuildable()) return false;
		ArrayList<Edge> edges = v.getAllEdges();
		for (Edge e: edges)
		{
			if (e.getOtherVertex(v).getBuilding() != null) return false;
		}
		for (Edge e: edges)
		{
			if (e.getRoad().getOwner().equals(this)) return true;
		}
		return false;
	}
//Check if empty
//Check if one valid land vertex
//Check if building is on any of edges' vertices
//Check if road is on adjacent edge (CHECK ALL EDGES)

	/**
	 * 
	 * @param v - A vertex to be checked
	 * @return - True if a city can be built
	 */
	public Boolean canBuildCity(Vertex v) {
//Check if has building 
//Check	if building is settlement (RESPOND PIECE TYPE)
//Check if settlement owner is player (COMPARABLE INTERFACE)
		return null;}
	/**
	 * 
	 * @param e - An edge to be checked
	 * @return - True if a Road can be built
	 */
	public Boolean canBuildRoad(Edge e) {
		if (e.getRoad() != null) return false;
		if (!e.isBuildable()) return false;
		ArrayList<Vertex> vertices = e.getAllVertices();
		for (Vertex v: vertices)
		{
			if (v.getBuilding().getOwner().equals(this)) return true;
			if (v.getBuilding() == null)
			{
				if (v.getLeftEdge(e).getRoad().getOwner().equals(this)) return true;
				if (v.getRightEdge(e).getRoad().getOwner().equals(this)) return true;
			}
		}
		return false;
		
		
//Check if is empty
//Check if adjacent vertices' (that don't have building of other player) edges have road (CHECK ALL VERTICES)
//Check if road is owned by player
		return null;}
	/**
	 * 
	 * @pre canBuildSettlement()
	 * @param v - A vertex to be checked
	 * @throws BuildException
	 * @throws PositionTakenException 
	 * @post 
	 */
	public void buildSettlement(Vertex v) throws BuildException, PositionTakenException{
		for (Settlement s: settlements)
		{
			if (s.getVertex() == null)
			{
				s.setVertex(v);
				v.setBuilding(s);
				return;
			}
		}
		throw new BuildException();
	}
	
	/**
	 * @pre canBuildCity()
	 * @param v - A vertex to be checked
	 * @throws BuildException
	 * @throws PositionTakenException 
	 * @post 
	 */
	public void buildCity(Vertex v) throws BuildException, PositionTakenException{
		for (City c: cities)
		{
			if (c.getVertex() == null)
			{
				v.getBuilding().setVertex(null);
				v.setBuilding(c);
				c.setVertex(v);
				return;
			}
		}
		throw new BuildException();
	}

	/**
	 * @pre canBuildRoad()
	 * @param e - An edge to be checked
	 * @throws BuildException
	 * @throws PositionTakenException 
	 * @post 
	 */
	public void buildRoad(Edge e) throws BuildException, PositionTakenException {
		for (Road r: roads)
		{
			if (r.getEdge() == null)
			{
				r.setEdge(e);
				e.setRoad(r);
				return;
			}
		}
		throw new BuildException();
	}
	
	/**
	 * @return The number of Victory Points a player has.
	 * 
	 */
	public int getVictoryPoints() {
		int points = 0;
		for (DevCard card: this.getHand().getDevCards())
		{
			if (card.getType() == DevCardType.VICTORY) points++;
		}
		if (game.getAchievements().isLargestArmy(this)) points += 2;
		if (game.getAchievements().isLongestRoad(this)) points += 2;
		for (City c: cities)
		{
			if (c.getVertex() != null) points +=2;
		}
		for (Settlement s: settlements)
		{
			if (s.getVertex() != null) points++;
		}
		
		return points;}
	
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
	 * @post The card has been deleted from the player's hand and added to the bank
	 */
	public void returnDevCard(DevCard card) throws NoDevCardFoundException {
		for (int i = 0; i < hand.getDevCards().size(); i++)
		{
			if (hand.getDevCards().get(i).equals(card))
			{
				game.getBank().giveDevCardToBank(card);
				hand.getDevCards().remove(i);
				return;
			}
		}
		throw new NoDevCardFoundException();
	}

/**
 * 
 * @return number of played knights
 */
	public int getArmies() {
		return armies;
	}


/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + userIndex;
	return result;
}


/* (non-Javadoc)
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


}
