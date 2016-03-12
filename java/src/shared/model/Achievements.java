package shared.model;

import java.util.List;
import java.util.Map;

public class Achievements {

	
	private Player largestArmy;
	private Player longestRoad;
	
	public Achievements (Player longestRoad, Player largestArmy)
	{ 
		this.largestArmy = largestArmy;
		this.longestRoad = longestRoad;
	}
	
	
	public boolean equalsJSON(Integer longestRoad, Integer largestArmy) {
		if(!(this.longestRoad == null &&
				longestRoad == -1)) {
				if (this.longestRoad.getPlayerIndex() != longestRoad) return false;
			}
		if(!(this.largestArmy == null &&
				largestArmy == -1)) {
				if (this.largestArmy.getPlayerIndex() != largestArmy) return false;
			}
		return true;
	}
	/**
	 * @pre A Road has been built on the board by every player
	 * @post The Player with the Longest Road is set as longestRoad and their haslongestRoad is set to true
	 */
	public boolean checkRoads(Map<Integer, Player> players) {
		if (longestRoad == null)
		{
			for (Player p: players.values())
			{
				if (p.getRoadLength() >= 5)
				longestRoad = p;
				return true;
			}			
		}
		else
		{
			int max = longestRoad.getRoadLength();
			for (Player p: players.values())
			{
				if (p.getRoadLength() > max)
					longestRoad = p;
				return true;
			}

		}
		return false;
	}
	
	/**
	  * @post The Player with the Largest Army is set as largestArmy and their haslargestArmy is set to true
	  */
	public boolean checkArmies(Map<Integer, Player> players) {
		if (largestArmy == null)
		{
			for (Player p: players.values())
			{
				if (p.getArmies() >= 3)
					largestArmy = p;
				return true;
			}			
		}
		else
		{
			int max = largestArmy.getArmies();
			for (Player p: players.values())
			{
				if (p.getArmies() > max)
					largestArmy = p;
				return true;
			}

		}
		return false;

		
	}
	
	/**
	 * 
	 * @param p The Player who has the Longest Road
	 * @post P is set with the hasLongestRoad true and last player with hasLongestRoad true is set to false 
	 */
	public void setLongestRoad(Player p) {
		
	}

	/**
	 * 
	 * @param p The Player who has the Largest Army
	 * @post P is set with the hasLargestArmy true and last player with hasLargestArmy true is set to false 
	 */
	public void setLargestArmy(Player p) {
		
	}

	
	public Boolean isLargestArmy(Player p) {
		if (largestArmy == null)
		return false;
		if (p.getPlayerIndex() == largestArmy.getPlayerIndex())
		return true;
		return false;
		}
	
	
	public Boolean isLongestRoad(Player p) {
		if (longestRoad == null)
		return false;
		if (p.getPlayerIndex() == longestRoad.getPlayerIndex())
		return true;
		return false;
		}

	

	
}
