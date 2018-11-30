package model;

import java.sql.Date;
import java.util.ArrayList;

import services.CardsService;

public class Card {
	private int cardID;
	private int userID;
	private long cardNumber;
	private Date expiry;
	private boolean isCVCSaved;
	
	private CardsService cardservice;
	
	public static final String TABLE = "card";
	public static final String COL_USERID = "userid";
	public static final String COL_CARDSID = "cardsid";
	public static final String COL_CARDNUMBER = "cardnumber";
	public static final String COL_EXPIRY = "expiry";
	public static final String COL_CVC = "iscvcsaved";
	
	public Card()
	{
		cardservice = new CardsService();
	}
	
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
	public boolean getCvc() {
		return isCVCSaved;
	}
	public void setCvc(boolean cvc) {
		this.isCVCSaved = cvc;
	}
	
	public void saveCard()
	{
		cardservice.saveCard(userID, cardNumber, isCVCSaved, expiry);
	}
	
	public boolean doesCardExist()
	{
		return cardservice.doesCardExist(userID, cardNumber);
	}
	
	public ArrayList<Card> getCardsOfUser()
	{
		return cardservice.getCardsOfUser(userID);
	}
	
	public void delete()
	{
		cardservice.delete(userID, cardID);
	}
}
