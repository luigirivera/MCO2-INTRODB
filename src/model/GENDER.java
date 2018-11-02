package model;

public enum GENDER {
	M, F;
	
	public String toString()
	{
		switch (this)
		{
			case M: return "M";
			case F: return "F";
			default: return "I";
		}
	}
}
