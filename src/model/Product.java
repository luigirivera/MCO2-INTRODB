package model;

public class Product {

	private int productID;
	private int sellerID;
	private String name;
	private String category;
	private String brand;
	private String description;
	private long stock;
	private long sold;
	private double price;
	private double discount;
	private double shipping;
	
	public static final String TABLE = "products";
	public static final String COL_ID = "id";
	public static final String COL_SELLERID = "seller";
	public static final String COL_NAME = "name";
	public static final String COL_CATEGORY = "category";
	public static final String COL_BRAND = "brand";
	public static final String COL_DESC = "description";
	public static final String COL_STOCK = "stock";
	public static final String COL_SOLD = "sold";
	public static final String COL_PRICE = "price";
	public static final String COL_DISC = "discount";
	public static final String COL_SHIP = "shipping";
	
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getSellerID() {
		return sellerID;
	}
	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getStock() {
		return stock;
	}
	public void setStock(long stock) {
		this.stock = stock;
	}
	public long getSold() {
		return sold;
	}
	public void setSold(long sold) {
		this.sold = sold;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getShipping() {
		return shipping;
	}
	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
	
	
}
