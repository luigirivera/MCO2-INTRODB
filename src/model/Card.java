package model;

import java.sql.Date;

public class Card {
	private int cardID;
	private int userID;
	private long cardNumber;
	private Date expiry;
	private int cvc;
	
	public static final String TABLE = "cards";
	public static final String COL_CARDSID = "cardsid";
	public static final String COL_CARDNUMBER = "cardnumber";
	public static final String COL_EXPIRY = "expiry";
	public static final String COL_cvc = "cvc";
	
	
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getExpiry() {
		return expiry;
	}
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	public int getCvc() {
		return cvc;
	}
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	
	
}
