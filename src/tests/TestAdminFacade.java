package tests;

import java.util.ArrayList;

import DAO.Company;
import DAO.Coupon;
import DAO.Customer;
import exceptions.CouponSystemException;
import facades.AdminFacade;

public class TestAdminFacade {
	
	/**
	 * Adds companies and customers to database,
	 * testing the methods addCompany(company) and addCustomer(customer) of AdminFacade
	 * @param adminFacade
	 */
	public static void initializeCompaniesAndCustomers(AdminFacade adminFacade) {
		for(int i = 0; i < 5; i++) {
			try {
				adminFacade.addCompany(createCompany("company"+i+"@gmail.com", "company"+i, "pass"+i));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("addCompany succeeded"); 
		for(int i = 0; i < 5; i++) {
			try {
				adminFacade.addCustomer(createCustomer("customer"+i+"@gmail.com", "firstName"+i, "lastName"+i, "pass"+i));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("addCustomer succeeded"); 
	}
	
	/**
	 * Testing methods of AdminFacade:
	 * getAllCompanies(), getAllCustomers(), deleteCompany(companyID),
	 * deleteCustomer(customerID), getOneCompany(companyID), 
	 * getOneCustomer(customerID), updateCompany(company), updateCustomer(customer),
	 * getCompanyCoupons(companyID), getCustomerCoupons(customerID)
	 * @param adminFacade
	 */
	public static void start(AdminFacade adminFacade) {
		ArrayList<Company> companies = null;
		try {
			companies = adminFacade.getAllCompanies();
			System.out.println("getAllCompanies succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Customer> customers = null;
		try {
			customers = adminFacade.getAllCustomers();
			System.out.println("getAllCustomers succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(companies != null && companies.size() > 0) {
				adminFacade.deleteCompany(companies.get(3).getId());
				System.out.println("deleteCompany succeeded");
			} else {
				System.out.println("ERROR: companies array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(customers != null && customers.size() > 0) {
				adminFacade.deleteCustomer(customers.get(3).getId());
				System.out.println("deleteCustomer succeeded");
			} else {
				System.out.println("ERROR: customers array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(companies != null && companies.size() > 0) {
				System.out.println(adminFacade.getOneCompany(companies.get(1).getId()).toString());
				System.out.println("getOneCompany succeeded");
			} else {
				System.out.println("ERROR: companies array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(customers != null && customers.size() > 0) {
				System.out.println(adminFacade.getOneCustomer(customers.get(1).getId()).toString());
				System.out.println("getOneCustomer succeeded");
			} else {
				System.out.println("ERROR: customers array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(companies != null && companies.size() > 0) {
				companies.get(1).setPassword("passUpdate");
				adminFacade.updateCompany(companies.get(1));
				System.out.println("updateCompany succeeded");
			} else {
				System.out.println("ERROR: companies array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(customers != null && customers.size() > 0) {
				customers.get(1).setPassword("passUpdate");
				adminFacade.updateCustomer(customers.get(1));
				System.out.println("updateCustomer succeeded");
			} else {
				System.out.println("ERROR: customers array is empty");
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Coupon> companyCoupons = null;
		try {
			companyCoupons = adminFacade.getCompanyCoupons(companies.get(2).getId());
			System.out.println("\ncompany coupons:");
			for(Coupon c: companyCoupons) {
				System.out.println("	" + c.toString());
			}
			if(companyCoupons.size() == 0) {
				System.out.println("	this company don't have coupons");
			}
			System.out.println("getCompanyCoupons succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Coupon> customerCoupons = null;
		try {
			customerCoupons = adminFacade.getCustomerCoupons(customers.get(2).getId());
			System.out.println("\ncustomer coupons:");
			for(Coupon c: customerCoupons) {
				System.out.println("	" + c.toString());
			}
			if(customerCoupons.size() == 0) {
				System.out.println("	this customer don't have coupons");
			}
			System.out.println("getCustomerCoupons succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static Company createCompany(String email, String name, String password) {
		Company company = new Company(0);
		company.setEmail(email);
		company.setName(name);
		company.setPassword(password);
		return company;
	}
	
	private static Customer createCustomer(String email, String firstName, String lastName, String password) {
		Customer customer = new Customer(0);
		customer.setEmail(email);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setPassword(password);
		return customer;
	}
	
}
