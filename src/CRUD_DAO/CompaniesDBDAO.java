package CRUD_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DAO.Company;
import connections.ConnectionPool;
import exceptions.CouponSystemException;


public class CompaniesDBDAO implements CompaniesDAO {
	
	/**
	 * Checks if a company exists in the database
	 * @param email
	 * @param password
	 * @return company id, if found
	 * @throws CouponSystemException
	 */
	@Override
	public int isCompanyExists(String email, String password) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select ID from COMPANIES where EMAIL = ? and PASSWORD = ?";
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
			throw new CouponSystemException("Finding company with email: " + email + " and password: " + password + ", failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public int addCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "insert into COMPANIES values(0, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.executeUpdate();
			
			ResultSet rsId = pstmt.getGeneratedKeys();
			rsId.next();
			int id = rsId.getInt(1);
			company.setId(id);
			return id;
		} catch (SQLException e) {
			throw new CouponSystemException("Adding company: " + company.toString() + " to data base, failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
     }
	
	/**
	 * Update company name, email and password by @param(company) properties
	 * @throws CouponSystemException in case company was not found in database
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "update COMPANIES set NAME = ?, EMAIL = ?, PASSWORD = ? where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("Update company: " + company.toString() + ", failed because its not in the database");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Update company: " + company.toString() + ", failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * Deletes company with @param(companyID) from database
	 * @throws CouponSystemException in case company was not found in database
	 */
	@Override
	public void deleteCompany(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection(); 
			String sql = "delete from COMPANIES where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			int rowCount = pstmt.executeUpdate();
			if (rowCount == 0) {
				throw new CouponSystemException("Delete company with id: " + companyID + ", failed because its not in the database");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Delete company with id: " + companyID + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from COMPANIES";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<Company> companies = new ArrayList<>();
			while(rs.next()) {
				Company company = new Company(rs.getInt("ID"));
				company.setName(rs.getString("NAME"));
				company.setEmail(rs.getString("EMAIL"));
				company.setPassword(rs.getString("PASSWORD"));
				companies.add(company);
			}
			return companies;
		} catch (SQLException e) {
			throw new CouponSystemException("Getting all companies, failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection(); 
			String sql = "select * from COMPANIES where ID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, companyID);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Company company = new Company(companyID);
				company.setName(rs.getString("NAME"));
				company.setEmail(rs.getString("EMAIL"));
				company.setPassword(rs.getString("PASSWORD"));
				return company;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get company with id: " + companyID + ", failed", e);
		}finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return null;
	}
	
	@Override
	public boolean isCompanyNameExists(String companyName) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select ID from COMPANIES where NAME = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, companyName);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Finding company name: " + companyName + ", failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	@Override
	public boolean isCompanyEmailExists(String companyEmail) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			//String sql = "select ID from COMPANIES where exists (select EMAIL from COMPANIES where EMAIL = ?)";//TODO: check if need "exists"
			String sql = "select ID from COMPANIES where EMAIL = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, companyEmail);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Finding company email: " + companyEmail + ", failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
}
