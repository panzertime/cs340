package shared.models;

import shared.models.board.edge.Edge;
import shared.models.board.piece.City;
import shared.models.board.piece.NullPlayerException;
import shared.models.board.piece.Road;
import shared.models.board.piece.Settlement;
import shared.models.board.vertex.Vertex;
import shared.models.hand.Hand;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;
import shared.models.hand.development.DevCard;
import shared.models.hand.development.NoDevCardFoundException;

public class Player {
	
	private Hand hand;
	private Settlement[] settlements = new Settlement[5];
	private City[] cities = new City[4];
	private Road[] roads = new Road[15];
	private String userName;
	private int armies;
	private Boolean hasLargestArmy;
	private Boolean hasLongestRoad;
	
	public Player(String userName) {
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
	public void giveResource(ResourceType type, Integer num) throws ResourceException {
		hand.giveResource(type, num);
	}


	/**
	 * @param type the type of resource to take
	 * @param num the number of resource to be taken
	 * @throws ResourceException
	 * @post player now has less resources of type and num 
	 */
	public void takeResource(ResourceType type, Integer num) throws ResourceException {
		hand.takeResource(type, num);
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
	 * @return true if player has a City piece available
	 */
	public Boolean hasCityPiece() {
		return null;}

	/**
	 * @return true if player has a Settlement piece available
	 */
	public Boolean hasSettlementPiece() {
		return null;}
	
	/**
	 * @return true if player has a Road piece available
	 */
	public Boolean hasRoadPiece() {
		return null;}
	
	/**
	 * @return true if player has a YearOfPlenty card available and enabled
	 */
	public Boolean hasYearOfPlenty() {
		return null;}
	
	/**
	 * @pre hasYearOfPlenty
	 * @param type1 - the first resource the player selects
	 * @param type2 - the second resource the player selects
	 * @throws NoDevCardFoundException
	 * @post type1++ type2++ for player, type1-- type2-- for bank
	 */
	public void playYearOfPlenty(ResourceType type1, ResourceType type2) throws NoDevCardFoundException {}
	
	/**
	 * @return true if player has a RoadBuilder card available and enabled
	 */
	public Boolean hasRoadBuilder() {
		return null;}

	/**
	 * @pre hasRoadBuilder
	 * @throws NoDevCardFoundException
	 * @post buildRoad() has been called twice
	 */
	public void playRoadBuilder() throws NoDevCardFoundException {}
	
	/**
	 * @return true if player has a Monopoly card available and enabled
	 */
	public Boolean hasMonopoly() {
		return null;}
	
	/**
	 * @pre hasMonopoly()
	 * @param type The type of resource the player will monopolize
	 * @throws NoDevCardFoundException
	 * @post the player increases in the quantity of type that the others players combined all have and each other player's type = 0
	 */
	public void playMonopoly(ResourceType type) throws NoDevCardFoundException {}
	
	/**
	 * @return true if player has a Knight card available and enabled
	 */
	public Boolean hasKnight() {
		return null;}
	
	/**
	 * 
	 * @throws NoDevCardFoundException
	 * @post armies++
	 */
	public void playKnight() throws NoDevCardFoundException {}
	
	
	/**
	 * @pre p has > 0 resources
	 * @param p The player to be stolen from
	 * @return random type of resource selected from hand of p
	 * @post Player type++; p type--
	 */
	public ResourceType steal(Player p) {
		return null;}
	
	
	/**
	 * 
	 * @param v - A vertex to be checked
	 * @return True if a settlement can be build
	 */
	public Boolean canBuildSettlement(Vertex v) {
		return null;}
	/**
	 * 
	 * @param v - A vertex to be checked
	 * @return - True if a city can be built
	 */
	public Boolean canBuildCity(Vertex v) {
		return null;}
	/**
	 * 
	 * @param e - An edge to be checked
	 * @return - True if a Road can be built
	 */
	public Boolean canBuildRoad(Edge e) {
		return null;}
	/**
	 * 
	 * @pre canBuildSettlement()
	 * @param v - A vertex to be checked
	 * @throws BuildException
	 * @post 
	 */
	public void buildSettlement(Vertex v) throws BuildException {}
	/**
	 * @pre canBuildCity()
	 * @param v - A vertex to be checked
	 * @throws BuildException
	 * @post 
	 */
	public void buildCity(Vertex v) throws BuildException {}
	/**
	 * @pre canBuildRoad()
	 * @param e - An edge to be checked
	 * @throws BuildException
	 * @post 
	 */
	public void buildRoad(Edge e) throws BuildException {}
	
	
	/**
	 * @return The number of Victory Points a player has.
	 * 
	 */
	public int getVictoryPoints() {
		return 0;}
	
	/**
	 * @param card The card being received from the bank
	 * @post The card is added to the corresponding list in the player's hand
	 */
	public void receiveDevCard(DevCard card) {}

	/**
	 * @pre The card is in the player's hand
	 * @param card The card being returned
	 * @throws NoDevCardFoundException
	 * @post The card has been deleted from the player's hand and added to the bank
	 */
	public void returnDevCard(DevCard card) throws NoDevCardFoundException {}

/**
 * @return hasLargestArmy - True if has
 */
	public Boolean getLargestArmy() {
		return hasLargestArmy;
	}

/**
 * 
 * @param hasLargestArmy - True if has
 */
	public void setLargestArmy(Boolean hasLargestArmy) {
		this.hasLargestArmy = hasLargestArmy;
	}

/**
 * 
 * @return hasLongestRoad - True if has
 */
	public Boolean getHasLongestRoad() {
		return hasLongestRoad;
	}

/**
 * 
 * @param hasLongestRoad - True if has
 */
	public void setHasLongestRoad(Boolean hasLongestRoad) {
		this.hasLongestRoad = hasLongestRoad;
	}
	
/**
 * 
 * @return number of played knights
 */
	public int getArmies() {
		return armies;
	}


}
