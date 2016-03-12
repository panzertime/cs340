
package shared.model.hand;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;

import shared.logger.Log;
import shared.model.exceptions.BadJSONException;
import shared.model.hand.development.DevCard;
import shared.model.hand.development.Knight;
import shared.model.hand.development.Monopoly;
import shared.model.hand.development.Monument;
import shared.model.hand.development.RoadBuilding;
import shared.model.hand.development.YearOfPlenty;
import shared.model.hand.exceptions.NoRemainingResourceException;

public class Hand {

	private int wood = 0;
	private int brick = 0;
	private int sheep = 0;
	private int wheat = 0;
	private int ore = 0;

	private ArrayList<DevCard> devCards;

	/**
	 * 
	 * @return wood
	 */
	public Integer getWood() {
		return wood;
	}

	/**
	 * 
	 * @return brick
	 */
	public Integer getBrick() {
		return brick;
	}

	/**
	 * 
	 * @return sheep
	 */

	public Integer getSheep() {
		return sheep;
	}

	/**
	 * 
	 * @return wheat
	 */
	public Integer getWheat() {
		return wheat;
	}

	/**
	 * 
	 * @return ore
	 */
	public Integer getOre() {
		return ore;
	}

	/**
	 * 
	 * @param resourceList
	 * @param deckList
	 * @throws BadJSONException
	 */

	public Hand(JSONObject resourceList, JSONObject deckList) throws BadJSONException {
		if (resourceList == null || deckList == null)
			throw new BadJSONException();

		Long wood = ((Long) resourceList.get("wood"));
		Long brick = ((Long) resourceList.get("brick"));
		Long sheep = ((Long) resourceList.get("sheep"));
		Long wheat = ((Long) resourceList.get("wheat"));
		Long ore = ((Long) resourceList.get("ore"));

		if (wood == null || brick == null || sheep == null || wheat == null || ore == null)
			throw new BadJSONException();

		this.wood = wood.intValue();
		this.brick = brick.intValue();
		this.sheep = sheep.intValue();
		this.wheat = wheat.intValue();
		this.ore = ore.intValue();

		devCards = new ArrayList<DevCard>();
		Long yOP = ((Long) deckList.get("yearOfPlenty"));
		Long mono = ((Long) deckList.get("monopoly"));
		Long sol = ((Long) deckList.get("soldier"));
		Long rB = ((Long) deckList.get("roadBuilding"));
		Long monu = ((Long) deckList.get("monument"));

		if (yOP == null || mono == null || sol == null || rB == null || monu == null)
			throw new BadJSONException();

		for (int i = 0; i < yOP.intValue(); i++) {
			DevCard card = new YearOfPlenty();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < mono.intValue(); i++) {
			DevCard card = new Monopoly();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < sol.intValue(); i++) {
			DevCard card = new Knight();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < rB.intValue(); i++) {
			DevCard card = new RoadBuilding();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < monu.intValue(); i++) {
			DevCard card = new Monument();
			card.setEnabled(false);
			devCards.add(card);
		}

	}

	public boolean equalsJSON(JSONObject resourceList, JSONObject deckList) {
		if (resourceList == null || deckList == null)
			return false;

		Long wood = ((Long) resourceList.get("wood"));
		Long brick = ((Long) resourceList.get("brick"));
		Long sheep = ((Long) resourceList.get("sheep"));
		Long wheat = ((Long) resourceList.get("wheat"));
		Long ore = ((Long) resourceList.get("ore"));
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null)
			return false;

		if (this.wood != wood.intValue())
			return false;
		if (this.brick != brick.intValue())
			return false;
		if (this.sheep != sheep.intValue())
			return false;
		if (this.wheat != wheat.intValue())
			return false;
		if (this.ore != ore.intValue())
			return false;

		Long yearOfPlenty = ((Long) deckList.get("yearOfPlenty"));
		Long monopoly = ((Long) deckList.get("monopoly"));
		Long soldier = ((Long) deckList.get("soldier"));
		Long roadBuilder = ((Long) deckList.get("roadBuilding"));
		Long monument = ((Long) deckList.get("monument"));
		if (yearOfPlenty == null || monopoly == null || soldier == null || roadBuilder == null || monument == null)
			return false;

		Integer yOP = yearOfPlenty.intValue();
		Integer mono = monopoly.intValue();
		Integer sol = soldier.intValue();
		Integer rB = roadBuilder.intValue();
		Integer monu = monument.intValue();
		for (int j = 0; j < devCards.size(); j++) {
			switch (devCards.get(j).getType()) {
			case YEAROFPLENTY:
				yOP--;
				break;
			case MONOPOLY:
				mono--;
				break;
			case KNIGHT:
				sol--;
				break;
			case ROADBUILDING:
				rB--;
				break;
			case MONUMENT:
				monu--;
				break;
			}
			if (devCards.get(j).isEnabled())
				return false;
		}
		if (yOP != 0 || mono != 0 || sol != 0 || rB != 0 || monu != 0)
			return false;

		return true;
	}

	public Hand(JSONObject resourceList, JSONObject oldDevList, JSONObject newDevList) throws BadJSONException {

		if (resourceList == null || oldDevList == null || newDevList == null)
			throw new BadJSONException();
		Long wood = ((Long) resourceList.get("wood"));
		Long brick = ((Long) resourceList.get("brick"));
		Long sheep = ((Long) resourceList.get("sheep"));
		Long wheat = ((Long) resourceList.get("wheat"));
		Long ore = ((Long) resourceList.get("ore"));

		if (wood == null || brick == null || sheep == null || wheat == null || ore == null)
			throw new BadJSONException();

		this.wood = wood.intValue();
		this.brick = brick.intValue();
		this.sheep = sheep.intValue();
		this.wheat = wheat.intValue();
		this.ore = ore.intValue();
		devCards = new ArrayList<DevCard>();

		Long yOP = ((Long) oldDevList.get("yearOfPlenty"));
		Long mono = ((Long) oldDevList.get("monopoly"));
		Long sol = ((Long) oldDevList.get("soldier"));
		Long rB = ((Long) oldDevList.get("roadBuilding"));
		Long monu = ((Long) oldDevList.get("monument"));

		if (yOP == null || mono == null || sol == null || rB == null || monu == null)
			throw new BadJSONException();

		for (int i = 0; i < yOP.intValue(); i++) {
			DevCard card = new YearOfPlenty();
			card.setEnabled(true);
			devCards.add(card);
		}

		for (int i = 0; i < mono.intValue(); i++) {
			DevCard card = new Monopoly();
			card.setEnabled(true);
			devCards.add(card);
		}

		for (int i = 0; i < sol.intValue(); i++) {
			DevCard card = new Knight();
			card.setEnabled(true);
			devCards.add(card);
		}

		for (int i = 0; i < rB.intValue(); i++) {
			DevCard card = new RoadBuilding();
			card.setEnabled(true);
			devCards.add(card);
		}

		for (int i = 0; i < monu.intValue(); i++) {
			DevCard card = new Monument();
			card.setEnabled(true);
			devCards.add(card);
		}
		yOP = ((Long) newDevList.get("yearOfPlenty"));
		mono = ((Long) newDevList.get("monopoly"));
		sol = ((Long) newDevList.get("soldier"));
		rB = ((Long) newDevList.get("roadBuilding"));
		monu = ((Long) newDevList.get("monument"));

		if (yOP == null || mono == null || sol == null || rB == null || monu == null)
			throw new BadJSONException();

		for (int i = 0; i < yOP.intValue(); i++) {
			DevCard card = new YearOfPlenty();
			card.setEnabled(false);
			devCards.add(card);
		}

		for (int i = 0; i < mono.intValue(); i++) {
			DevCard card = new Monopoly();
			card.setEnabled(false);
			devCards.add(card);
		}

		for (int i = 0; i < sol.intValue(); i++) {
			DevCard card = new Knight();
			card.setEnabled(false);
			devCards.add(card);
		}

		for (int i = 0; i < rB.intValue(); i++) {
			DevCard card = new RoadBuilding();
			card.setEnabled(false);
			devCards.add(card);
		}

		for (int i = 0; i < monu.intValue(); i++) {

			DevCard card = new Monument();
			card.setEnabled(false);
			devCards.add(card);
		}

	}

	public boolean equalsJSON(JSONObject resourceList, JSONObject oldDevList, JSONObject newDevList) {
		if (resourceList == null || oldDevList == null || newDevList == null)
			return false;

		Long wood = ((Long) resourceList.get("wood"));
		Long brick = ((Long) resourceList.get("brick"));
		Long sheep = ((Long) resourceList.get("sheep"));
		Long wheat = ((Long) resourceList.get("wheat"));
		Long ore = ((Long) resourceList.get("ore"));
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null)
			return false;

		if (this.wood != wood.intValue())
			return false;
		if (this.brick != brick.intValue())
			return false;
		if (this.sheep != sheep.intValue())
			return false;
		if (this.wheat != wheat.intValue())
			return false;
		if (this.ore != ore.intValue())
			return false;

		Long yearOfPlenty = ((Long) oldDevList.get("yearOfPlenty"));
		Long monopoly = ((Long) oldDevList.get("monopoly"));
		Long soldier = ((Long) oldDevList.get("soldier"));
		Long roadBuilder = ((Long) oldDevList.get("roadBuilding"));
		Long monument = ((Long) oldDevList.get("monument"));
		if (yearOfPlenty == null || monopoly == null || soldier == null || roadBuilder == null || monument == null)
			return false;

		Integer yOP = yearOfPlenty.intValue();
		Integer mono = monopoly.intValue();
		Integer sol = soldier.intValue();
		Integer rB = roadBuilder.intValue();
		Integer monu = monument.intValue();
		for (int j = 0; j < devCards.size(); j++) {
			switch (devCards.get(j).getType()) {
			case YEAROFPLENTY:
				if (devCards.get(j).isEnabled())
					yOP--;
				break;
			case MONOPOLY:
				if (devCards.get(j).isEnabled())
					mono--;
				break;
			case KNIGHT:
				if (devCards.get(j).isEnabled())
					sol--;
				break;
			case ROADBUILDING:
				if (devCards.get(j).isEnabled())
					rB--;
				break;
			case MONUMENT:
				if (devCards.get(j).isEnabled())
					monu--;
				break;
			}
		}
		if (yOP != 0 || mono != 0 || sol != 0 || rB != 0 || monu != 0)
			return false;

		yearOfPlenty = ((Long) newDevList.get("yearOfPlenty"));
		monopoly = ((Long) newDevList.get("monopoly"));
		soldier = ((Long) newDevList.get("soldier"));
		roadBuilder = ((Long) newDevList.get("roadBuilding"));
		monument = ((Long) newDevList.get("monument"));
		if (yearOfPlenty == null || monopoly == null || soldier == null || roadBuilder == null || monument == null)
			return false;

		yOP = yearOfPlenty.intValue();
		mono = monopoly.intValue();
		sol = soldier.intValue();
		rB = roadBuilder.intValue();
		monu = monument.intValue();

		for (int j = 0; j < devCards.size(); j++) {
			switch (devCards.get(j).getType()) {
			case YEAROFPLENTY:
				if (!devCards.get(j).isEnabled())
					yOP--;
				break;
			case MONOPOLY:
				if (!devCards.get(j).isEnabled())
					mono--;
				break;
			case KNIGHT:
				if (!devCards.get(j).isEnabled())
					sol--;
				break;
			case ROADBUILDING:
				if (!devCards.get(j).isEnabled())
					rB--;
				break;
			case MONUMENT:
				if (!devCards.get(j).isEnabled())
					monu--;
				break;
			}
		}
		if (yOP != 0 || mono != 0 || sol != 0 || rB != 0 || monu != 0)
			return false;
		return true;
	}

	/**
	 * @return Total: wood + brick + sheep + wheat + ore
	 */
	public int getHandSize() {
		return getWood() + getBrick() + getSheep() + getWheat() + getOre();
	}

	/**
	 * @param type
	 *            the type of resource to add
	 * @param num
	 *            the number of resource to be added
	 * @post hand now has addition resources of type and num
	 */
	public void receiveResource(ResourceType type, Integer num) {
		switch (type) {
		case WOOD:
			wood += num;
			return;
		case BRICK:
			brick += num;
			return;
		case SHEEP:
			sheep += num;
			return;
		case WHEAT:
			wheat += num;
			return;
		case ORE:
			ore += num;
			return;
		}
		Log.error("Hand is broken");
		assert false;
	}

	/**
	 * @param type
	 *            the type of resource to take
	 * @param num
	 *            the number of resource to be taken
	 * @throws NoRemainingResourceException
	 * @post hand now has less resources of type and num
	 */
	public void sendResource(ResourceType type, Integer num) throws NoRemainingResourceException {
		if (!hasResource(type, num)) {
			throw new NoRemainingResourceException();
		}
		switch (type) {
		case WOOD:
			wood -= num;
			return;
		case BRICK:
			brick -= num;
			return;
		case SHEEP:
			sheep -= num;
			return;
		case WHEAT:
			wheat -= num;
			return;
		case ORE:
			ore -= num;
			return;
		}
	}

	public Integer getResourceAmount(ResourceType type) {
		switch (type) {
		case WOOD:
			return wood;
		case BRICK:
			return brick;
		case WHEAT:
			return wheat;
		case SHEEP:
			return sheep;
		case ORE:
			return ore;
		}
		Log.error("Hand is broken");
		assert false;
		return null;
	}

	/**
	 * @param type
	 *            the type of resource to checked
	 * @param num
	 *            the number of resource to be checked
	 * @return Boolean Indicating if the player has the resources of type and
	 *         nun
	 */
	public Boolean hasResource(ResourceType type, Integer num) {
		switch (type) {
		case WOOD:
			if (wood >= num) {
				return true;
			}
			return false;
		case BRICK:
			if (brick >= num) {
				return true;
			}
			return false;
		case SHEEP:
			if (sheep >= num) {
				return true;
			}
			return false;
		case WHEAT:
			if (wheat >= num) {
				return true;
			}
			return false;
		case ORE:
			if (ore >= num) {
				return true;
			}
			return false;
		}
		Log.error("Hand is broken");
		assert false;
		return null;
	}

	public ResourceType drawRandomResourceCard() throws NoRemainingResourceException {
		if (getHandSize() == 0)
			throw new NoRemainingResourceException();
		Random generator = new Random();
		int r = generator.nextInt(getHandSize()) + 1;
		int k = 0;
		for (int i = 0; i < wood; i++) {
			k++;
			if (k == r) {
				wood--;
				return ResourceType.WOOD;
			}
		}
		for (int i = 0; i < brick; i++) {
			k++;
			if (k == r) {
				brick--;
				return ResourceType.BRICK;
			}
		}
		for (int i = 0; i < wheat; i++) {
			k++;
			if (k == r) {
				wheat--;
				return ResourceType.WHEAT;
			}
		}
		for (int i = 0; i < sheep; i++) {
			k++;
			if (k == r) {
				sheep--;
				return ResourceType.SHEEP;
			}
		}
		for (int i = 0; i < ore; i++) {
			k++;
			if (k == r) {
				ore--;
				return ResourceType.ORE;
			}
		}
		Log.error("Hand is broken");
		assert false;
		return null;
	}

	/**
	 * @return Boolean indicating if player has resources for road
	 */
	public Boolean hasRoadCost() {
		if (!hasResource(ResourceType.WOOD, 1))
			return false;
		if (!hasResource(ResourceType.BRICK, 1))
			return false;
		return true;
	}

	/**
	 * @return Boolean indicating if player has resources for settlement
	 */
	public Boolean hasSettlementCost() {
		if (!hasResource(ResourceType.WOOD, 1))
			return false;
		if (!hasResource(ResourceType.BRICK, 1))
			return false;
		if (!hasResource(ResourceType.SHEEP, 1))
			return false;
		if (!hasResource(ResourceType.WHEAT, 1))
			return false;
		return true;
	}

	/**
	 * @return Boolean indicating if player has resources for city
	 */
	public Boolean hasCityCost() {
		if (!hasResource(ResourceType.WHEAT, 2))
			return false;
		if (!hasResource(ResourceType.ORE, 3))
			return false;
		return true;
	}

	/**
	 * @return Boolean indicating if player has resources for development
	 */
	public Boolean hasDevelopmentCost() {
		if (!hasResource(ResourceType.SHEEP, 1))
			return false;
		if (!hasResource(ResourceType.WHEAT, 1))
			return false;
		if (!hasResource(ResourceType.ORE, 1))
			return false;
		return true;
	}

	/**
	 * @return true if bank hand has more than 1 Dev card
	 */
	public Boolean hasDevCard() {
		return (devCards.size() > 0);
	}

	public int getNumberOfDevCards() {
		return devCards.size();
	}


	/**
	 * @return the devCards
	 */
	public ArrayList<DevCard> getDevCards() {
		return devCards;
	}

	public Boolean canDiscardCard() {
		return (getHandSize() > 7);
	}

	public Boolean hasCards(Map<ResourceType, Integer> resourceList) {

		if (resourceList == null)
			return false;
		Integer w = resourceList.get(ResourceType.WOOD);
		Integer b = resourceList.get(ResourceType.BRICK);
		Integer s = resourceList.get(ResourceType.SHEEP);
		Integer wh = resourceList.get(ResourceType.WHEAT);
		Integer o = resourceList.get(ResourceType.ORE);
		if (w == null || b == null || s == null || wh == null || o == null)
			return false;

		if (getWood() < w)
			return false;
		if (getBrick() < b)
			return false;
		if (getSheep() < s)
			return false;
		if (getWheat() < wh)
			return false;
		if (getOre() < o)
			return false;
		return true;
	}

}