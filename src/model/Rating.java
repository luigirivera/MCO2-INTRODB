package model;

import java.sql.Date;

import services.ProductsService;

public class Rating {
	private int id;
	private int user;
	private int product;
	private int rating;
	private String comment;
	private Date ratingdate;
	
	private ProductsService productservice;
	
	public static final String TABLE = "rating";
	public static final String COL_ID = "id";
	public static final String COL_USER = "user";
	public static final String COL_PRODUCT = "product";
	public static final String COL_RATING = "rating";
	public static final String COL_COMMENT = "comment";
	public static final String COL_DATE = "ratingdate";
	
	public Rating()
	{
		productservice = new ProductsService();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getRatingdate() {
		return ratingdate;
	}
	public void setRatingdate(Date ratingdate) {
		this.ratingdate = ratingdate;
	}
	
	
}
