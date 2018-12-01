package model;

public enum STATUS {
	PENDING, ONTRANSIT, DELAYED, DELIVERED;
	
	public String toString() {
		switch(this)
		{
		case PENDING: return "PENDING";
		case ONTRANSIT: return "ON TRANSIT";
		case DELAYED: return "DELAYED";
		case DELIVERED: return "DELIVERED";
			
		default: return "Invalid";
		}
	}
}
