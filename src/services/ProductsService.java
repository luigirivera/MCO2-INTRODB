package services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

import database.DatabaseConnection;
import model.CartContent;
import model.Favorite;
import model.Order;
import model.OrderContent;
import model.Product;
import model.Rating;
import model.User;

public class ProductsService {
	
	public Product getProductFromID(int id) {
		Product product = null;
		
		String query = "SELECT P." + Product.COL_ID + ", P."
								 + Product.COL_SELLERID + ", P."
								 + Product.COL_NAME + ", P."
								 + Product.COL_CATEGORY + ", P."
								 + Product.COL_BRAND + ", U."
								 + User.COL_USERNAME + ", P."
								 + Product.COL_DESC + ", P."
								 + Product.COL_STOCK + ", P."
								 + Product.COL_SOLD + ", P."
								 + Product.COL_PRICE + ", P."
								 + Product.COL_DISC + ", P."
								 + Product.COL_SHIP + ", P."
								 + Product.COL_SHIPDUR + ", "
								 + "F.Favorites, R.rate FROM " + Product.TABLE + " AS P LEFT JOIN " + User.TABLE + " AS U ON U." + User.COL_ID + " = P." + Product.COL_SELLERID 
																						+ " LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", "
																						   + "COUNT(F." + Favorite.COL_PRODUCT
																						   + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
																						   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT + 
																	" LEFT JOIN (SELECT R." + Rating.COL_PRODUCT + 
																				", AVG(R." + Rating.COL_RATING + 
																				") AS rate FROM " + Rating.TABLE + " AS R GROUP BY R." + Rating.COL_PRODUCT + ") AS R "
																				+ " ON P." + Product.COL_ID + " = " + "R." + Rating.COL_PRODUCT
								 											   + " WHERE " + Product.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
		
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				product = toProduct(rs);
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] GET PRODUCTS DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] GET PRODUCTS FAILED");
			e.printStackTrace();
		}
		
		return product;
	}
	
	
	public void updateCartQuantity(int userid, int productID, int quantity) {
		String query = "UPDATE " + CartContent.TABLE + " SET " + CartContent.COL_QUANTITY + " = ? WHERE " + CartContent.COL_USER + " = ? AND " + CartContent.COL_PRODUCT + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, quantity);
			ps.setInt(2, userid);
			ps.setInt(3, productID);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[CART] QUANTITY UPDATE DONE");
		}catch(SQLException e) {
			System.out.println("[CART] QUANTITY UPDATE FAILED");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Rating> getRatings(int productID) {
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		
		String query = "SELECT R." + Rating.COL_ID + ", U."
								 + User.COL_USERNAME + ", U."
								 + User.COL_ID + ", R."
								 + Rating.COL_RATING + ", R."
								 + Rating.COL_COMMENT + ", R."
								 + Rating.COL_DATE + " FROM " + Rating.TABLE + " AS R, " + User.TABLE + " AS U WHERE R." + Rating.COL_USER + " = " + User.COL_ID + " AND R." + Rating.COL_PRODUCT + " = ?";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, productID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				ratings.add(toRating(rs));
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] GET PRODUCTS DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] GET PRODUCTS FAILED");
			e.printStackTrace();
		}
		
		return ratings;
	}
	
	private Rating toRating(ResultSet rs) throws SQLException{
		Rating r = new Rating();
		
		r.setId(rs.getInt(Rating.COL_ID));
		r.setBuyer(rs.getString(User.COL_USERNAME));
		r.setUser(rs.getInt(User.COL_ID));
		r.setRating(rs.getInt(Rating.COL_RATING));
		r.setComment(rs.getString(Rating.COL_COMMENT));
		r.setRatingdate(rs.getDate(Rating.COL_DATE));
		
		return r;
	}

	public boolean checkifincart(int userID, int productID) {
		boolean found = false;
		
		String query = "SELECT " + CartContent.COL_ID + " FROM " + CartContent.TABLE + " WHERE " + CartContent.COL_USER + " = ? AND " + CartContent.COL_PRODUCT + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setInt(2, productID);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] CHECK CART DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] CHECK CART FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	
	public int getCartQuantity(int userID, int productID) {
		int quantity = 0;
		
		String query = "SELECT " + CartContent.COL_QUANTITY + " FROM " + CartContent.TABLE + " WHERE " + CartContent.COL_USER + " = ? AND " + CartContent.COL_PRODUCT + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setInt(2, productID);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				quantity = rs.getInt(CartContent.COL_QUANTITY);
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] CHECK CART DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] CHECK CART FAILED");
			e.printStackTrace();
		}
		
		return quantity;
	}
	
	public void unrate(int id)
	{
		String query = "DELETE FROM " + Rating.TABLE + " WHERE " + Rating.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);

			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] UNRATE DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] UNRATE FAILED");
			e.printStackTrace();
		}
	}
	
	public void rate(int rating, String comment, int id, int product)
	{
		String query = "INSERT INTO " + Rating.TABLE + " (" + Rating.COL_USER + ", "
															+ Rating.COL_PRODUCT + ", "
															+ Rating.COL_RATING + ", "
															+ Rating.COL_COMMENT + ", "
															+ Rating.COL_DATE + ") VALUES(?,?,?,?,NOW())";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setInt(2, product);
			ps.setInt(3, rating);
			ps.setString(4, comment);

			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] RATE DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] RATE FAILED");
			e.printStackTrace();
		}
	}
	
	public void addToCart(int id, int product, int quantity) {
		String query = "INSERT INTO " + CartContent.TABLE + " (" + CartContent.COL_USER + ", "
				 												 + CartContent.COL_PRODUCT + ", "
				 												 + CartContent.COL_QUANTITY + ") VALUES(?,?,?)";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setInt(2, product);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] ADD TO CART DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] ADD TO CART FAILED");
			e.printStackTrace();
		}
	}
	
	public boolean checkfavorite(int id, int productID) {
		boolean found = false;
		
		String query = "SELECT COUNT(*) FROM " + Favorite.TABLE + " WHERE " + Favorite.COL_USER + " = ? AND " + Favorite.COL_PRODUCT + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setInt(2, productID);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() && rs.getInt("COUNT(*)") > 0)
				found = true;
				
			
			rs.close();
			ps.close();
			System.out.println("[PRODUCT] FAVE CHECK DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] FAVE CHECK FAILED");
			e.printStackTrace();
		}
		
		return found;
	}	
	
	public void unfavorite(int user, int product)
	{
		String query = "DELETE FROM " + Favorite.TABLE + " WHERE " + Favorite.COL_USER + " = ? AND " + Favorite.COL_PRODUCT + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, user);
			ps.setInt(2, product);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] UNFAVORITE DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] UNFAVORITE FAILED");
			e.printStackTrace();
		}
	}
	
	public void favorite(int user, int product)
	{
		String query = "INSERT INTO " + Favorite.TABLE + " (" + Favorite.COL_USER + ", "
															  + Favorite.COL_PRODUCT + ") VALUES(?,?)";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, user);
			ps.setInt(2, product);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] FAVORITE DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] FAVORITE FAILED");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Product> getProducts(String whereClause) {
		ArrayList<Product> products = new ArrayList<Product>();
		
		String query = "SELECT P." + Product.COL_ID + ", P."
								 + Product.COL_SELLERID + ", P."
								 + Product.COL_NAME + ", P."
								 + Product.COL_CATEGORY + ", P."
								 + Product.COL_BRAND + ", U."
								 + User.COL_USERNAME + ", P."
								 + Product.COL_DESC + ", P."
								 + Product.COL_STOCK + ", P."
								 + Product.COL_SOLD + ", P."
								 + Product.COL_PRICE + ", P."
								 + Product.COL_DISC + ", P."
								 + Product.COL_SHIP + ", P."
								 + Product.COL_SHIPDUR + ", "
								 + "F.Favorites, R.rate FROM " + Product.TABLE + " AS P LEFT JOIN " + User.TABLE + " AS U ON U." + User.COL_ID + " = P." + Product.COL_SELLERID 
																						+ " LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", "
																						   + "COUNT(F." + Favorite.COL_PRODUCT
																						   + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
																						   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT + 
																	" LEFT JOIN (SELECT R." + Rating.COL_PRODUCT + 
																				", AVG(R." + Rating.COL_RATING + 
																				") AS rate FROM " + Rating.TABLE + " AS R GROUP BY R." + Rating.COL_PRODUCT + ") AS R "
																				+ " ON P." + Product.COL_ID + " = " + "R." + Rating.COL_PRODUCT
								 											   + whereClause;
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
		
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				products.add(toProduct(rs));
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] GET PRODUCTS DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] GET PRODUCTS FAILED");
			e.printStackTrace();
		}
		
		return products;
	}
	
	public ArrayList<Product> getAllProducts()
	{
		ArrayList<Product> products = new ArrayList<Product>();
		
		String query = "SELECT P." + Product.COL_ID + ", P."
				 + Product.COL_SELLERID + ", P."
				 + Product.COL_NAME + ", P."
				 + Product.COL_CATEGORY + ", P."
				 + Product.COL_BRAND + ", U."
				 + User.COL_USERNAME + ", P."
				 + Product.COL_DESC + ", P."
				 + Product.COL_STOCK + ", P."
				 + Product.COL_SOLD + ", P."
				 + Product.COL_PRICE + ", P."
				 + Product.COL_DISC + ", P."
				 + Product.COL_SHIP + ", P."
				 + Product.COL_SHIPDUR + ", "
				 + "F.Favorites, R.rate FROM " + Product.TABLE + " AS P LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", "
				 																	   + "COUNT(F." + Favorite.COL_PRODUCT
				 																	   + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
				 																	   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT + 
				 												" LEFT JOIN (SELECT R." + Rating.COL_PRODUCT + 
				 															", AVG(R." + Rating.COL_RATING + 
				 															") AS rate FROM " + Rating.TABLE + " AS R GROUP BY R." + Rating.COL_PRODUCT + ") AS R "
				 															+ " ON P." + Product.COL_ID + " = " + "R." + Rating.COL_PRODUCT;
		System.out.println(query);
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				products.add(toProduct(rs));
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] GET PRODUCTS DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] GET PRODUCTS FAILED");
			e.printStackTrace();
		}
		
		return products;
	}
	
	public Product toProduct(ResultSet rs) throws SQLException{
		Product product = new Product();
		
		product.setProductID(rs.getInt(Product.COL_ID));
		product.setSellerID(rs.getInt(Product.COL_SELLERID));
		product.setName(rs.getString(Product.COL_NAME));
		product.setCategory(rs.getString(Product.COL_CATEGORY));
		product.setBrand(rs.getString(Product.COL_BRAND));
		product.setSeller(rs.getString(User.COL_USERNAME));
		product.setDescription(rs.getString(Product.COL_DESC));
		product.setStock(rs.getLong(Product.COL_STOCK));
		product.setSold(rs.getInt(Product.COL_SOLD));
		product.setPrice(rs.getDouble(Product.COL_PRICE));
		product.setDiscount(rs.getDouble(Product.COL_DISC));
		product.setShipping(rs.getDouble(Product.COL_SHIP));
		product.setShippingduration(rs.getInt(Product.COL_SHIPDUR));
		product.setFavorites(rs.getLong("Favorites"));
		product.setRating(rs.getDouble("rate"));
		
		return product;
	}
	
	public ArrayList<Product> getProductsOfSeller(int userID)
	{
		ArrayList<Product> products = new ArrayList<Product>();
		
		String query = "SELECT P." + Product.COL_ID + ", P."
								 + Product.COL_NAME + ", P."
								 + Product.COL_CATEGORY + ", P."
								 + Product.COL_BRAND + ", P."
								 + Product.COL_DESC + ", P."
								 + Product.COL_STOCK + ", P."
								 + Product.COL_SOLD + ", P."
								 + Product.COL_PRICE + ", P."
								 + Product.COL_DISC + ", P."
								 + Product.COL_SHIP + ", P."
								 + Product.COL_SHIPDUR + ", "
								 + "F.Favorites, R.rate FROM " + Product.TABLE + " AS P LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", "
																								   + "COUNT(F." + Favorite.COL_PRODUCT
																								   + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
																								   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT + 
																				" LEFT JOIN (SELECT R." + Rating.COL_PRODUCT + 
																							", AVG(R." + Rating.COL_RATING + 
																							") AS rate FROM " + Rating.TABLE + " AS R GROUP BY R." + Rating.COL_PRODUCT + ") AS R "
																							+ " ON P." + Product.COL_ID + " = " + "R." + Rating.COL_PRODUCT + " WHERE P." + Product.COL_SELLERID + " = ? ";
		System.out.println(query);
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				products.add(toSellerProduct(rs));
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] GET PRODUCTS DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] GET PRODUCTS FAILED");
			e.printStackTrace();
		}
		
		return products;
	}
	
	public Product toSellerProduct(ResultSet rs) throws SQLException{
		Product product = new Product();
		
		product.setProductID(rs.getInt(Product.COL_ID));
		product.setName(rs.getString(Product.COL_NAME));
		product.setCategory(rs.getString(Product.COL_CATEGORY));
		product.setBrand(rs.getString(Product.COL_BRAND));
		product.setDescription(rs.getString(Product.COL_DESC));
		product.setSold(rs.getLong(Product.COL_SOLD));
		product.setStock(rs.getLong(Product.COL_STOCK));
		product.setPrice(rs.getDouble(Product.COL_PRICE));
		product.setDiscount(rs.getDouble(Product.COL_DISC));
		product.setShipping(rs.getDouble(Product.COL_SHIP));
		product.setShippingduration(rs.getInt(Product.COL_SHIPDUR));
		product.setFavorites(rs.getLong("Favorites"));
		product.setRating(rs.getDouble("rate"));
		
		return product;
	}
	
	public boolean doesProductExist(String name, String category, String brand, String description, long stock,
			double price, double discount, double shipping, int sellerID) {
		boolean found = false;
		
		String query = "SELECT " + Product.COL_ID + " FROM " + Product.TABLE + " WHERE " + Product.COL_NAME + " = ? AND "
																						 + Product.COL_CATEGORY + " = ? AND "
																						 + Product.COL_BRAND + " = ? AND "
																						 + Product.COL_DESC + " = ? AND "
																						 + Product.COL_STOCK + " = ? AND "
																						 + Product.COL_PRICE + " = ? AND "
																						 + Product.COL_DISC + " = ? AND "
																						 + Product.COL_SHIP + " = ? AND "
																						 + Product.COL_SELLERID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, brand);
			ps.setString(4, description);
			ps.setLong(5, stock);
			ps.setDouble(6, price);
			ps.setDouble(7, discount);
			ps.setDouble(8, shipping);
			ps.setInt(9, sellerID);
			
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			System.out.println("[PRODUCT] FIND DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] FIND FAILED");
			e.printStackTrace();
		}
		return found;
	}

	public void addProduct(String name, String category, String brand, String description, long stock, double price,
			double discount, double shipping, int sellerID, int shippdur) {
		String query = "INSERT INTO " + Product.TABLE + " (" + Product.COL_NAME + ", "
															 + Product.COL_CATEGORY + ", "
															 + Product.COL_BRAND + ", "
															 + Product.COL_DESC + ", "
															 + Product.COL_STOCK + ", "
															 + Product.COL_PRICE + ", "
															 + Product.COL_DISC + ", "
															 + Product.COL_SHIP + ", "
															 + Product.COL_SELLERID + ", "
															 + Product.COL_SHIPDUR
															 + ") VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, brand);
			ps.setString(4, description);
			ps.setLong(5, stock);
			ps.setDouble(6, price);
			ps.setDouble(7, discount);
			ps.setDouble(8, shipping);
			ps.setInt(9, sellerID);
			ps.setInt(10, shippdur);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] ADD DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] ADD FAILED");
			e.printStackTrace();
		}
		
	}

	public void updateProduct(String name, String category, String brand, String description, long stock, double price,
			double discount, double shipping, int productID, int shippdur) {
		String query = "UPDATE " + Product.TABLE + " SET " + Product.COL_NAME + " = ?, "
															 + Product.COL_CATEGORY + " = ?, "
															 + Product.COL_BRAND + " = ?, "
															 + Product.COL_DESC + " = ?, "
															 + Product.COL_STOCK + " = ?, "
															 + Product.COL_PRICE + " = ?, "
															 + Product.COL_DISC + " = ?, "
															 + Product.COL_SHIP + " = ? "
															 + Product.COL_SHIPDUR + " = ?"
															 + " WHERE " + Product.COL_ID + " = ? ";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, brand);
			ps.setString(4, description);
			ps.setLong(5, stock);
			ps.setDouble(6, price);
			ps.setDouble(7, discount);
			ps.setDouble(8, shipping);
			ps.setInt(9, shippdur);
			ps.setInt(10, productID);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] UPDATE DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] UPDATE FAILED");
			e.printStackTrace();
		}
	}

	public void deleteProduct(int productID) {
		String query;
		
		try {
			PreparedStatement ps;
			
			query = "DELETE FROM " + Favorite.TABLE + " WHERE " + Favorite.COL_PRODUCT + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, productID);	
			ps.executeUpdate();
			System.out.println("[PRODUCT] FAVORITE DELETE SUCCESS");
			
			query = "DELETE FROM " + CartContent.TABLE + " WHERE " + CartContent.COL_PRODUCT + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, productID);
			ps.executeUpdate();
			System.out.println("[PRODUCT] CART CONTENT DELETE SUCCESS");
			
			query = "DELETE FROM " + Rating.TABLE + " WHERE " + Rating.COL_PRODUCT + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, productID);
			ps.executeUpdate();
			System.out.println("[PRODUCT] RATING DELETE SUCCESS");
			
			query = "DELETE FROM " + OrderContent.TABLE + " WHERE " + OrderContent.COL_PRODUCT + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, productID);			
			ps.executeUpdate();
			System.out.println("[PRODUCT] ORDER CONTENT DELETE SUCCESS");				
			
			query = "DELETE FROM " + OrderContent.TABLE + " WHERE " + OrderContent.COL_PRODUCT + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, productID);
			ps.executeUpdate();
			System.out.println("[PRODUCT] ORDER CONTENT DELETE SUCCESS");
			
			query = "DELETE FROM " + Product.TABLE + " WHERE " + Product.COL_ID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, productID);
			ps.executeUpdate();

		kk;
			ps.close();
			System.out.println("[PRODUCT] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[PRODUCT] DELETE FAILED");
			e.printStackTrace();
		}	
	}
}
