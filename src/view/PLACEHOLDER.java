package view;

public enum PLACEHOLDER {
	USERNAME, PASSWORD, EMAIL, NUMBER,
	
	CCNUM, CVC,
	
	LINE1, LINE2, CITY, PROV, ZIP,
	
	BANK, ACCNUM,
	
	NAME, CATEGORY, BRAND, DESCRIPTION, STOCK, SOLD, PRICE, DISCOUNT, SHIPPING,
	
	ADD, SAVE, CANCEL, DELETE;
	
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
		case NAME: return "Name";
		case CATEGORY: return "Category";
		case BRAND: return "Brand";
		case DESCRIPTION: return "Description";
		case STOCK: return "Stock";
		case SOLD: return "Sold";
		case PRICE: return "Price";
		case DISCOUNT: return "Discount";
		case SHIPPING: return "Shipping";
		case ADD: return "+ Add";
		case SAVE: return "Save";
		case CANCEL: return "Cancel";
		case DELETE: return "Delete";
		default: return "Invalid";
		}
	}
}
