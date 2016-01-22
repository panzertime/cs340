package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexType;

public class ForestHex extends ProductionHex {

	public ForestHex(Integer productionNumber) throws BadProductionNumberException {
		super(productionNumber);
		hexType = HexType.WOOD;
	}

}
