package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Product;
import model.User;

public class ProductsService {
	
	public ArrayList<Product> getAllProducts()
	{
		ArrayList<Product> products = new ArrayList<Product>();
		
		String query = "SELECT " + Product.COL_ID + ", "
								 + Product.COL_NAME + ", "
								 + Product.COL_CATEGORY + ", "
								 + Product.COL_BRAND + ", "
								 + User.COL_USERNAME + ", "
								 + Product.COL_DESC + ", "
								 + Product.COL_STOCK + ", "
								 + Product.COL_SOLD + ", "
								 + Product.COL_PRICE + ", "
								 + Product.COL_DISC + ", "
								 + Product.COL_SHIP + " FROM " + Product.TABLE + " LEFT JOIN " + User.TABLE + " ON " + User.COL_ID + " = " + Product.COL_SELLERID;
		
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
		
		return product;
	}
	
	public ArrayList<Product> getProductsOfSeller(int userID)
	{
		ArrayList<Product> products = new ArrayList<Product>();
		
		String query = "SELECT " + Product.COL_ID + ", "
								 + Product.COL_NAME + ", "
								 + Product.COL_CATEGORY + ", "
								 + Product.COL_BRAND + ", "
								 + Product.COL_DESC + ", "
								 + Product.COL_STOCK + ", "
								 + Product.COL_PRICE + ", "
								 + Product.COL_DISC + ", "
								 + Product.COL_SHIP + " FROM " + Product.TABLE + " WHERE " + Product.COL_SELLERID + " = ?";
		
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
		product.setStock(rs.getLong(Product.COL_STOCK));
		product.setPrice(rs.getDouble(Product.COL_PRICE));
		product.setDiscount(rs.getDouble(Product.COL_DISC));
		product.setShipping(rs.getDouble(Product.COL_SHIP));
		
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
		String query = "DELETE FROM " + Product.TABLE + " WHERE " + Product.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
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
