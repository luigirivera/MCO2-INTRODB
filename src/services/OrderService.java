package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import database.DatabaseConnection;
import model.Order;
import model.OrderContent;
import model.Product;

public class OrderService {

	public int createOrder(int userID, int addressID, int cardID, int bankID) {
		int orderID = 0;
		
		String query = "INSERT INTO " + Order.TABLE + " (" + Order.COL_USERID + ", "
														   + Order.COL_ADDRESS + ", "
														   + Order.COL_CARD + ", "
														   + Order.COL_BANK + ", "
														   + Order.COL_CREATION + ") VALUES(?,?,?,?,NOW())";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setInt(2, addressID);
			if(cardID == 0)
				ps.setNull(3, Types.INTEGER);
			else
				ps.setInt(3, cardID);
			
			if(bankID == 0)
				ps.setNull(4, Types.INTEGER);
			else
				ps.setInt(4, bankID);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[ORDER] ORDER CREATE DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] ORDER CREATE FAILED");
			e.printStackTrace();
		}
		
		query = "SELECT " + Order.COL_ID + " FROM " + Order.TABLE + " ORDER BY " + Order.COL_ID + " DESC LIMIT 1";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				orderID = rs.getInt(Order.COL_ID);
			
			rs.close();
			ps.close();
			System.out.println("[ORDER] ORDER ID GET DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] ORDER ID GET FAILED");
			e.printStackTrace();
		}
		
		return orderID;
	}

	public void addtoOrder(int id, int productID, int quantity)
	{
		String query = "INSERT INTO " + OrderContent.TABLE + " (" + OrderContent.COL_ORDER + ", "
																  + OrderContent.COL_PRODUCT + ", "
																  + OrderContent.COL_QUANTITY + ", "
																  + OrderContent.COL_STATUS + ", "
																  + OrderContent.COL_DELIVERYDATE + ") " + 
															"SELECT ?,?,?,'PENDING', DATE_ADD(NOW(), INTERVAL F.shippingduration DAY) FROM " + Product.TABLE + " AS F WHERE F." + Product.COL_ID + " = ? ";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.setInt(4, productID);
			
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[ORDER] ORDER CONTENT CREATE DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] ORDER CONTENT FAILED");
			e.printStackTrace();
		}		
	}
	
	public int getOrderQuantity(int id)
	{
		int quantity = 0;
		String query = "SELECT SUM(" + OrderContent.COL_QUANTITY + ") AS Quantity FROM " + OrderContent.TABLE + " WHERE " + OrderContent.COL_ORDER + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				quantity = rs.getInt("Quantity");
			
		
			rs.close();
			ps.close();
			System.out.println("[ORDER] ORDER QUANTITY DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] ORDER QUANTITY FAILED");
			e.printStackTrace();
		}
		
		return quantity;
	}

	public void setOrderTotalQuantity(int total, int id) {
		String query = "UPDATE " + Order.TABLE + " SET " + Order.COL_QUANTITY + " = ? WHERE " + Order.COL_ID + " = ?";
		
		try {
			
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, total);
			ps.setInt(2, id);
			
			ps.executeUpdate();

			ps.close();
			System.out.println("[ORDER] SET ORDER QUANTITY DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] SET ORDER QUANTITY FAILED");
			e.printStackTrace();
		}
		
	}
	
	public void subtractStock(int quantity, int sold, int productid)
	{
		String query = "UPDATE " + Product.TABLE + " SET " + Product.COL_STOCK + " = ?, " + Product.COL_SOLD + " = ? WHERE " + Product.COL_ID + " = ?";
		
		try {
			
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, quantity);
			ps.setInt(2, sold);
			ps.setInt(3, productid);
			
			ps.executeUpdate();

			ps.close();
			System.out.println("[ORDER] SET PRODUCT QUANTITY DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] SET PRODUCT QUANTITY FAILED");
			e.printStackTrace();
		}
	}
}
