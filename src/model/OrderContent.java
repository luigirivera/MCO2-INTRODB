package model;

import java.sql.Date;
import java.util.ArrayList;

import services.OrderService;

public class OrderContent {
	
	private int orderid;
	private String name;
	private String category;
	private String brand;
	private String seller;
	private String buyer;
	private int productID;
	private int quantity;
	private String status;
	private Date delivery;
	private Date orderdate;
	private double total;
	
	private OrderService orderservice;
	
	public static final String TABLE = "ordercontent";
	public static final String COL_ORDER = "orderid";
	public static final String COL_PRODUCT = "productID";
	public static final String COL_QUANTITY = "quantity";
	public static final String COL_STATUS = "status";
	public static final String COL_DELIVERYDATE = "deliverydate";
	public static final String COL_TOTAL = "total";
	
	public OrderContent()
	{
		orderservice = new OrderService();
	}
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDelivery() {
		return delivery;
	}
	public void setDelivery(Date delivery) {
		this.delivery = delivery;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public ArrayList<OrderContent> getOrderDetails(String status)
	{
		return orderservice.getOrderDetails(orderid, status);
	}
	
	public ArrayList<OrderContent> getSalesOfSeller(int sellerID, String whereClause)
	{
		return orderservice.getSalesOfSeller(sellerID, whereClause);
	}
	
	public void updateStatus(String status)
	{
		orderservice.updateStatus(orderid, productID, status);
	}
}
