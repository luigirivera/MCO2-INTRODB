package services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.DatabaseConnection;
import model.Card;

public class CardsService {
	
	public void delete(int userID, int cardsID)
	{
		String query = "DELETE FROM " + Card.TABLE + " WHERE " + Card.COL_USERID + " = ? AND "
															   + Card.COL_CARDSID + " = ?";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			ps.setInt(2, cardsID);
			
			ps.executeUpdate();
			ps.close();
			System.out.println("[CARD] DELETE SUCCESS");
		} catch (SQLException e) {
			System.out.println("[CARD] DELETE FAILED");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Card> getCardsOfUser(int userID)
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		
		String query = "SELECT " + Card.COL_CARDSID + ", "
								 + Card.COL_CARDNUMBER + ", "
								 + Card.COL_CVC + ", "
								 + Card.COL_EXPIRY + " FROM " + Card.TABLE + " WHERE " + Card.COL_USERID + " = ? ORDER BY " + Card.COL_CARDSID + " ASC";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				cards.add(toCard(rs));
			
			ps.close();
			rs.close();
			System.out.println("[CARD] GET CARDS DONE");
		}catch(SQLException e) {
			System.out.println("[CARD] GET CARDS FAILED");
			e.printStackTrace();
		}
		
		return cards;
	}
	
	private Card toCard(ResultSet rs) throws SQLException{
		Card card = new Card();
		
		card.setCardID(rs.getInt(Card.COL_CARDSID));
		card.setCardNumber(rs.getLong(Card.COL_CARDNUMBER));
		card.setCvc(rs.getBoolean(Card.COL_CVC));
		card.setExpiry(rs.getDate(Card.COL_EXPIRY));
		
		return card;
	}
	
	public void saveCard(int userID, long number, boolean cvc, Date expiry)
	{
		String query = "INSERT INTO " + Card.TABLE + " (" + Card.COL_USERID + ", "
														  + Card.COL_CARDNUMBER + ", "
														  + Card.COL_CVC + ", "
														  + Card.COL_EXPIRY
														  + ") VALUES(?,?,?,?)";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);

			ps.setInt(1, userID);
			ps.setLong(2, number);
			ps.setBoolean(3, cvc);
			ps.setDate(4, expiry, Calendar.getInstance());
			ps.executeUpdate();
			
			ps.close();
			System.out.println("[CARD] ADD DONE");
		}catch(SQLException e) {
			System.out.println("[CARD] ADD FAILED");
			e.printStackTrace();
		}
	}
	
	public boolean doesCardExist(int userID, long number)
	{
		boolean found = false;
		String query = "SELECT " + Card.COL_CARDNUMBER + " FROM " + Card.TABLE + " WHERE "
																			   + Card.COL_CARDNUMBER + " = ?"
																			   + " AND " + Card.COL_USERID + " = ?";
	
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setLong(1, number);
			ps.setInt(2, userID);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				found = true;
			
			ps.close();
			rs.close();
			System.out.println("[CARD] FIND DONE");
		}catch(SQLException e) {
			System.out.println("[CARD] FIND FAILED");
			e.printStackTrace();
		}
		
		return found;
	}
}
