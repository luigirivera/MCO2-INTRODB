package model;

public enum GENDER {
	M, F;
	
	public String toString()
	{
		switch (this)
		{
			case M: return "MALE";
			case F: return "FEMALE";
			default: return "Invalid";
		}
	}
}
