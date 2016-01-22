package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexType;

public class ClayHex extends ProductionHex {

	public ClayHex(Integer productionNumber) throws BadProductionNumberException {
		super(productionNumber);
		hexType = HexType.BRICK;
	}

}
