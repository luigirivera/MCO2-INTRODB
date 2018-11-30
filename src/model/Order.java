package model;

import java.sql.Date;

import services.OrderService;

public class Order {
	private int id;
	private int userID;
	private int quantity;
	private Date orderDate;
	private int addressID;
	private int cardID;
	private int bankID;
	
	private OrderService orderservice;
	
	public static final String TABLE = "consumerorder";
	public static final String COL_ID = "orderid";
	public static final String COL_USERID = "userID";
	public static final String COL_QUANTITY = "quantity";
	public static final String COL_CREATION = "creationdate";
	public static final String COL_ADDRESS = "addressID";
	public static final String COL_CARD = "cardID";
	public static final String COL_BANK = "bankID";
	
	public Order()
	{
		orderservice = new OrderService();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	public int getBankID() {
		return bankID;
	}
	public void setBankID(int bankID) {
		this.bankID = bankID;
	}
	
	public int createOrder(int userID)
	{
		return orderservice.createOrder(userID, addressID, cardID, bankID);
	}

	public void addtoOrder(int productID, int quantity2) {
		orderservice.addtoOrder(id, productID, quantity2);
	}
	
	public int getOrderQuantity()
	{
		return orderservice.getOrderQuantity(id);
	}
	
	public void setOrderTotalQuantity(int total)
	{
		this.quantity = total;
		
		orderservice.setOrderTotalQuantity(total, id);
	}

	public void subtractStock(int i, int sold, int productID) {
		orderservice.subtractStock(i, sold, productID);
		
	}
}
