package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.CartContent;
import model.Favorite;
import model.Product;
import model.User;

public class ProductsService {
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
								 + Product.COL_SHIP + ", "
								 + "F.Favorites FROM " + Product.TABLE + " AS P LEFT JOIN " + User.TABLE + " AS U ON U." + User.COL_ID + " = P." + Product.COL_SELLERID 
								 											   + " LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", " + "COUNT(F." + Favorite.COL_PRODUCT + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
								 											   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT
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
				 + Product.COL_SHIP + ", "
				 + "F.Favorites FROM " + Product.TABLE + " AS P LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", "
				 																	   + "COUNT(F." + Favorite.COL_PRODUCT
				 																	   + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
				 					   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT;
		
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
		product.setFavorites(rs.getLong("Favorites"));
		
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
								 + Product.COL_SHIP + ", "
								 + "F.Favorites FROM " + Product.TABLE + " AS P LEFT JOIN  (SELECT F." + Favorite.COL_PRODUCT + ", "
								 																	   + "COUNT(F." + Favorite.COL_PRODUCT
								 																	   + ") AS Favorites FROM " + Favorite.TABLE + " AS F GROUP BY F." + Favorite.COL_PRODUCT + ") AS F"
								 					   + " ON P." + Product.COL_ID + " = F." + Favorite.COL_PRODUCT + " WHERE P." + Product.COL_SELLERID + " = ? ";
		
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
		product.setFavorites(rs.getLong("Favorites"));
		
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
			if(discount == 0)
				ps.setNull(7, Types.DECIMAL);
			else
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
			double discount, double shipping, int sellerID) {
		String query = "INSERT INTO " + Product.TABLE + " (" + Product.COL_NAME + ", "
															 + Product.COL_CATEGORY + ", "
															 + Product.COL_BRAND + ", "
															 + Product.COL_DESC + ", "
															 + Product.COL_STOCK + ", "
															 + Product.COL_PRICE + ", "
															 + Product.COL_DISC + ", "
															 + Product.COL_SHIP + ", "
															 + Product.COL_SELLERID
															 + ") VALUES(?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, brand);
			ps.setString(4, description);
			ps.setLong(5, stock);
			ps.setDouble(6, price);
			if(discount == 0)
				ps.setNull(7, Types.DECIMAL);
			else
				ps.setDouble(7, discount);
			
			ps.setDouble(8, shipping);
			ps.setInt(9, sellerID);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] ADD DONE");
		}catch(SQLException e) {
			System.out.println("[PRODUCT] ADD FAILED");
			e.printStackTrace();
		}
		
	}

	public void updateProduct(String name, String category, String brand, String description, long stock, double price,
			double discount, double shipping, int productID) {
		String query = "UPDATE " + Product.TABLE + " SET " + Product.COL_NAME + " = ?, "
															 + Product.COL_CATEGORY + " = ?, "
															 + Product.COL_BRAND + " = ?, "
															 + Product.COL_DESC + " = ?, "
															 + Product.COL_STOCK + " = ?, "
															 + Product.COL_PRICE + " = ?, "
															 + Product.COL_DISC + " = ?, "
															 + Product.COL_SHIP + " = ? "
															 + " WHERE " + Product.COL_ID + " = ? ";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, brand);
			ps.setString(4, description);
			ps.setLong(5, stock);
			ps.setDouble(6, price);
			if(discount == 0)
				ps.setNull(7, Types.DECIMAL);
			else
				ps.setDouble(7, discount);
			
			ps.setDouble(8, shipping);
			ps.setInt(9, productID);
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
			
			query = "DELETE FROM " + Product.TABLE + " WHERE " + Product.COL_ID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, productID);
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[PRODUCT] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[PRODUCT] DELETE FAILED");
			e.printStackTrace();
		}
		
	}


}
