package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://127.0.0.1:3306/";
	private static String USERNAME = "root";
	private static String PASSWORD = "green1698";
	private static String DATABASE = "shopee";

	private static Connection connection = null;

	private DatabaseConnection() {
	}

	public static synchronized Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName(DRIVER_NAME);
				connection = DriverManager.getConnection(URL + DATABASE + "?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC",
						USERNAME, PASSWORD);
				System.out.println("[MYSQL] Connection successful");
			} catch (SQLException e) {
				System.out.println("[MYSQL] Connection failed!");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("[MYSQL] Connection failed!");
				e.printStackTrace();
			}
		}
		
		return connection;
	}
}
