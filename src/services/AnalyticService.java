package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import model.Analytic;
import model.Order;
import model.OrderContent;
import model.Product;

public class AnalyticService {

	public ArrayList<Analytic> getAnalytics(String category, int[] months, int year) {
		ArrayList<Analytic> analytics = new ArrayList<Analytic>();
		
		String query = "SELECT CONCAT(P." + Product.COL_NAME + ", ' - ', P." + Product.COL_CATEGORY + ") AS Name, "
							  + "M.Total AS Month1, O.Total AS Month2, N.Total AS Month3 "
							  + "FROM " + Product.TABLE + " AS P "
							  + "LEFT JOIN (SELECT OC." + OrderContent.COL_PRODUCT + " AS Product, SUM(OC." + OrderContent.COL_QUANTITY + ") AS Total "
							  				+ "FROM " + OrderContent.TABLE + " AS OC, " + Order.TABLE + " AS O "
							  				+ "WHERE O." + Order.COL_ID + " = OC." + OrderContent.COL_ORDER + " AND "
							  				+ "MONTH(O." + Order.COL_CREATION + ") = ? AND YEAR(O." + Order.COL_CREATION + ") = ? "
							  				+ "GROUP BY Product) AS M ON P." + Product.COL_ID + " = M.Product "
							  + "LEFT JOIN (SELECT OC." + OrderContent.COL_PRODUCT + " AS Product, SUM(OC." + OrderContent.COL_QUANTITY + ") AS Total "
							  				+ "FROM " + OrderContent.TABLE + " AS OC, " + Order.TABLE + " AS O "
							  				+ "WHERE O." + Order.COL_ID + " = OC." + OrderContent.COL_ORDER + " AND "
							  				+ "MONTH(O." + Order.COL_CREATION + ") = ? AND YEAR(O." + Order.COL_CREATION + ") = ? "
							  				+ "GROUP BY Product) AS O ON P." + Product.COL_ID + " = O.Product "
							  + "LEFT JOIN (SELECT OC." + OrderContent.COL_PRODUCT + " AS Product, SUM(OC." + OrderContent.COL_QUANTITY + ") AS Total "
							  				+ "FROM " + OrderContent.TABLE + " AS OC, " + Order.TABLE + " AS O "
							  				+ "WHERE O." + Order.COL_ID + " = OC." + OrderContent.COL_ORDER + " AND "
							  				+ "MONTH(O." + Order.COL_CREATION + ") = ? AND YEAR(O." + Order.COL_CREATION + ") = ? "
							  				+ "GROUP BY Product) AS N ON P." + Product.COL_ID + " = N.Product "
							  + "WHERE P." + Product.COL_CATEGORY + " = ?"
							  + "ORDER BY Name";
		
		try {
			PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(query);
			
			ps.setInt(1, months[0]);
			ps.setInt(2, year);
			ps.setInt(3, months[1]);
			ps.setInt(4, year);
			ps.setInt(5, months[2]);
			ps.setInt(6, year);
			ps.setString(7, category);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				analytics.add(toAnalytics(rs));
			
			rs.close();
			ps.close();
			System.out.println("[ANALYTICS] ANALYTICS GET DONE");
		}catch(SQLException e) {
			System.out.println("[ANALYTICS] ANALYTICS GET FAILED");
			e.printStackTrace();
		}
		
		return analytics;
	}
	
	private Analytic toAnalytics(ResultSet rs) throws SQLException{
		Analytic a = new Analytic();
		
		a.setName(rs.getString("Name"));
		a.setMonth1(rs.getInt("Month1"));
		a.setMonth2(rs.getInt("Month2"));
		a.setMonth3(rs.getInt("Month3"));
		
		return a;
	}

}
