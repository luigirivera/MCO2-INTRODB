package view;

public enum PLACEHOLDER {
	USERNAME, PASSWORD, EMAIL, NUMBER,
	
	CCNUM, CVC,
	
	LINE1, LINE2, CITY, PROV, ZIP,
	
	BANK, ACCNUM,
	
	ADD, SAVE, CANCEL;
	
	public String toString() {
		switch(this)
		{
		case USERNAME: return "Username";
		case PASSWORD: return "Password";
		case EMAIL: return "Email Address";
		case NUMBER: return "Phone Number";
		case CCNUM: return "Card Number";
		case CVC: return "CVC";
		case LINE1: return "Address Line 1";
		case LINE2: return "Address Line 2";
		case CITY: return "City";
		case PROV: return "Province";
		case ZIP: return "Zip";
		case BANK: return "Bank";
		case ACCNUM: return "Account Number";
		case ADD: return "+ Add";
		case SAVE: return "Save";
		case CANCEL: return "Cancel";
		default: return "Invalid";
		}
	}
}
