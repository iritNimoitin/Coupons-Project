package CRUD_DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import DAO.Category;
import DAO.Coupon;
import connections.ConnectionPool;
import exceptions.CouponSystemException;

public class CouponsDBDAO implements CouponsDAO {

	@Override
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into COUPONS values(0, ?, ?, ? ,? ,? ,? ,? ,? ,?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, coupon.getCompanyID());
			pstmt.setInt(2, Category.categoryNum(coupon.getCategory()));
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();
			
			ResultSet rsId = pstmt.getGeneratedKeys();
			rsId.next();
			int id = rsId.getInt(1);
			coupon.setId(id);
			return id;
		} catch (SQLException e) {
			throw new CouponSystemException("Adding coupon: " + coupon.toString() + " to data base, failed ", e );
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * Update coupon properties
	 * @param coupon
	 * @throws CouponSystemException in case coupon was not found in database
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update COUPONS set COMPANY_ID = ?, CATEGORY_ID = ?, TITLE = ?, DESCRIPTION = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, PRICE = ?, IMAGE = ? where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coupon.getCompanyID());
			pstmt.setInt(2, Category.categoryNum(coupon.getCategory()));
			pstmt.setString(3, coupon.getTitle());
			pstmt.setString(4, coupon.getDescription());
			pstmt.setDate(5, Date.valueOf(coupon.getStartDate()));
			pstmt.setDate(6, Date.valueOf(coupon.getEndDate()));
			pstmt.setInt(7, coupon.getAmount());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.setInt(10, coupon.getId());
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("update coupon " + coupon.toString() + " faild because it is not in the database");
			}
		} catch (SQLException e) {
			throw new CouponSystemException( "update coupon: " + coupon.toString() + " faild",e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * Deletes coupon with @param(couponID) from database
	 * @throws CouponSystemException in case coupon was not found in database
	 */
	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from COUPONS where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("delete coupon " + couponID + " faild because it is not in the data base");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("delete coupon with id: " + couponID + " faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException  {
		Connection con = null;
		try {
		con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("getting all coupons: faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Coupon coupon = new Coupon(couponID);
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				return coupon;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("get coupon with id: " + couponID + " faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return null;
	}
	
	/**
	 * Add purchase of coupon with @param(couponID) by customer with @param(customerID) to the database
	 * @throws CouponSystemException
	 */
	@Override
	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
		    con =  ConnectionPool.getInstance().getConnection();
			String sql = "insert into CUSTOMERS_VS_COUPONS values(?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Adding coupon Purchase to data base by customer with the id : " + customerID  + " and with coupon id " + couponID + " failed " ,e); 
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Deletes purchase of coupon with @param(couponID) by customer with @param(customerID) from the database
	 * @throws CouponSystemException
	 */
	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
		con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSROMERS_VS_COUPONS where CUSTOMER_ID = ?, COUPON_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("delete Coupon Purchase with the id" + couponID + "by customer with the id " + customerID + " faild because it is not in the database");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("delliting coupon Purchase from data base by customer with the id : " + customerID  + " and with coupon id " + couponID + " failed " ,e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Deletes all purchases of coupon with @param(couponID) from the database
	 * @throws CouponSystemException in case the purchase was not found in database
	 */
	@Override
	public void deleteAllCouponPurchases(int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSROMERS_VS_COUPONS where COUPON_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, couponID);
		} catch (SQLException e) {
			throw new CouponSystemException("delete all coupon purchase with id: " + couponID + " faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	@Override
	public ArrayList<Coupon> getCouponsByCategory(Category category) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where CATEGORY_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Category.categoryNum(category));
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting coupons with category: " + category.toString() + ", faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves all Expired coupons from database 
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getExpiredCoupons() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where END_DATE < ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, Date.valueOf(LocalDate.now()));
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<Coupon>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting coupons with end date less then: " + LocalDate.now() + ", faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Deletes all company coupons that have been purchased
	 * @param companyID
	 * @throws CouponSystemException
	 */
	@Override
	public void deleteCompanyCouponsPurchases(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSTOMERS_VS_COUPONS where COUPON_ID in (select COUPONS.ID from COUPONS join COMPANIES on COMPANIES.ID = COUPONS.COMPANY_ID where COMPANIES.ID = ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Delete all coupon purchase with company id: " + companyID +  ", faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	@Override
	public ArrayList<Coupon> getCompanyCoupons(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where COMPANY_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Gettong all coupons by company with id: " + companyID + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	@Override
	public void deleteAllCompanyCoupons(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from COUPONS where COMPANY_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
		} catch (SQLException | CouponSystemException e) {
			throw new CouponSystemException("delete comapny coupons with where company id is: " + companyID + " faild", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves all coupons that customer with @param(customerID) purchased
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con=ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS inner join CUSTOMERS_VS_COUPONS on CUSTOMERS_VS_COUPONS.COUPON_ID = COUPONS.ID where CUSTOMERS_VS_COUPONS.CUSTOMER_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting all coupons by customer with id: " + customerID + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Deletes all coupons that customer with @param(customerID) purchased
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public void deleteAllCustomerPurchases(int customerID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "delete from CUSROMERS_VS_COUPONS where CUSTOMER_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
		} catch (SQLException e) {
			throw new CouponSystemException("delete all customer purchases where customer id is: " + customerID + " faild.", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves coupon with @param(title) from database
	 * @param title
	 * @return coupon if found and null if coupon with @param(title) does not exists in the database
	 * @throws CouponSystemException
	 */
	@Override
	public Coupon getCoupon(String title) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where TITLE = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				return coupon;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Getting coupon with title: " + title + ", failed ", e); 
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves all company coupons by category from database
	 * @param companyID
	 * @param category
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getCompanyCouponsByCategory(int companyID, Category category) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where COMPANY_ID = ? and CATEGORY_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.setInt(2, Category.categoryNum(category));
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting all coupons by company with id: " + companyID + " and category: " + category + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves all company coupons from database that their price are less then @param(maxPrice) 
	 * @param companyID
	 * @param maxPrice
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyID, double maxPrice) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS where COMPANY_ID = ? and PRICE <= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting all coupons by company with id: " + companyID + " and price less then: " + maxPrice + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Checks if purchase of coupon exists in database
	 * @param customerID
	 * @param couponID
	 * @return true if exists
	 * @throws CouponSystemException
	 */
	@Override
	public boolean isPurchaseExists(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from CUSTOMERS_VS_COUPONS where CUSTOMER_ID = ? and COUPON_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Finding purchase with customer id: " + customerID + " and coupon id: " + couponID + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves all customer coupons by category from database
	 * @param customerID
	 * @param category
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getCustomerCouponsByCategory(int customerID, Category category) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS inner join CUSTOMERS_VS_COUPONS on CUSTOMERS_VS_COUPONS.COUPON_ID = COUPONS.ID where CUSTOMERS_VS_COUPONS.CUSTOMER_ID = ? and COUPONS.CATEGORY_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, Category.categoryNum(category));
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting all coupons by customer with id: " + customerID + " and category: " + category + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	/**
	 * Retrieves all customer coupons from database that their price are less then @param(maxPrice)
	 * @param customerID
	 * @param maxPrice
	 * @return ArrayList of coupons
	 * @throws CouponSystemException
	 */
	@Override
	public ArrayList<Coupon> getCustomerCouponsByMaxPrice(int customerID, double maxPrice) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COUPONS inner join CUSTOMERS_VS_COUPONS on CUSTOMERS_VS_COUPONS.COUPON_ID = COUPONS.ID where CUSTOMERS_VS_COUPONS.CUSTOMER_ID = ? and COUPONS.PRICE <= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, customerID);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Coupon> coupons = new ArrayList<>();
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getInt("ID"));
				coupon.setCompanyID(rs.getInt("COMPANY_ID"));
				coupon.setCategory(Category.values()[rs.getInt("CATEGORY_ID")-1]);
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setDescription(rs.getString("DESCRIPTION"));
				LocalDate startDate = LocalDate.parse(rs.getDate("START_DATE").toString());
				coupon.setStartDate(startDate);
				LocalDate endDate = LocalDate.parse(rs.getDate("END_DATE").toString());
				coupon.setEndDate(endDate);
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting all coupons by customer with id: " + customerID + " and price less than: " + maxPrice + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
}
