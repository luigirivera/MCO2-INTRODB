package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Address;
import model.BankAccount;
import model.Card;
import model.Order;
import model.OrderContent;
import model.Product;
import model.User;

public class OrderService {
	public void updateStatus(int order, int product, String status)
	{
		String query = "UPDATE " + OrderContent.TABLE + " SET " + OrderContent.COL_STATUS + " = ?"
																+ " WHERE " + OrderContent.COL_ORDER + " = ?"
																+ " AND " + OrderContent.COL_PRODUCT + " = ?";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, status);
			ps.setInt(2, order);
			ps.setInt(3, product);

			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[ORDER CONTENT] STATUS UPDATE DONE");
		}catch(Exception e)
		{
			System.out.println("[ORDER CONTENT] STATUS UPDATE FAIL");
			e.printStackTrace();
		}
	}
	public ArrayList<OrderContent> getSalesOfSeller(int sellerID, String whereClause)
	{
		ArrayList<OrderContent> details = new ArrayList<OrderContent>();
		
		String query = "SELECT P." + Product.COL_NAME + ", "
							+ "OC." + OrderContent.COL_ORDER + ", "
							+ "OC." + OrderContent.COL_PRODUCT + ", "
							+ "P." + Product.COL_CATEGORY + ", "
							+ "P." + Product.COL_BRAND + ", "
							+ "A." + User.COL_USERNAME + ", "
							+ "OC." + OrderContent.COL_QUANTITY + ", "
							+ "OC." + OrderContent.COL_STATUS + ", "
							+ "O." + Order.COL_CREATION + ", "
							+ "OC." + OrderContent.COL_TOTAL
							+ " FROM " + OrderContent.TABLE + " AS OC, "
									   + Product.TABLE + " AS P, "
									   + User.TABLE + " AS A, "
									   + Order.TABLE + " AS O"
							+ " WHERE OC." + OrderContent.COL_ORDER + " = O." + Order.COL_ID
							+ " AND OC." + OrderContent.COL_PRODUCT + " = P." + Product.COL_ID
							+ " AND A." + User.COL_ID + " = O." + Order.COL_USERID
							+ " AND P." + Product.COL_SELLERID + " = ? "
							+ whereClause;
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);

			ps.setInt(1, sellerID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				details.add(toSale(rs));
			rs.close();
			ps.close();
			System.out.println("[ORDER CONTENT] SALES GET DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER CONTENT] SALES GET FAILED");
			e.printStackTrace();
		}
		return details;
	}
	
	private OrderContent toSale(ResultSet rs) throws SQLException{
		OrderContent oc = new OrderContent();
		
		oc.setOrderid(rs.getInt(OrderContent.COL_ORDER));
		oc.setProductID(rs.getInt(OrderContent.COL_PRODUCT));
		oc.setName(rs.getString(Product.COL_NAME));
		oc.setCategory(rs.getString(Product.COL_CATEGORY));
		oc.setBrand(rs.getString(Product.COL_BRAND));
		oc.setBuyer(rs.getString(User.COL_USERNAME));
		oc.setQuantity(rs.getInt(OrderContent.COL_QUANTITY));
		oc.setStatus(rs.getString(OrderContent.COL_STATUS));
		oc.setOrderdate(rs.getDate(Order.COL_CREATION));
		oc.setTotal(rs.getDouble(OrderContent.COL_TOTAL));
		
		return oc;
	}
	
	public ArrayList<OrderContent> getOrderDetails(int orderID, String status)
	{
		ArrayList<OrderContent> details = new ArrayList<OrderContent>();
		
		String query = "SELECT P." + Product.COL_NAME + ", P."
								   + Product.COL_CATEGORY + ", P."
								   + Product.COL_BRAND + ", A."
								   + User.COL_USERNAME + ", OC."
								   + OrderContent.COL_QUANTITY + ", OC."
								   + OrderContent.COL_STATUS + ", OC."
								   + OrderContent.COL_DELIVERYDATE + ", OC."
								   + OrderContent.COL_TOTAL
						+ " FROM " + OrderContent.TABLE + " AS OC, " + Product.TABLE + " AS P, " + User.TABLE + " AS A"
						+ " WHERE OC." + OrderContent.COL_PRODUCT + " = P." + Product.COL_ID + " AND P." + Product.COL_SELLERID + " = " + User.COL_ID + " AND " + OrderContent.COL_ORDER + " = ?"
						+ status;  
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, orderID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				details.add(toDetails(rs));
			rs.close();
			ps.close();
			System.out.println("[ORDER CONTENT] CONTENT GET DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER CONTENT] CONTENT GET FAILED");
			e.printStackTrace();
		}
		return details;
	}
	
	private OrderContent toDetails(ResultSet rs) throws SQLException{
		OrderContent oc = new OrderContent();
		
		oc.setName(rs.getString(Product.COL_NAME));
		oc.setCategory(rs.getString(Product.COL_CATEGORY));
		oc.setBrand(rs.getString(Product.COL_BRAND));
		oc.setSeller(rs.getString(User.COL_USERNAME));
		oc.setQuantity(rs.getInt(OrderContent.COL_QUANTITY));
		oc.setStatus(rs.getString(OrderContent.COL_STATUS));
		oc.setDelivery(rs.getDate(OrderContent.COL_DELIVERYDATE));
		oc.setTotal(rs.getDouble(OrderContent.COL_TOTAL));
		
		return oc;
	}

	public ArrayList<Order> getOrdersOfUser(int userID, String orderClause)
	{
		ArrayList<Order> orders = new ArrayList<Order>();
	
		String query = "SELECT C." + Order.COL_ID + ", C."
								 + Order.COL_QUANTITY + ", C."
								 + Order.COL_CREATION + ", CONCAT(A."
								 + Address.COL_LINE1 + ", ' ', COALESCE(CONCAT(A." + Address.COL_LINE2 + ", ' '), ''), A." + Address.COL_CITY + ", ' ', A." + Address.COL_PROV + ") AS Address, "
								 + "CONCAT(B." + BankAccount.COL_BANK + ", ' - ', B." + BankAccount.COL_ACCNUM + ") AS Bank, CONCAT('Card - ', Ca." + Card.COL_CARDNUMBER + ") AS Card "
								 + "FROM " + Order.TABLE + " AS C LEFT JOIN " + BankAccount.TABLE + " AS B ON C." + Order.COL_BANK + " = B." + BankAccount.COL_BAID
								 + " LEFT JOIN " + Card.TABLE + " AS Ca ON C." + Order.COL_CARD + " = Ca." + Card.COL_CARDSID + ", " + Address.TABLE + " AS A "
								 + "WHERE C." + Order.COL_ADDRESS + " = A." + Address.COL_ADDRESSID + " AND C." + Order.COL_USERID + " = ?" + orderClause;
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				orders.add(toOrder(rs));
			
			rs.close();
			ps.close();
			System.out.println("[ORDER] ORDER GET DONE");
		}catch(SQLException e) {
			System.out.println("[ORDER] ORDER GET FAILED");
			e.printStackTrace();
		}
		return orders;
	}
	
	private Order toOrder(ResultSet rs) throws SQLException
	{
		Order order = new Order();
		
		order.setId(rs.getInt(Order.COL_ID));
		order.setQuantity(rs.getInt(Order.COL_QUANTITY));
		order.setOrderDate(rs.getDate(Order.COL_CREATION));
		order.setAddress(rs.getString("Address"));
		if(rs.getString("Bank") != null)
			order.setPayment(rs.getString("Bank"));
		else
			order.setPayment(rs.getString("Card"));
		
		return order;
	}
	
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

	public void addtoOrder(int id, int productID, int quantity, double total)
	{
		String query = "INSERT INTO " + OrderContent.TABLE + " (" + OrderContent.COL_ORDER + ", "
																  + OrderContent.COL_PRODUCT + ", "
																  + OrderContent.COL_QUANTITY + ", "
																  + OrderContent.COL_TOTAL + ", "
																  + OrderContent.COL_STATUS + ", "
																  + OrderContent.COL_DELIVERYDATE + ") " + 
															"SELECT ?,?,?,?,'PENDING', DATE_ADD(NOW(), INTERVAL F.shippingduration DAY) FROM " + Product.TABLE + " AS F WHERE F." + Product.COL_ID + " = ? ";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.setDouble(4, total);
			ps.setInt(5, productID);
			
			
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
