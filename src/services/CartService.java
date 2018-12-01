package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Cart;
import model.CartContent;
import model.Product;
import model.User;

public class CartService {
	
	public void addIncomeToSeller(double income, int productID)
	{
		String query = "UPDATE " + User.CONSU_TABLE + " SET " + User.COL_INCOME + " = ?"
								 + " WHERE " + User.COL_ID + " = "
								 			 +"(SELECT P." + Product.COL_SELLERID + " FROM " + Product.TABLE + " AS P WHERE P." + Product.COL_ID + " = ?)";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setDouble(1, income);
			ps.setInt(2, productID);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[USER] UPDATE INCOME DONE");
		}catch(SQLException e) {
			System.out.println("[USER] UPDATE INCOME FAILED");
			e.printStackTrace();
		}
	}

	public double getSellerIncome(int productID)
	{
		double income = 0;
		String query = "SELECT U." + User.COL_INCOME + " FROM " + User.CONSU_TABLE + " AS U, " + Product.TABLE + " AS P WHERE P." + Product.COL_SELLERID + " = U." + User.COL_ID + " AND P." + Product.COL_SELLERID + " = ?";
	
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, productID);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				income = rs.getDouble(User.COL_INCOME);

			rs.close();
			ps.close();
			System.out.println("[USER] GET INCOME DONE");
		}catch(SQLException e) {
			System.out.println("[USER] GET INCOME FAILED");
			e.printStackTrace();
		}
		
		return income;
	}
	public int getProductSold(int id)
	{
		int sold = 0;
		
		String query = "SELECT " + Product.COL_SOLD + " FROM " + Product.TABLE + " WHERE " + Product.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				sold = rs.getInt(Product.COL_SOLD);

			rs.close();
			ps.close();
			System.out.println("[CART] GET STOCK DONE");
		}catch(SQLException e) {
			System.out.println("[CART] GET STOCK FAILED");
			e.printStackTrace();
		}
		
		return sold;
	}
	
	public int getProductStock(int id)
	{
		int stock = 0;
		
		String query = "SELECT " + Product.COL_STOCK + " FROM " + Product.TABLE + " WHERE " + Product.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				stock = rs.getInt(Product.COL_STOCK);

			ps.close();
			System.out.println("[CART] GET STOCK DONE");
		}catch(SQLException e) {
			System.out.println("[CART] GET STOCK FAILED");
			e.printStackTrace();
		}
		
		return stock;
	}
	public void delete(int id)
	{
		String query = "DELETE FROM " + CartContent.TABLE + " WHERE " + CartContent.COL_ID + " = ? ";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			

			ps.close();
			System.out.println("[CART] DELETE DONE");
		}catch(SQLException e) {
			System.out.println("[CART] DELETE FAILED");
			e.printStackTrace();
		}
	}
	public ArrayList<Cart> getCartOfUser(int userID)
	{
		ArrayList<Cart> cart = new ArrayList<Cart>();
		String query = "SELECT C." + CartContent.COL_ID +
							", P." + Product.COL_ID +
							", P." + Product.COL_NAME +  
							", C." + CartContent.COL_QUANTITY +
							", P." + Product.COL_DISC +
							", P." + Product.COL_PRICE +
							", P." + Product.COL_SHIP +
							", ((P." + Product.COL_PRICE + " - " + "((P." + Product.COL_DISC + "/100)*P." + Product.COL_PRICE + ") + P." + Product.COL_SHIP + ")*C." + CartContent.COL_QUANTITY + ") AS Total FROM " + 
							Product.TABLE + " AS P, " + CartContent.TABLE + " AS C WHERE P." + Product.COL_ID + " = " + CartContent.COL_PRODUCT + " AND " + CartContent.COL_USER + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				cart.add(toCart(rs));
			
			rs.close();
			ps.close();
			System.out.println("[CART] GET CART DONE");
		}catch(SQLException e) {
			System.out.println("[USER] GET CART FAILED");
			e.printStackTrace();
		}
		
		return cart;
	}
	
	private Cart toCart(ResultSet rs) throws SQLException{
		Cart cart = new Cart();
		
		cart.setProductID(rs.getInt(Product.COL_ID));
		cart.setId(rs.getInt(CartContent.COL_ID));
		cart.setName(rs.getString(Product.COL_NAME));
		cart.setQuantity(rs.getInt(CartContent.COL_QUANTITY));
		cart.setDiscount(rs.getDouble(Product.COL_DISC));
		cart.setPrice(rs.getDouble(Product.COL_PRICE));
		cart.setShipping(rs.getDouble(Product.COL_SHIP));
		cart.setTotal(rs.getDouble("Total"));
		System.out.println(rs.getDouble("Total"));
		return cart;
	}
	public void updateQuantity(int newquantity, int id) {
		String query = "UPDATE " + CartContent.TABLE + " SET " + CartContent.COL_QUANTITY + " = ? " + " WHERE " + CartContent.COL_ID + " = ? ";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(2, id);
			ps.setInt(1, newquantity);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[CART] QUANTITY UPDATE DONE");
		}catch(Exception e)
		{
			System.out.println("[CART] QUANTITY UPDATE FAIL");
			e.printStackTrace();
		}
		
	}
}
