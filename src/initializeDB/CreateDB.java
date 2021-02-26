package initializeDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.Category;
import connections.ConnectionPool;
import exceptions.CouponSystemException;

public class CreateDB {
	
	/**
	 * First creates database,
	 * then creates tables: 
	 * categories, companies, customers, coupons, coustomers_vs_coupons
	 * Finally inserts all categories to table "categories" 
	 * @throws CouponSystemException
	 */
	public static void create() throws CouponSystemException {
		String url = "jdbc:mysql://localhost:3306?serverTimezone=Israel";
		String user = "irit13";
		String password = "1234";

		try (Connection con = DriverManager.getConnection(url, user, password);) {
			Statement statement = con.createStatement();
			String dbName = "coupon_system";
			String sql = "create database " + dbName;

			statement.executeUpdate(sql);
			
			statement.executeUpdate(CreateTables.categories(dbName));
			statement.executeUpdate(CreateTables.companies(dbName));
			statement.executeUpdate(CreateTables.customers(dbName));
			statement.executeUpdate(CreateTables.coupons(dbName));
			statement.executeUpdate(CreateTables.coustomers_vs_coupons(dbName));
		} catch (SQLException e) {
			throw new CouponSystemException("Failed in creating database with url: " + url,e );
		}
		
		Connection con2 = null;
		try {
			con2 = ConnectionPool.getInstance().getConnection();
			for (Category category: Category.values()) {
				String sql = "insert into CATEGORIES values(0, ?)";
				PreparedStatement pstmt = con2.prepareStatement(sql);
				pstmt.setString(1, category.toString());
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException("Adding category to data base, failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con2);
		}
	}
}
