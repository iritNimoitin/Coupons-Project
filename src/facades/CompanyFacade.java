package facades;

import java.util.ArrayList;
import CRUD_DAO.CompaniesDBDAO;
import CRUD_DAO.CouponsDBDAO;
import DAO.Category;
import DAO.Company;
import DAO.Coupon;
import exceptions.CouponSystemException;

public class CompanyFacade extends AdminFacade {
	
	private int companyID;
	
	public CompanyFacade() {
		couponsDAO = new CouponsDBDAO();
		companiesDAO = new CompaniesDBDAO();
	}
	
	/**
	 * first checks if company exists in database,
	 * then initialize the filed companyID to be the company id
	 * @param email
	 * @param password
	 * @return true if company exists in database
	 * @throws CouponSystemException
	 */
	public boolean login(String email, String password) throws CouponSystemException {
		this.companyID = companiesDAO.isCompanyExists(email, password);
		if(companyID >= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adds @param(coupon) of the company to database,
	 * and updates the id and the company id of the @param(coupon)
	 * @throws CouponSystemException in case the coupon title of the company already exist in the database
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Coupon companyCoupon = couponsDAO.getCoupon(coupon.getTitle());
		if(companyCoupon != null) {
			throw new CouponSystemException("The coupon title : " + coupon.getTitle() +  " of company with id: " + companyID  + " already exist in the database. Can't add coupon with the same title.");
		}
		coupon.setCompanyID(companyID);
		int id = couponsDAO.addCoupon(coupon);
		coupon.setId(id);
	}
	
	/**
	 * Updates the @param(coupon) properties in the database
	 * @throws CouponSystemException in case the coupon not exists in the database
	 * or belongs to other company
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon sorceCoupon = couponsDAO.getOneCoupon(coupon.getId());
		if(sorceCoupon == null) {
			throw new CouponSystemException("The coupon with id: " + coupon.getId() + " not exists in the database.");
		}
		if(coupon.getCompanyID() != companyID) {
			throw new CouponSystemException("The coupon with id: " +coupon.getId()+ " belongs to other company");
		}
		sorceCoupon.setTitle(coupon.getTitle());
		sorceCoupon.setCategory(coupon.getCategory());
		sorceCoupon.setDescription(coupon.getDescription());
		sorceCoupon.setAmount(coupon.getAmount());
		sorceCoupon.setPrice(coupon.getPrice());
		sorceCoupon.setStartDate(coupon.getStartDate());
		sorceCoupon.setEndDate(coupon.getEndDate());
		sorceCoupon.setImage(coupon.getImage());
		couponsDAO.updateCoupon(sorceCoupon);
	}
	
	public void deleteCoupon(int couponID) throws CouponSystemException {
		couponsDAO.deleteAllCouponPurchases(couponID);
		couponsDAO.deleteCoupon(couponID);
    }
	
	public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemException {
		ArrayList<Coupon> companyCoupons = couponsDAO.getCompanyCoupons(companyID);
		return companyCoupons;
	}
		
	public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		ArrayList<Coupon> coupons = couponsDAO.getCompanyCouponsByCategory(companyID, category);
		return coupons;
	}
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		ArrayList<Coupon> coupons = couponsDAO.getCompanyCouponsByMaxPrice(companyID, maxPrice);
		return coupons;
	}
	
	public Company getCompanyDetails() throws CouponSystemException {
		Company company = companiesDAO.getOneCompany(companyID);
		return company;
	}
}


