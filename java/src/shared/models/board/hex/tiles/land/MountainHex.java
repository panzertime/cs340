package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexType;

public class MountainHex extends ProductionHex {

	public MountainHex(Integer productionNumber) throws BadProductionNumberException {
		super(productionNumber);
		hexType = HexType.ORE;
	}

}
