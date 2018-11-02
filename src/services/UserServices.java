package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import model.User;

public class UserServices {
	
	public boolean checkAccountExist(String username) {
		boolean found = false;
		
		String query = "SELECT " + User.COL_USERNAME + " FROM " + User.TABLE + " WHERE " + User.COL_USERNAME + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
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
}
