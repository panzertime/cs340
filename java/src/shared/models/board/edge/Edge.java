package shared.models.board.edge;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;

public class Edge {
	
	private Hex hex0;
	private Hex hex1;
	private EdgeLocation edgeLocation;
	private Road road;

	/**
	 * @pre hex is either hex0 or hex1;
	 * @param hex one of two possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Other hex attached to this
	 */
	public Hex getOtherHex(Hex hex) throws HexNotLinkedException {
		if (hex == hex0) {
			return hex1;
		} else if (hex == hex1) {
			return hex0;
		}
		throw new HexNotLinkedException();
	}

	/**
	 * @return the edgeLocation
	 */
	public EdgeLocation getEdgeLocation() {
		return edgeLocation;
	}

	/**
	 * @param edgeLocation the edgeLocation to set
	 */
	public void setEdgeLocation(EdgeLocation edgeLocation) {
		this.edgeLocation = edgeLocation.getNormalizedLocation();
	}

	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}

	/**
	 * @param road the road to set
	 * @throws PositionTakenException 
	 */
	public void setRoad(Road road) throws PositionTakenException {
		if (this.road != null)
			throw new PositionTakenException();
		this.road = road;
	}
}
