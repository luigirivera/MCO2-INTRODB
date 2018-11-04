package view;

public enum PLACEHOLDER {
	USERNAME, PASSWORD, EMAIL, NUMBER,
	
	CCNUM, CVC;
	
	public String toString() {
		switch(this)
		{
		case USERNAME: return "Username";
		case PASSWORD: return "Password";
		case EMAIL: return "Email Address";
		case NUMBER: return "Phone Number";
		case CCNUM: return "Card Number";
		case CVC: return "CVC";
		default: return "Invalid";
		}
	}
}
