package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Address;
import model.BankAccount;
import model.Card;
import model.Favorite;
import model.Following;
import model.Product;
import model.User;

public class AccountsService {

	public ArrayList<User> getAccounts(String whereClause) {
		ArrayList<User> accounts = new ArrayList<User>();
		
		String query = "SELECT " + User.COL_ID + ", "
								 + User.COL_USERNAME + ", "
								 + User.COL_NUMBER + ", "
								 + User.COL_EMAIL + ", "
								 + User.COL_REGISTER + ", "
								 + User.COL_ISLOCKED + ", "
								 + User.COL_FORDELETION + ", "
								 + User.COL_ISCORPORATE + ", "
								 + User.COL_LASTLOGIN + " FROM " + User.TABLE + whereClause;
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
		user.setCorporate(rs.getBoolean(User.COL_ISCORPORATE));
		
		return user;
	}

	public void deleteAccount(int userID) {
		String query;
		
		try {
			PreparedStatement ps;
			
			query = "DELETE FROM " + Following.TABLE + " WHERE " + Following.COL_FOLLOWER + " = ?";
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
			
			query = "DELETE FROM " + Product.TABLE + " WHERE " + Product.COL_SELLERID + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] PRODUCT DELETE SUCCESS");
			
			query = "DELETE FROM " + Favorite.TABLE + " WHERE " + Favorite.COL_USER + " = ?";
			ps = DatabaseConnection.getConnection().prepareStatement(query);
			ps.setInt(1, userID);	
			ps.executeUpdate();
			System.out.println("[ACCOUNTS] FAVORITE DELETE SUCCESS");
			
			query = "DELETE FROM " + User.TABLE + " WHERE " + User.COL_ID + " = ?";			
			ps = DatabaseConnection.getConnection().prepareStatement(query);			
			ps.setInt(1, userID);			
			ps.executeUpdate();
			kk;
			ps.close();
			System.out.println("[ACCOUNTS] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[ACCOUNTS] DELETE FAILED");
			e.printStackTrace();
		}
		
	}

}
