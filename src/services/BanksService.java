package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.BankAccount;

public class BanksService {
	public void delete(int userID, int baid)
	{
		String query = "DELETE FROM " + BankAccount.TABLE + " WHERE " + BankAccount.COL_USERID + " = ? AND "
																	  + BankAccount.COL_BAID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setInt(2, baid);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[BANK] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[BANK] DELETE FAILED");
			e.printStackTrace();
		}
	}
	
	public ArrayList<BankAccount> getAccountsOfUser(int userID)
	{
		ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
		
		String query = "SELECT " + BankAccount.COL_BAID + ", " + BankAccount.COL_BANK + ", "
								 + BankAccount.COL_ACCNUM + " FROM " + BankAccount.TABLE + " WHERE " + BankAccount.COL_USERID + " = ? ORDER BY " + BankAccount.COL_BAID + " ASC";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				accounts.add(toAccount(rs));
			
			ps.close();
			rs.close();
			System.out.println("[BANK] GET ACCOUNTS DONE");
		}catch(SQLException e) {
			System.out.println("[BANK] GET ACCOUNTS FAILED");
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	private BankAccount toAccount(ResultSet rs) throws SQLException{
		BankAccount account = new BankAccount();
		
		account.setbAID(rs.getInt(BankAccount.COL_BAID));
		account.setBank(rs.getString(BankAccount.COL_BANK));
		account.setAccountNumber(rs.getLong(BankAccount.COL_ACCNUM));
		
		return account;
	}
	
	public boolean doesAccountExist(int userID, String bank, long num) {
		boolean found = false;
		
		String query = "SELECT " + BankAccount.COL_BANK + ", "
				 				 + BankAccount.COL_ACCNUM + " FROM " + BankAccount.TABLE
								 + " WHERE " + BankAccount.COL_BANK + " = ? AND "
				 				 			 + BankAccount.COL_ACCNUM + " = ? AND "
											 + BankAccount.COL_USERID + " = ?";
	
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(3, userID);
			ps.setString(1, bank);
			ps.setLong(2, num);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			System.out.println("[BANK] FIND DONE");
		}catch(SQLException e) {
			System.out.println("[BANK] FIND FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
	
	public void saveAccount(int userID, String bank, long num) {
		String query = "INSERT INTO " + BankAccount.TABLE + " (" + BankAccount.COL_USERID + ", "
																 + BankAccount.COL_BANK + ", "
																 + BankAccount.COL_ACCNUM  
																 + ") VALUES (?,?,?)"; 
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setString(2, bank);
			ps.setLong(3, num);
			
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[BANK] ADD DONE");
		}catch(SQLException e) {
			System.out.println("[BANK] ADD FAILED");
			e.printStackTrace();
		}
	}
}
