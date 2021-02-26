package facades;

import java.util.ArrayList;
import CRUD_DAO.CompaniesDBDAO;
import CRUD_DAO.CouponsDBDAO;
import CRUD_DAO.CustomersDBDAO;
import DAO.Company;
import DAO.Coupon;
import DAO.Customer;
import exceptions.CouponSystemException;

public class AdminFacade extends ClientFacade {
	
	public AdminFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		if(email.equals("admin@admin.com") && password.equals("admin")){
			return true;
		}
		return false;
	}
	
	public void addCompany(Company company) throws CouponSystemException {
		if(companiesDAO.isCompanyNameExists(company.getName())) {
			throw new CouponSystemException("The name of the company already exist in the system. Can't add company with the same name.");
		} else if (companiesDAO.isCompanyEmailExists(company.getEmail())) {
			throw new CouponSystemException("the email of the company already exist in the system. Can't add company with the same email.");
		} else {
			int id = companiesDAO.addCompany(company);
			company.setId(id);
		}
	}
	
	public void updateCompany(Company company) throws CouponSystemException {
		Company sorceCompany = companiesDAO.getOneCompany(company.getId());
		if(sorceCompany == null) {
			throw new CouponSystemException("The company with id: " + company.getId() + " not exist in the system.");
		}
		if(!sorceCompany.getName().equals(company.getName())) {
			throw new CouponSystemException("Can't update the name of the company.");
		}
		sorceCompany.setEmail(company.getEmail());
		sorceCompany.setPassword(company.getPassword());
		companiesDAO.updateCompany(sorceCompany);
	}
	
	/**
	 * first deletes all company coupons and the coupons purchases from database, 
	 * then deletes company from database
	 * @param email
	 * @param password
	 * @return true if company exists in database
	 * @throws CouponSystemException
	 */
	public void deleteCompany(int companyID) throws CouponSystemException {
		couponsDAO.deleteCompanyCouponsPurchases(companyID);
		couponsDAO.deleteAllCompanyCoupons(companyID);
		companiesDAO.deleteCompany(companyID);
	}
	
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		return companies;
	}
	
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Company company = companiesDAO.getOneCompany(companyID);
		return company;
	}
	
	public void addCustomer(Customer customer) throws CouponSystemException {
		if (customersDAO.isCustomerEmailExists(customer.getEmail())) {
			throw new CouponSystemException("the email of the customer already exist in the database. Can't add customer with the same email.");
		}
		int id = customersDAO.addCustomer(customer);
		customer.setId(id);
	}
	
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer sorceCustomer = customersDAO.getOneCustomer(customer.getId());
		if(sorceCustomer == null) {
			throw new CouponSystemException("The customer with id: " + customer.getId() + " not exist in the system.");
		}
		sorceCustomer.setEmail(customer.getEmail());
		sorceCustomer.setFirstName(customer.getFirstName());
		sorceCustomer.setLastName(customer.getLastName());
		sorceCustomer.setPassword(customer.getPassword());
		customersDAO.updateCustomer(sorceCustomer);
	}
	
	public void deleteCustomer(int customerID) throws CouponSystemException {
		couponsDAO.deleteAllCustomerPurchases(customerID);
		customersDAO.deleteCustomer(customerID);
	}
	
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		return customers;
	}
	
	public Customer getOneCustomer(int customerID) throws  CouponSystemException {
		Customer customer = customersDAO.getOneCustomer(customerID);
		return customer;
	}
	
	public ArrayList<Coupon> getCompanyCoupons(int companyID) throws CouponSystemException {
		ArrayList<Coupon> coupons = couponsDAO.getCompanyCoupons(companyID);
		return coupons;
	}
	
	public ArrayList<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException {
		ArrayList<Coupon> coupons = couponsDAO.getCustomerCoupons(customerID);
		return coupons;
	}
}
