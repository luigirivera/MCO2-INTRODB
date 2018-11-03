package view;

public enum PLACEHOLDER {
	USERNAME, PASSWORD, EMAIL, NUMBER;
	
	public String toString() {
		switch(this)
		{
		case USERNAME: return "Username";
		case PASSWORD: return "Password";
		case EMAIL: return "Email Address";
		case NUMBER: return "Phone Number";
		default: return "Invalid";
		}
	}
}
