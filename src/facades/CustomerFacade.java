package facades;

import java.time.LocalDate;
import java.util.ArrayList;
import CRUD_DAO.CouponsDBDAO;
import CRUD_DAO.CustomersDBDAO;
import DAO.Category;
import DAO.Coupon;
import DAO.Customer;
import exceptions.CouponSystemException;

public class CustomerFacade extends ClientFacade {
	
	private int customerID;

	public CustomerFacade() {
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}
	
	/**
	 * first checks if customer exists in database,
	 * then initialize the filed customerID to be the customer id
	 * @param email
	 * @param password
	 * @return true if customer exists in database
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		this.customerID = customersDAO.isCustomerExists(email, password);
		if(customerID >= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adds coupon purchase to database if:
	 * 1. The customer does not purchased it before
	 * 2. The amount of the coupon is above zero
	 * 3. The coupon is still valid
	 * Then reduce the amount of the coupon by one,
	 * if now the coupon amount is zero, delete coupon
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon (Coupon coupon) throws CouponSystemException {
		if (couponsDAO.isPurchaseExists(customerID, coupon.getId())) {
			throw new CouponSystemException("You already purchesed this coupon " + coupon.toString() + " in the past . you can't purches the same coupon more than once");
		}
		if (coupon.getAmount() == 0) {
			throw new CouponSystemException("The coupon " + coupon.toString() + "  is currently out of stock. Please select another coupon"); 
		}
		if (coupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponSystemException("The expiration date of the coupon : " + coupon.toString()+ " has arrived so it is not possible to purchase it. please select another coupon"); // TODO: write exception massage
		}
		couponsDAO.addCouponPurchase(customerID, coupon.getId());
		if(coupon.getAmount() <= 1) {
			couponsDAO.deleteCoupon(coupon.getId());
		} else {
			coupon.setAmount(coupon.getAmount()-1);
			couponsDAO.updateCoupon(coupon);
		}
	}
	
	public ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException {
		ArrayList<Coupon> coupons = couponsDAO.getCustomerCoupons(customerID);
	    return coupons;
	}	
	
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {
    	ArrayList<Coupon> coupons = couponsDAO.getCustomerCouponsByCategory(customerID, category);
		return coupons;
	}
    
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException {
    	ArrayList<Coupon> coupons = couponsDAO.getCustomerCouponsByMaxPrice(customerID, maxPrice);
		return coupons;
	}
    
    public Customer getCustomerDetails() throws CouponSystemException {
    	Customer customer = customersDAO.getOneCustomer(customerID);
		return customer;
    }
    
    public ArrayList<Coupon> getCoupons() throws CouponSystemException{
    	ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
    	return coupons;
    }
    
    public ArrayList<Coupon> getCouponsByCategory(Category category) throws CouponSystemException{
    	ArrayList<Coupon> coupons = couponsDAO.getCouponsByCategory(category);
    	return coupons;
    }
    
    public Coupon getCouponByTitle(String title) throws CouponSystemException {
    	Coupon coupon = couponsDAO.getCoupon(title);
    	return coupon;
    }
}
