package shared.models.board.hex;

import org.json.simple.JSONObject;

import shared.models.board.edge.EdgeDirection;
import shared.models.exceptions.BadJSONException;

/**
 * Represents the location of a hex on a hex map
 */
public class HexLocation
{
	public HexLocation(JSONObject json) throws BadJSONException {
		Object x = json.get("x");
		if (x == null)
			throw new BadJSONException();
		Object y = json.get("y");
		if (y == null)
			throw new BadJSONException();
		setX(((Long)x).intValue());
		setY(((Long)y).intValue());
	}
	
	public HexLocation(Integer x, Integer y) {
		setX(x);
		setY(y);
	}
	
	private int x;
	private int y;
	
	public HexLocation(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public int getX()
	{
		return x;
	}
	
	private void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	private void setY(int y)
	{
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "HexLocation [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		HexLocation other = (HexLocation)obj;
		if(x != other.x)
			return false;
		if(y != other.y)
			return false;
		return true;
	}
	
	public HexLocation getNeighborLoc(EdgeDirection dir)
	{
		switch (dir)
		{
			case NorthWest:
				return new HexLocation(x - 1, y);
			case North:
				return new HexLocation(x, y - 1);
			case NorthEast:
				return new HexLocation(x + 1, y - 1);
			case SouthWest:
				return new HexLocation(x - 1, y + 1);
			case South:
				return new HexLocation(x, y + 1);
			case SouthEast:
				return new HexLocation(x + 1, y);
			default:
				assert false;
				return null;
		}
	}
	
}

