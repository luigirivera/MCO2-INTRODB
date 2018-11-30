package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import model.Following;
import model.User;

public class LoginService {
	public boolean checkCorporate(int id)
	{
		boolean found = false;
		
		String query = "SELECT " + User.COL_ID + " FROM " + User.CORP_TABLE + " WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] CORPORATE CHECK DONE");
		}catch(SQLException e) {
			System.out.println("[USER] CORPORATE CHECK FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	public boolean doesUsernameExist(String username) {
		boolean found = false;
		
		String query = "SELECT " + User.COL_USERNAME + " FROM " + User.TABLE + " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] ACCOUNT CHECK DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ACCOUNT CHECK FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	
	public boolean doesEmailExist(String email) {
		boolean found = false;
		
		String query = "SELECT " + User.COL_EMAIL + " FROM " + User.TABLE + " WHERE " + User.COL_EMAIL + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] ACCOUNT CHECK DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ACCOUNT CHECK FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	
	public boolean doesNumberExist(long number) {
		boolean found = false;
		
		String query = "SELECT " + User.COL_NUMBER + " FROM " + User.TABLE + " WHERE " + User.COL_NUMBER + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setLong(1, number);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] ACCOUNT CHECK DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ACCOUNT CHECK FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	
	public void followAccount(int user, int follower)
	{
		String query = "INSERT INTO " + Following.TABLE + " (" + Following.COL_USER + ", "
				+ Following.COL_FOLLOWER + ") VALUES (?, ?)";

		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, user);
			ps.setInt(2, follower);
			
			ps.executeUpdate();
			
			System.out.println("[USER] FOLLOW DONE");
		}catch(SQLException e) {
			System.out.println("[USER] FOLLOW FAILED");
			e.printStackTrace();
		}
	}
	
	public int getID(String username)
	{
		int id = 0;
		
		String query = "SELECT " + User.COL_ID + " FROM " + User.TABLE + " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				id = rs.getInt(User.COL_ID);
			System.out.println("[USER] ID GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ID GET FAILED");
			e.printStackTrace();
		}
		
		return id;
	}
	
	public void registerAccount(String username, String password, String email) {
		String query = "INSERT INTO " + User.TABLE + " (" + User.COL_USERNAME + ", " 
														  + User.COL_PASSWORD + ", "
														  + User.COL_EMAIL + ", "
														  + User.COL_REGISTER
														  + ") VALUES(?,?,?, NOW())";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			
			ps.executeUpdate();
			ps.close();
			
			System.out.println("[USER] REGISTER DONE");
		}catch(SQLException e) {
			System.out.println("[USER] REGISTER FAILED");
			e.printStackTrace();
		}

	}
	
	public void registerAccount(String username, String password, long number) { 
		String query = "INSERT INTO " + User.TABLE + " (" + User.COL_USERNAME + ", " 
														  + User.COL_PASSWORD + ", "
														  + User.COL_NUMBER + ", "
														  + User.COL_REGISTER
														  + ") VALUES(?,?,?, NOW())";
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setLong(3, number);
			
			ps.executeUpdate();
			ps.close();
			
			System.out.println("[USER] REGISTER DONE");
		}catch(SQLException e) {
			System.out.println("[USER] REGISTER FAILED");
			e.printStackTrace();
		}
		
	}
	
	public User getAccountLogin(String username) {
		User account = null;
		String query = "SELECT " + User.COL_USERNAME + ", " + User.COL_PASSWORD + ", " + User.COL_TRIES + ", " + User.COL_ISLOCKED + " FROM " + User.TABLE + " WHERE " + User.COL_USERNAME + " = ?";
		
			try {
				PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
				
				ps.setString(1, username);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next())
					account = toAccountLogin(rs);
				
				ps.close();
				rs.close();
				System.out.println("[USER] ACCOUNT GET DONE");
			}catch(SQLException e) {
				System.out.println("[USER] ACCOUNT GET FAILED");
				e.printStackTrace();
			}
			
			return account;
		}
	
	private User toAccountLogin(ResultSet rs) throws SQLException{
		User user = new User();
		
		user.setUsername(rs.getString(User.COL_USERNAME));
		user.setPassword(rs.getString(User.COL_PASSWORD));
		user.setTries(rs.getInt(User.COL_TRIES));
		user.setLocked(rs.getBoolean(User.COL_ISLOCKED));
		
		return user;
	}
	
	public void updateAccount(String username, int tries, boolean isLocked)
	{
		String query = "UPDATE " + User.TABLE + " SET " + User.COL_TRIES + " = ?,"
														+ User.COL_ISLOCKED + " = ?"
														+ " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, tries);
			ps.setBoolean(2, isLocked);
			ps.setString(3, username);
			
			ps.executeUpdate();
			ps.close();
			
			System.out.println("[USER] ACCOUNT UPDATE DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ACCOUNT UPDATE FAILED");
			e.printStackTrace();
		}
	}
	
	public void setLogin(String username)
	{
		String query = "UPDATE " + User.TABLE + " SET " + User.COL_LASTLOGIN + " = NOW()"
														+ " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);

			ps.setString(1, username);
			
			ps.executeUpdate();
			ps.close();
			
			System.out.println("[USER] ACCOUNT UPDATE DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ACCOUNT UPDATE FAILED");
			e.printStackTrace();
		}
	}
	
	public User getDetails(String username)
	{
		User account = null;
		String query = "SELECT " + User.COL_USERNAME + ", "
								 + User.COL_PASSWORD + ", "
								 + User.COL_BIRTHDAY + ", "
								 + User.COL_GENDER + ", "
								 + User.COL_NUMBER + ", "
								 + User.COL_EMAIL + ", "
								 + User.COL_ID
								 + " FROM " + User.TABLE + " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				account = toAccount(rs);
			
			ps.close();
			rs.close();
			System.out.println("[USER] ACCOUNT GET DONE");
		}catch(SQLException e) {
			System.out.println("[USER] ACCOUNT GET FAILED");
			e.printStackTrace();
		}
		
		return account;
								 
	}
	
	private User toAccount(ResultSet rs) throws SQLException{
		User user = new User();
		
		user.setId(rs.getInt(User.COL_ID));
		user.setUsername(rs.getString(User.COL_USERNAME));
		user.setPassword(rs.getString(User.COL_PASSWORD));
		user.setBirthday(rs.getDate(User.COL_BIRTHDAY));
		user.setGender(rs.getString(User.COL_GENDER));
		user.setNumber(rs.getLong(User.COL_NUMBER));
		user.setEmail(rs.getString(User.COL_EMAIL));
		
		return user;
	}
}
