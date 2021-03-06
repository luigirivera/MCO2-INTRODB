package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Address;
import model.BankAccount;
import model.Card;
import model.CartContent;
import model.Favorite;
import model.Following;
import model.Order;
import model.OrderContent;
import model.Product;
import model.Rating;
import model.User;

public class AccountsService {
	public void setCoinsOfUser(double coins, int userID) {
		
		String query = "UPDATE " + User.CONSU_TABLE + " SET " + User.COL_COINS + " = ? WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setDouble(1, coins);
			ps.setInt(2, userID);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[USER] COINS UPDATED DONE");
		}catch(SQLException e) {
			System.out.println("[USER] COINS UPDATED FAILED");
			e.printStackTrace();
		}
		
	}
	
	public double getCoinsOfUser(int userID) {
		double coins = 0;
		String query = "SELECT " + User.COL_COINS + " FROM " + User.CONSU_TABLE + " WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				coins = rs.getDouble(User.COL_COINS);
			
			rs.close();
			ps.close();
			System.out.println("[USER] COINS GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] COINS GET FAILED");
			e.printStackTrace();
		}
		return coins;
	}
	
	
	public int getFollowing(int userID)
	{
		int following = 0;
		String query = "SELECT COUNT(" + Following.COL_FOLLOWER + ") As Following FROM " + Following.TABLE + " WHERE " + Following.COL_FOLLOWER + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				following = rs.getInt("Following");
			
			rs.close();
			ps.close();
			System.out.println("[USER] FOLLOWING GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] FOLLOWING GET FAILED");
			e.printStackTrace();
		}
		return following;
	}
	public int getFollowers(int userID)
	{
		int followers = 0;
		String query = "SELECT COUNT(" + Following.COL_USER + ") AS Follower FROM " + Following.TABLE + " WHERE " + Following.COL_USER + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				followers = rs.getInt("Follower");
			
			rs.close();
			ps.close();
			System.out.println("[USER] FOLLOWERS GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] FOLLOWERS GET FAILED");
			e.printStackTrace();
		}
		return followers;
	}
	public void setIncomeOfUser(double income, int userID)
	{
		String query = "UPDATE " + User.CONSU_TABLE + " SET " + User.COL_INCOME + " = ? WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setDouble(1, income);
			ps.setInt(2, userID);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[USER] BALANCE UPDATE DONE");
		}catch(SQLException e) {
			System.out.println("[USER] BALANCE UPDATE FAILED");
			e.printStackTrace();
		}
	}
	
	public double getBalance(int userID) {
		double balance = 0;
		String query = "SELECT " + User.COL_WALLET + " FROM " + User.CONSU_TABLE + " WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				balance = rs.getDouble(User.COL_WALLET);
			
			rs.close();
			ps.close();
			System.out.println("[USER] BALANCE GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] BALANCE GET FAILED");
			e.printStackTrace();
		}
		return balance;
	}
	public void setBalance(double balance, int userID)
	{
		String query = "UPDATE " + User.CONSU_TABLE + " SET " + User.COL_WALLET + " = ? WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setDouble(1, balance);
			ps.setInt(2, userID);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[USER] BALANCE UPDATE DONE");
		}catch(SQLException e) {
			System.out.println("[USER] BALANCE UPDATE FAILED");
			e.printStackTrace();
		}
	}
	
	public double getIncomeOfUser(int userID) {
		double income = 0;
		String query = "SELECT " + User.COL_INCOME + " FROM " + User.CONSU_TABLE + " WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				income = rs.getDouble(User.COL_INCOME);
			
			rs.close();
			
			ps.close();
			System.out.println("[USER] INCOME GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] INCOME GET FAILED");
			e.printStackTrace();
		}
		return income;
	}
	
	public void unfollow(int user, int follower)
	{
		String query = "DELETE FROM " + Following.TABLE + " WHERE " + Following.COL_USER + " = ? AND "
																	+ Following.COL_FOLLOWER + " = ?";

		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, user);
			ps.setInt(2, follower);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[USER] UNFOLLOW DONE");
		}catch(SQLException e) {
			System.out.println("[USER] UNFOLLOW FAILED");
			e.printStackTrace();
		}
	}
	
	public boolean checkFollow(int user, int follower)
	{
		boolean found = false;
		String query = "SELECT COUNT(*) FROM " + Following.TABLE + " WHERE " + Following.COL_USER + " = ? AND "
																		  							   + Following.COL_FOLLOWER + " = ?";

		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, user);
			ps.setInt(2, follower);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() && rs.getInt("COUNT(*)") > 0)
				found = true;
			
			rs.close();
			ps.close();
			System.out.println("[USER] FOLLOW FOUND DONE");
		}catch(SQLException e) {
			System.out.println("[USER] FOLLOW FOUND FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	
	public ArrayList<User> getAccounts(String whereClause) {
		ArrayList<User> accounts = new ArrayList<User>();
		
		String query = "SELECT U." + User.COL_ID + ", U."
								 + User.COL_USERNAME + ", U."
								 + User.COL_NUMBER + ", U."
								 + User.COL_EMAIL + ", U."
								 + User.COL_REGISTER + ", U."
								 + User.COL_ISLOCKED + ", C."
								 + User.COL_FORDELETION + ", U."
								 + User.COL_LASTLOGIN + " FROM " + User.TABLE + " AS U LEFT JOIN " + User.CONSU_TABLE + " AS C ON U." + User.COL_ID + " = C." + User.COL_ID + whereClause + " ORDER BY " + User.COL_REGISTER + " ASC";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
		
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toAccount(rs));
			
			ps.close();
			rs.close();
			System.out.println("[ACCOUNTS] GET ACCOUNTS DONE");
		}catch(SQLException e) {
			System.out.println("[ACCOUNTS] GET ACCOUNTS FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public ArrayList<User> getCorporateAccounts() {
		ArrayList<User> accounts = new ArrayList<User>();
		
		String query = "SELECT U." + User.COL_ID + ", U."
								 + User.COL_USERNAME + ", U."
								 + User.COL_NUMBER + ", U."
								 + User.COL_EMAIL + ", U."
								 + User.COL_REGISTER + ", U."
								 + User.COL_ISLOCKED + ", U."
								 + User.COL_LASTLOGIN + " FROM " + User.TABLE + " AS U HAVING U." + User.COL_ID + " IN (SELECT " + User.COL_ID + " FROM " + User.CORP_TABLE + ") ORDER BY U." + User.COL_REGISTER + " ASC";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
		
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toCorporateAccount(rs));
			
			ps.close();
			rs.close();
			System.out.println("[ACCOUNTS] GET ACCOUNTS DONE");
		}catch(SQLException e) {
			System.out.println("[ACCOUNTS] GET ACCOUNTS FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public ArrayList<User> getConsumerAccounts() {
		ArrayList<User> accounts = new ArrayList<User>();
		
		String query = "SELECT U." + User.COL_ID + ", U."
								 + User.COL_USERNAME + ", U."
								 + User.COL_NUMBER + ", U."
								 + User.COL_EMAIL + ", U."
								 + User.COL_REGISTER + ", U."
								 + User.COL_ISLOCKED + ", C."
								 + User.COL_FORDELETION + ", U."
								 + User.COL_LASTLOGIN + " FROM " + User.TABLE + " AS U LEFT JOIN " + User.CONSU_TABLE + " AS C ON U." + User.COL_ID + " = C." + User.COL_ID + " HAVING " + User.COL_ID + " IN (SELECT " + User.COL_ID + " FROM " + User.CONSU_TABLE + ") ORDER BY "  + User.COL_REGISTER + " ASC";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
		
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toAccount(rs));
			
			ps.close();
			rs.close();
			System.out.println("[ACCOUNTS] GET ACCOUNTS DONE");
		}catch(SQLException e) {
			System.out.println("[ACCOUNTS] GET ACCOUNTS FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public User toAccount(ResultSet rs) throws SQLException{
		User user = new User();
		
		user.setId(rs.getInt(User.COL_ID));
		user.setUsername(rs.getString(User.COL_USERNAME));
		user.setNumber(rs.getLong(User.COL_NUMBER));
		user.setEmail(rs.getString(User.COL_EMAIL));
		user.setRegister(rs.getDate(User.COL_REGISTER));
		user.setLastLogin(rs.getDate(User.COL_LASTLOGIN));
		user.setLocked(rs.getBoolean(User.COL_ISLOCKED));
		user.setForDeletion(rs.getBoolean(User.COL_FORDELETION));
		
		return user;
	}
	
	public User toCorporateAccount(ResultSet rs) throws SQLException{
		User user = new User();
		
		user.setId(rs.getInt(User.COL_ID));
		user.setUsername(rs.getString(User.COL_USERNAME));
		user.setNumber(rs.getLong(User.COL_NUMBER));
		user.setEmail(rs.getString(User.COL_EMAIL));
		user.setRegister(rs.getDate(User.COL_REGISTER));
		user.setLastLogin(rs.getDate(User.COL_LASTLOGIN));
		user.setLocked(rs.getBoolean(User.COL_ISLOCKED));
		
		return user;
	}

	public void deleteAccount(int userID) {
		String query;
		
		try {
			PreparedStatement ps;
			
			query = "DELETE FROM " + CartContent.TABLE + " WHERE " + CartContent.COL_USER + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);			
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] CART CONTENT DELETE SUCCESS");
			
			query = "DELETE FROM " + Rating.TABLE + " WHERE " + Rating.COL_USER + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);			
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] RATING DELETE SUCCESS");
			
			ArrayList<Integer> orderIDs = new ArrayList<Integer>();
			query = "SELECT " + Order.COL_ID + " FROM " + Order.TABLE + " WHERE " + Order.COL_USERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);	
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				orderIDs.add(rs.getInt(Order.COL_ID));
			System.out.println("[ACCOUNTS] ORDER GET SUCCESS");
			
			for(Integer oID : orderIDs)
			{
				query = "DELETE FROM " + OrderContent.TABLE + " WHERE " + OrderContent.COL_ORDER + " = ?";			
				ps = DatabaseConnection.getConnection().prepareStatement(query);			
				ps.setInt(1, oID);			
				ps.executeUpdate();
				System.out.println("[ACCOUNTS] ORDER CONTENT DELETE SUCCESS");				
			}
			
			ArrayList<Integer> productIDs = new ArrayList<Integer>();
			query = "SELECT " + Product.COL_ID + " FROM " + Product.TABLE + " WHERE " + Product.COL_SELLERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			while(rs.next())
				productIDs.add(rs.getInt(Product.COL_ID));
			System.out.println("[ACCOUNTS] PRODUCTS GET SUCCESS");
			
			for(Integer pID : productIDs)
			{
				query = "DELETE FROM " + CartContent.TABLE + " WHERE " + CartContent.COL_PRODUCT + " = ?";			
				ps = DatabaseConnection.getConnection().prepareStatement(query);			
				ps.setInt(1, pID);			
				ps.executeUpdate();
				System.out.println("[ACCOUNTS] CART CONTENT DELETE SUCCESS");
				
				query = "DELETE FROM " + OrderContent.TABLE + " WHERE " + OrderContent.COL_PRODUCT + " = ?";			
				ps = DatabaseConnection.getConnection().prepareStatement(query);			
				ps.setInt(1, pID);			
				ps.executeUpdate();
				System.out.println("[ACCOUNTS] ORDER CONTENT DELETE SUCCESS");
			
				query = "DELETE FROM " + Favorite.TABLE + " WHERE " + Favorite.COL_PRODUCT + " = ?";			
				ps = DatabaseConnection.getConnection().prepareStatement(query);			
				ps.setInt(1, pID);			
				ps.executeUpdate();
				System.out.println("[ACCOUNTS] FAVORITE DELETE SUCCESS");
				
				query = "DELETE FROM " + Rating.TABLE + " WHERE " + Rating.COL_PRODUCT + " = ?";			
				ps = DatabaseConnection.getConnection().prepareStatement(query);			
				ps.setInt(1, pID);			
				ps.executeUpdate();
				System.out.println("[ACCOUNTS] RATING DELETE SUCCESS");
				
				
			}
			
			query = "DELETE FROM " + Order.TABLE + " WHERE " + Order.COL_USERID + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);			
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] ORDERS DELETE SUCCESS");
			
			query = "DELETE FROM " + Following.TABLE + " WHERE " + Following.COL_FOLLOWER + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] FOLLOW DELETE SUCCESS");
			
			query = "DELETE FROM " + Following.TABLE + " WHERE " + Following.COL_USER + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] FOLLOW DELETE SUCCESS");
			
			query = "DELETE FROM " + Card.TABLE + " WHERE " + Card.COL_USERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] CARD DELETE SUCCESS");
			
			query = "DELETE FROM " + Address.TABLE + " WHERE " + Address.COL_USERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] ADDRESS DELETE SUCCESS");
			
			query = "DELETE FROM " + BankAccount.TABLE + " WHERE " + Address.COL_USERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] BANK DELETE SUCCESS");
			
			query = "DELETE FROM " + Favorite.TABLE + " WHERE " + Favorite.COL_USER + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] FAVORITE DELETE SUCCESS");
			
			query = "DELETE FROM " + Product.TABLE + " WHERE " + Product.COL_SELLERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] PRODUCT DELETE SUCCESS");
			
			query = "DELETE FROM " + User.CONSU_TABLE + " WHERE " + Order.COL_USERID + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);			
			ps.executeUpdate();
			
			query = "DELETE FROM " + User.TABLE + " WHERE " + User.COL_ID + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);			
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			System.out.println("[ACCOUNTS] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] DELETE FAILED");
			e.printStackTrace();
		}
		
	}

}
