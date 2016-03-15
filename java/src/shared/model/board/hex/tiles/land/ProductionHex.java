package shared.model.board.hex.tiles.land;

import org.json.simple.JSONObject;

import shared.model.board.hex.HexLocation;
import shared.model.board.vertex.Vertex;
import shared.model.hand.exceptions.NoRemainingResourceException;

public abstract class ProductionHex extends LandHex {

	protected Integer productionNumber;

	public ProductionHex(HexLocation hexLocation, Integer productionNumber) throws IllegalArgumentException {
		super(hexLocation);
		if (productionNumber == null)
			throw new IllegalArgumentException();
		if (productionNumber < 2 || productionNumber > 12)
			throw new IllegalArgumentException();
		this.productionNumber = productionNumber;
	}

	public void produce() throws NoRemainingResourceException {
		for (Vertex vert : verts)
			vert.produce(hexType.getResourceType());
	}

	/**
	 * @return the productionNumber
	 */
	public Integer getProductionNumber() {
		return productionNumber;
	}

	@Override
	public boolean equals(JSONObject jsonHex) {
		if (!super.equals(jsonHex))
			return false;
		Integer number = ((Long) jsonHex.get("number")).intValue();
		if (productionNumber != number)
			return false;
		return true;
	}
}
