package model;

import java.util.ArrayList;

import services.ProductsService;

public class Product {

	private int productID;
	private int sellerID;
	private String name;
	private String seller;
	private String category;
	private String brand;
	private String description;
	private long favorites;
	private long stock;
	private long sold;
	private double price;
	private double discount;
	private double shipping;
	
	private ProductsService productservice;
	
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
	
	public Product()
	{
		productservice = new ProductsService();
	}
	
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
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public long getFavorites() {
		return favorites;
	}

	public void setFavorites(long favorites) {
		this.favorites = favorites;
	}

	public boolean productExists() {
		return productservice.doesProductExist(name, category, brand, description, stock, price, discount, shipping, sellerID);
	}

	public void addProduct() {
		productservice.addProduct(name, category, brand, description, stock, price, discount, shipping, sellerID);
		
	}
	
	public void updateProduct() {
		productservice.updateProduct(name, category, brand, description, stock, price, discount, shipping, productID);
	}
	
	public ArrayList<Product> getProductsOfUser()
	{
		return productservice.getProductsOfSeller(sellerID);
	}

	public void delete() {
		productservice.deleteProduct(productID);
		
	}

	public ArrayList<Product> getAllProducts() {
		return productservice.getAllProducts();
	}
	
	public ArrayList<Product> getProducts(String whereClause) {
		return productservice.getProducts(whereClause);
	}

	public void favorite(int id) {
		productservice.favorite(id, productID);
		
	}

	public void unfavorite(int id) {
		productservice.unfavorite(id, productID);
		
	}

	public boolean checkFavorite(int id) {
		return productservice.checkfavorite(id, productID);
	}
	
	public void addToCart(int id, int quantity)
	{
		productservice.addToCart(id, productID, quantity);
	}
	
	
}
