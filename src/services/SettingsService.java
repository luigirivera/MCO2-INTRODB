package services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Calendar;

import database.DatabaseConnection;
import model.User;

public class SettingsService {
	public void updateInformation(int id, String username, String email, long number, Date birth, String gender)
	{
		String query = "UPDATE " + User.TABLE + " SET " + User.COL_USERNAME + " = ?, "
														+ User.COL_EMAIL + " = ?, "
														+ User.COL_NUMBER + " = ?, "
														+ User.COL_BIRTHDAY + " = ?, "
														+ User.COL_GENDER + " = ? "
														+ " WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(6, id);
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setLong(3, number);
			ps.setDate(4, birth);
			ps.setString(5, gender);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[USER] INFO UPDATE WITH NUMBER DONE");
		}catch(Exception e)
		{
			System.out.println("[USER] INFO UPDATE FAIL");
			e.printStackTrace();
		}
	}
	
	public void updateInformation(int id, String username, String email, Date birth, String gender)
	{
		String query = "UPDATE " + User.TABLE + " SET " + User.COL_USERNAME + " = ?, "
														+ User.COL_EMAIL + " = ?, "
														+ User.COL_NUMBER + " = ?, "
														+ User.COL_BIRTHDAY + " = ?, "
														+ User.COL_GENDER + " = ? "
														+ " WHERE " + User.COL_ID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(6, id);
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setNull(3, Types.BIGINT);
			ps.setDate(4, birth, Calendar.getInstance());
			ps.setString(5, gender);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[USER] INFO UPDATE NO NUMBER DONE");
		}catch(Exception e)
		{
			System.out.println("[USER] INFO UPDATE FAIL");
			e.printStackTrace();
		}
	}
	
	public void changePassword(String username, String password)
	{
		String query = "UPDATE " + User.TABLE + " SET " + User.COL_PASSWORD + " = ? "
														+ " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setString(1, password);
			ps.setString(2, username);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[USER] PASSWORD CHANGE DONE");
		}catch(Exception e)
		{
			System.out.println("[USER] PASSWORD CHANGE FAIL");
			e.printStackTrace();
		}
	}
	
	public void updateDelete(String username, boolean deletion) {
		String query = "UPDATE " + User.TABLE + " SET " + User.COL_FORDELETION + " = ? "
														+ " WHERE " + User.COL_USERNAME + " = ?";

		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setBoolean(1, deletion);
			ps.setString(2, username);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[USER] DELETION CHANGE DONE");
		}catch(Exception e)
		{
			System.out.println("[USER] DELETION CHANGE FAIL");
			e.printStackTrace();
		}
	}
}
