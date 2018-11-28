package model;

import java.util.ArrayList;

import services.CartService;

public class Cart {
	private int id;
	private int productID;
	private int userID;
	private String name;
	private int quantity;
	private double discount;
	private double price;
	private double shipping;
	private double total;
	
	private CartService cartservice;
	
	public Cart() {
		cartservice = new CartService();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getShipping() {
		return shipping;
	}
	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public ArrayList<Cart> getCartOfUser()
	{
		return cartservice.getCartOfUser(userID);
	}
	
	public void delete()
	{
		cartservice.delete(id);
	}
	
	public int getProductStock(int id)
	{
		return cartservice.getProductStock(id);
	}

	public void updateQuantity(int newquantity) {
		cartservice.updateQuantity(newquantity, id);
		
	}
}
