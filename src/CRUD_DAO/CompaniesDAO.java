package CRUD_DAO;

import java.util.ArrayList;
import DAO.Company;
import exceptions.CouponSystemException;

public interface CompaniesDAO {
	public int isCompanyExists(String email, String password) throws CouponSystemException;
	public int addCompany(Company company) throws CouponSystemException;
	public void updateCompany(Company company) throws CouponSystemException;
	public void deleteCompany(int companyID) throws CouponSystemException;
	public ArrayList<Company> getAllCompanies() throws CouponSystemException;
	public Company getOneCompany(int companyID) throws CouponSystemException;
	public boolean isCompanyNameExists(String companyName) throws CouponSystemException;
	public boolean isCompanyEmailExists(String companyEmail) throws CouponSystemException;
}
