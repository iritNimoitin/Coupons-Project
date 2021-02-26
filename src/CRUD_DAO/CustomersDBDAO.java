package CRUD_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.Customer;
import connections.ConnectionPool;
import exceptions.CouponSystemException;

public class CustomersDBDAO implements CustomersDAO {

	/**
	 * Checks if a customer exists in the database
	 * @param email
	 * @param password
	 * @return customer id, if found
	 * @throws CouponSystemException
	 */
	@Override
	public int isCustomerExists(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select ID from CUSTOMERS where EMAIL = ? and PASSWORD = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("ID");
			} else {
				return -1;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Finding customer with email: " + email + " and password: " + password + ", failed ", e); 
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	@Override
	public int addCustomer(Customer customer) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into CUSTOMERS values(0, ?, ?, ? ,?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.executeUpdate();
			
			ResultSet rsId = pstmt.getGeneratedKeys();
			rsId.next();
			int id = rsId.getInt(1);
			customer.setId(id);
			return id;
		} catch (SQLException e) {
			throw new CouponSystemException("Adding customer : " + customer.toString() + " to data base, failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * Update customer first-name, last-name, email and password
	 * @param company
	 * @throws CouponSystemException in case customer was not found in database
	 */
	@Override
	public void updateCustomer(Customer customer) throws  CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update CUSTOMERS set FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ? where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("update customer " + customer.toString() + " faild because it is not in the data base");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("update customer : " + customer.toString() + " faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * Deletes customer with @param(customerID) from database
	 * @throws CouponSystemException in case customer was not found in database
	 */
	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException {
		Connection con =  null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("delete customer " + customerID + " faild because it is not in the data base");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("delete customer with id: " + customerID + " faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Customer> customeres = new ArrayList<>();
			while(rs.next()) {
				Customer customer = new Customer(rs.getInt("ID"));
				customer.setFirstName(rs.getString("FIRST_NAME"));
				customer.setLastName(rs.getString("LAST_NAME"));
				customer.setEmail(rs.getString("EMAIL"));
				customer.setPassword(rs.getString("PASSWORD"));
				customeres.add(customer);
			}
			return customeres;
		} catch (SQLException e) {
			throw new CouponSystemException("getting all customeres: faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection(); 
			String sql = "select * from CUSTOMERS where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Customer customer = new Customer(customerID);
				customer.setFirstName(rs.getString("FIRST_NAME"));
				customer.setLastName(rs.getString("LAST_NAME"));
				customer.setEmail(rs.getString("EMAIL"));
				customer.setLastName(rs.getString("PASSWORD"));
				return customer;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Getting customer with id: " + customerID + ", failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return null;
	}
	
	@Override
	public boolean isCustomerEmailExists(String email) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select ID from CUSTOMERS where EMAIL = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Finding customer with email: " + email + ", failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	@Override
	public ArrayList<Customer> getCouponPurchases(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con =  ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS inner join CUSTOMERS_VS_COUPONS on CUSTOMERS_VS_COUPONS.CUSTOMER_ID = CUSTOMERS.ID where CUSTOMERS_VS_COUPONS.COUPON_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Customer> customers = new ArrayList<>();
			while(rs.next()) {
				Customer customer = new Customer(rs.getInt("ID"));
				customer.setFirstName(rs.getString("FIRST_NAME"));
				customer.setLastName(rs.getString("LAST_NAME"));
				customer.setEmail(rs.getString("EMAIL"));
				customer.setLastName(rs.getString("PASSWORD"));
				customers.add(customer);
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("getting all coupon purcheses with the id " + couponID +" failed " ,e ); 
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
}


