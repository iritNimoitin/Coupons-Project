package CRUD_DAO;

import java.util.ArrayList;
import DAO.Customer;
import exceptions.CouponSystemException;

public interface CustomersDAO {
	public int isCustomerExists(String email, String password) throws CouponSystemException;
	public int addCustomer(Customer customer) throws CouponSystemException;
	public void updateCustomer(Customer customer) throws CouponSystemException;
	public void deleteCustomer(int customerID) throws CouponSystemException;
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException;
	public Customer getOneCustomer(int customerID) throws CouponSystemException;
	public boolean isCustomerEmailExists(String email) throws CouponSystemException;
	public ArrayList<Customer> getCouponPurchases(int couponID) throws CouponSystemException;
}
