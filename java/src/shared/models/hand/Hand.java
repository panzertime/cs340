
package shared.models.hand;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;

import shared.models.exceptions.BadJSONException;
import shared.models.hand.development.DevCard;
import shared.models.hand.development.Knight;
import shared.models.hand.development.Monopoly;
import shared.models.hand.development.RoadBuilding;
import shared.models.hand.development.Monument;
import shared.models.hand.development.YearOfPlenty;
import shared.models.hand.exceptions.BadResourceTypeException;
import shared.models.hand.exceptions.NoRemainingResourceException;
import shared.models.hand.exceptions.ResourceException;

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
	 * @param wood
	 * @param brick
	 * @param sheep
	 * @param wheat
	 * @param ore
	 * @throws BadJSONException 
	 */
	public Hand(JSONObject resourceList, JSONObject deckList) throws BadJSONException {
		if (resourceList == null || deckList == null) throw new BadJSONException(); 
		Integer wood = (Integer) resourceList.get("wood");
		Integer brick = (Integer) resourceList.get("brick");
		Integer sheep = (Integer) resourceList.get("sheep");
		Integer wheat = (Integer) resourceList.get("wheat");
		Integer ore = (Integer) resourceList.get("ore");
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null) throw new BadJSONException(); 
		this.wood = wood;
		this.brick = brick;
		this.sheep = sheep;
		this.wheat = wheat;
		this.ore = ore;
		devCards = new ArrayList<DevCard>();
		Integer yOP = (Integer) deckList.get("yearOfPlenty");
		Integer mono = (Integer) deckList.get("monopoly");
		Integer sol = (Integer) deckList.get("soldier");
		Integer rB = (Integer) deckList.get("roadBuilding");
		Integer monu = (Integer) deckList.get("monument");
		if (yOP == null || mono == null || sol == null || rB == null || monu == null) throw new BadJSONException(); 
		for (int i = 0; i < yOP; i++) {
			DevCard card = new YearOfPlenty();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < mono; i++) {
			DevCard card = new Monopoly();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < sol; i++) {
			DevCard card = new Knight();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < rB; i++) {
			DevCard card = new RoadBuilding();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < monu; i++) {
			DevCard card = new Monument();
			card.setEnabled(false);
			devCards.add(card);
		}

	}

	public Hand(JSONObject resourceList, JSONObject oldDevList, JSONObject newDevList) throws BadJSONException {
		if (resourceList == null || oldDevList == null || newDevList == null) throw new BadJSONException(); 
		Integer wood = (Integer) resourceList.get("wood");
		Integer brick = (Integer) resourceList.get("brick");
		Integer sheep = (Integer) resourceList.get("sheep");
		Integer wheat = (Integer) resourceList.get("wheat");
		Integer ore = (Integer) resourceList.get("ore");
		if (wood == null || brick == null || sheep == null || wheat == null || ore == null) throw new BadJSONException(); 
		this.wood = wood;
		this.brick = brick;
		this.sheep = sheep;
		this.wheat = wheat;
		this.ore = ore;
		devCards = new ArrayList<DevCard>();

		Integer yOP = (Integer) oldDevList.get("yearOfPlenty");
		Integer mono = (Integer) oldDevList.get("monopoly");
		Integer sol = (Integer) oldDevList.get("soldier");
		Integer rB = (Integer) oldDevList.get("roadBuilding");
		Integer monu = (Integer) oldDevList.get("monument");
		if (yOP == null || mono == null || sol == null || rB == null || monu == null) throw new BadJSONException(); 
		for (int i = 0; i < yOP; i++) {
			DevCard card = new YearOfPlenty();
			card.setEnabled(true);
			devCards.add(card);
		}
		for (int i = 0; i < mono; i++) {
			DevCard card = new Monopoly();
			card.setEnabled(true);
			devCards.add(card);
		}
		for (int i = 0; i < sol; i++) {
			DevCard card = new Knight();
			card.setEnabled(true);
			devCards.add(card);
		}
		for (int i = 0; i < rB; i++) {
			DevCard card = new RoadBuilding();
			card.setEnabled(true);
			devCards.add(card);
		}
		for (int i = 0; i < monu; i++) {
			DevCard card = new Monument();
			card.setEnabled(true);
			devCards.add(card);
		}

		 yOP = (Integer) newDevList.get("yearOfPlenty");
		 mono = (Integer) newDevList.get("monopoly");
		 sol = (Integer) newDevList.get("soldier");
		 rB = (Integer) newDevList.get("roadBuilding");
		 monu = (Integer) newDevList.get("monument");
		if (yOP == null || mono == null || sol == null || rB == null || monu == null) throw new BadJSONException(); 
		for (int i = 0; i < yOP; i++) {
			DevCard card = new YearOfPlenty();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < mono; i++) {
			DevCard card = new Monopoly();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < sol; i++) {
			DevCard card = new Knight();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < rB; i++) {
			DevCard card = new RoadBuilding();
			card.setEnabled(false);
			devCards.add(card);
		}
		for (int i = 0; i < monu; i++) {
			DevCard card = new Monument();
			card.setEnabled(false);
			devCards.add(card);
		}

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
	 * @throws ResourceException
	 * @post hand now has addition resources of type and num
	 */
	public void receiveResource(ResourceType type, Integer num) throws ResourceException {
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
		throw new BadResourceTypeException();
	}

	/**
	 * @param type
	 *            the type of resource to take
	 * @param num
	 *            the number of resource to be taken
	 * @throws ResourceException
	 * @post hand now has less resources of type and num
	 */
	public void sendResource(ResourceType type, Integer num) throws ResourceException {
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

	public int getResourceAmount(ResourceType type) throws BadResourceTypeException {
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
		throw new BadResourceTypeException();
	}

	/**
	 * @param type
	 *            the type of resource to checked
	 * @param num
	 *            the number of resource to be checked
	 * @return Boolean Indicating if the player has the resources of type and
	 *         nun
	 * @throws BadResourceTypeException
	 */
	public Boolean hasResource(ResourceType type, Integer num) throws BadResourceTypeException {
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
		throw new BadResourceTypeException();
	}

	public ResourceType drawRandomResourceCard() throws ResourceException {
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
		throw new ResourceException();
	}

	/**
	 * @return Boolean indicating if player has resources for road
	 */
	public Boolean hasRoadCost() {
		try {
			if (!hasResource(ResourceType.WOOD, 1))
				return false;
			if (!hasResource(ResourceType.BRICK, 1))
				return false;
			return true;
		} catch (ResourceException e) {
			// TODO add log "fix enums in " print stackTrace
		}
		return false;
	}

	/**
	 * @return Boolean indicating if player has resources for settlement
	 */
	public Boolean hasSettlementCost() {
		try {
			if (!hasResource(ResourceType.WOOD, 1))
				return false;
			if (!hasResource(ResourceType.BRICK, 1))
				return false;
			if (!hasResource(ResourceType.SHEEP, 1))
				return false;
			if (!hasResource(ResourceType.WHEAT, 1))
				return false;
			return true;
		} catch (ResourceException e) {
			// TODO add log "fix enums in " print stackTrace
		}
		return false;
	}

	/**
	 * @return Boolean indicating if player has resources for city
	 */
	public Boolean hasCityCost() {
		try {
			if (!hasResource(ResourceType.WHEAT, 2))
				return false;
			if (!hasResource(ResourceType.ORE, 3))
				return false;
			return true;
		} catch (ResourceException e) {
			// TODO add log "fix enums in " print stackTrace
		}
		return false;
	}

	/**
	 * @return Boolean indicating if player has resources for development
	 */
	public Boolean hasDevelopmentCost() {
		try {
			if (!hasResource(ResourceType.SHEEP, 1))
				return false;
			if (!hasResource(ResourceType.WHEAT, 1))
				return false;
			if (!hasResource(ResourceType.ORE, 1))
				return false;
			return true;
		} catch (ResourceException e) {
			// TODO add log "fix enums in " print stackTrace
		}
		return false;
	}

	/**
	 * @return true if bank hand has >=1 Dev cards
	 */
	public Boolean hasDevCard() {
		return (devCards.size() > 0);
	}

	public int getNumberOfDevCards() {
		return devCards.size();
	}

	// public void shuffleDevCards()
	// {
	// Random generator = new Random();
	// ArrayList<DevCard> shuffledList = new ArrayList<DevCard>();
	// while (devCards.size() > 0)
	// {
	// int r = generator.nextInt(devCards.size());
	// shuffledList.add(devCards.get(r));
	// devCards.remove(r);
	// }
	// devCards = shuffledList;
	// }

	/**
	 * @return the devCards
	 */
	public ArrayList<DevCard> getDevCards() {
		return devCards;
	}

	public Boolean canDiscardCard() {
		return (getHandSize() > 7);
	}

	public Boolean hasCards(Map<String, Object> resourceList) throws BadJSONException {
		
		if (resourceList == null || resourceList == null) throw new BadJSONException(); 
		Integer w = (Integer) resourceList.get("wood");
		Integer b = (Integer) resourceList.get("brick");
		Integer s = (Integer) resourceList.get("sheep");
		Integer wh = (Integer) resourceList.get("wheat");
		Integer o = (Integer) resourceList.get("ore");
		if (w == null || b == null || s == null || wh == null || o == null) throw new BadJSONException(); 
		
		if (getWood() != w)
			return false;
		if (getBrick() != b)
			return false;
		if (getSheep() != s)
			return false;
		if (getWheat() != wh)
			return false;
		if (getOre() != o)
			return false;
		return true;
	}

}