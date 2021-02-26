package CRUD_DAO;

import java.util.ArrayList;
import DAO.Category;
import DAO.Coupon;
import exceptions.CouponSystemException;


public interface CouponsDAO {
	public int addCoupon(Coupon coupon) throws CouponSystemException;
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	public void deleteCoupon(int couponID) throws CouponSystemException;
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException;
	public Coupon getOneCoupon(int couponID) throws CouponSystemException;
	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	public void deleteAllCouponPurchases(int couponID) throws CouponSystemException;
	public ArrayList<Coupon> getCouponsByCategory(Category category) throws CouponSystemException;
	public ArrayList<Coupon> getExpiredCoupons() throws CouponSystemException;
	public void deleteCompanyCouponsPurchases(int companyID) throws CouponSystemException;
	public ArrayList<Coupon> getCompanyCoupons(int companyID) throws CouponSystemException;
	public void deleteAllCompanyCoupons(int companyID) throws CouponSystemException;
	public ArrayList<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException;
	public void deleteAllCustomerPurchases(int customerID) throws CouponSystemException;
	public Coupon getCoupon(String title) throws CouponSystemException;
	public ArrayList<Coupon> getCompanyCouponsByCategory(int companyID, Category category) throws CouponSystemException;
	public ArrayList<Coupon> getCompanyCouponsByMaxPrice(int companyID, double maxPrice) throws CouponSystemException;
	public boolean isPurchaseExists(int customerID, int couponID) throws CouponSystemException;
	public ArrayList<Coupon> getCustomerCouponsByCategory(int customerID, Category category) throws CouponSystemException;
	public ArrayList<Coupon> getCustomerCouponsByMaxPrice(int customerID, double maxPrice) throws CouponSystemException;
}
