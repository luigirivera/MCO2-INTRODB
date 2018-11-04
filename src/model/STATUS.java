package model;

public enum STATUS {
	ONTRANSIT, DELAYED, PENDING, DELIVERED;
	
	public String toString()
	{
		switch(this)
		{
		case ONTRANSIT: return "ON TRANSIT";
		case DELAYED: return "DELAYED";
		case PENDING: return "PENDING";
		case DELIVERED: return "DELIVERED";
		default: return "Invalid";
		}
	}
}
