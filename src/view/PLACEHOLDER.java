package view;

public enum PLACEHOLDER {
	USERNAME, PASSWORD, EMAIL, NUMBER, REGISTERDATE, LASTLOGIN,
	
	CCNUM, CVC,
	
	LINE1, LINE2, CITY, PROV, ZIP,
	
	BANK, ACCNUM,
	
	NAME, CATEGORY, BRAND, SELLER, DESCRIPTION, FAVORITES, STOCK, SOLD, PRICE, DISCOUNT, SHIPPING,
	
	ADD, SAVE, CANCEL, DELETE, UNLOCK, RATE, CART, FAVE, UNFAVE, FOLLOW, UNFOLLOW,
	
	QUANTITY,
	
	PRICELOW, PRICEHIGH,
	
	PRICELTH, PRICEHTL, NAMEATZ, NAMEZTA,
	
	LOCKED, DELETION, CONSUMER, CORPORATE;
	
	public String toString() {
		switch(this)
		{
		case USERNAME: return "Username";
		case PASSWORD: return "Password";
		case EMAIL: return "Email Address";
		case NUMBER: return "Phone Number";
		case REGISTERDATE: return "Register Date";
		case LASTLOGIN: return "Last Login Date";
		
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
		case SELLER: return "Seller";
		case DESCRIPTION: return "Description";
		case FAVORITES: return "Favorites";
		case STOCK: return "Stock";
		case SOLD: return "Sold";
		case PRICE: return "Price";
		case DISCOUNT: return "Discount Rate";
		case SHIPPING: return "Shipping";
		
		case ADD: return "+ Add";
		case SAVE: return "Save";
		case CANCEL: return "Cancel";
		case DELETE: return "Delete";
		case UNLOCK: return "Unlock";
		case RATE: return "Rate";
		case CART: return "Add to Cart"; 
		case FAVE: return "Favorite";
		case UNFAVE: return "Unfavorite";
		case FOLLOW: return "Follow";
		case UNFOLLOW: return "Unfollow";
		
		case QUANTITY: return "Quantity";
			
		case PRICELOW: return "Low";
		case PRICEHIGH: return "High";
		
		case PRICELTH: return "Price: Low to High";
		case PRICEHTL: return "Price: High to Low";
		case NAMEATZ: return "Name: A to Z";
		case NAMEZTA: return "Name: Z to A";
		
		case LOCKED: return "Locked";
		case DELETION: return "For Deletion";
		case CONSUMER: return "Consumer";
		case CORPORATE: return "Corporate";
		default: return "Invalid";
		}
	}
}
