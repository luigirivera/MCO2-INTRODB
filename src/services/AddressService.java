package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Address;

public class AddressService {
	public ArrayList<Address> getAddressesOfUser(int userID)
	{
		ArrayList<Address> address = new ArrayList<Address>();
		
		String query = "SELECT " + Address.COL_LINE1 + ", "
								 + Address.COL_LINE2 + ", "
								 + Address.COL_CITY + ", "
								 + Address.COL_PROV + ", "
								 + Address.COL_ZIP + " FROM " + Address.TABLE + " WHERE " + Address.COL_USERID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				address.add(toAddress(rs));
			
			ps.close();
			rs.close();
			System.out.println("[ADDRESS] GET ADDRESSES DONE");
		}catch(SQLException e) {
			System.out.println("[ADDRESS] GET ADDRESSES FAILED");
			e.printStackTrace();
		}
		
		return address;
	}
	
	private Address toAddress(ResultSet rs) throws SQLException{
		Address address = new Address();
		
		address.setLine1(rs.getString(Address.COL_LINE1));
		address.setLine2(rs.getString(Address.COL_LINE2));
		address.setCity(rs.getString(Address.COL_CITY));
		address.setProvince(rs.getString(Address.COL_PROV));
		address.setZip(rs.getInt(Address.COL_ZIP));
		
		return address;
	}
	
	public void saveAddress(int userID, String line1, String line2, String city, String prov, int zip) {
		String query = "INSERT INTO " + Address.TABLE + " (" + Address.COL_USERID + ", "
															 + Address.COL_LINE1 + ", "
															 + Address.COL_LINE2 + ", "
															 + Address.COL_CITY + ", "
															 + Address.COL_PROV + ", "
															 + Address.COL_ZIP 
															 + ") VALUES (?,?,?,?,?,?)"; 
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setString(2, line1);
			ps.setString(3, line2);
			ps.setString(4, city);
			ps.setString(5, prov);
			ps.setInt(6, zip);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[ADDRESS] ADD DONE");
		}catch(SQLException e) {
			System.out.println("[ADDRESS] ADD FAILED");
			e.printStackTrace();
		}
	}
	
	public boolean doesAddressExist(int userID, String line1, String line2, String city, String prov, int zip) {
		boolean found = false;
		
		String query = "SELECT " + Address.COL_LINE1 + ", "
								 + Address.COL_LINE2 + ", "
								 + Address.COL_CITY + ", "
								 + Address.COL_PROV + ", "
								 + Address.COL_ZIP + " FROM " + Address.TABLE
								 + " WHERE " + Address.COL_LINE1 + " = ? AND "
											 + Address.COL_LINE2 + " = ? AND "
											 + Address.COL_CITY + " = ? AND "
											 + Address.COL_PROV + " = ? AND "
											 + Address.COL_ZIP + " = ? AND "
											 + Address.COL_USERID + " = ?";
	
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(6, userID);
			ps.setString(1, line1);
			ps.setString(2, line2);
			ps.setString(3, city);
			ps.setString(4, prov);
			ps.setInt(5, zip);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			System.out.println("[ADDRESS] FIND DONE");
		}catch(SQLException e) {
			System.out.println("[ADDRESS] FIND FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
}
