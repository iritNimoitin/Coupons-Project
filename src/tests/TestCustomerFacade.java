package tests;

import java.util.ArrayList;

import DAO.Category;
import DAO.Coupon;
import exceptions.CouponSystemException;
import facades.CustomerFacade;

public class TestCustomerFacade {
	
	/**
	 * Testing methods of CustomerFacade:
	 * getCoupons(), purchaseCoupon(coupon), getCustomerCoupons(), 
	 * getCustomerCoupons(category), getCustomerCoupons(maxPrice),
	 * getCustomerDetails()
	 * @param customerFacade
	 */
	public static void start(CustomerFacade customerFacade) {
		ArrayList<Coupon> coupons = null;
		try {
			coupons = customerFacade.getCoupons();
			System.out.println("\nall coupons:");
			for(Coupon c: coupons) {
				System.out.println("	" + c.toString());
			}
			if(coupons.size() == 0) {
				System.out.println("	there are no coupons in the system");
			}
			System.out.println("getCoupons succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			ArrayList<Coupon> couponsByCategory = customerFacade.getCouponsByCategory(Category.Vacation);
			System.out.println("\nall coupons with category: " + Category.Vacation.toString());
			for(Coupon c: couponsByCategory) {
				System.out.println("	" + c.toString());
			}
			if(couponsByCategory.size() == 0) {
				System.out.println("	there are no coupons with category: " + Category.Vacation.toString() + ", in the system");
			}
			System.out.println("getCouponsByCategory succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			customerFacade.purchaseCoupon(coupons.get(1));
			customerFacade.purchaseCoupon(coupons.get(2));
			System.out.println("purchaseCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Coupon> customerCoupons = null;
		try {
			customerCoupons = customerFacade.getCustomerCoupons();
			System.out.println("\ncustomer coupons:");
			for(Coupon c: customerCoupons) {
				System.out.println("	" + c.toString());
			}
			if(customerCoupons.size() == 0) {
				System.out.println("	this customer don't purchase coupons");
			}
			System.out.println("getCustomerCoupons succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Coupon> customerCouponsByCategory = null;
		try {
			Category category = Category.Restaurant;
			customerCouponsByCategory = customerFacade.getCustomerCoupons(category);
			System.out.println("\ncustomer coupons by category: " + category.toString());
			for(Coupon c: customerCouponsByCategory) {
				System.out.println("	" + c.toString());
			}
			if(customerCouponsByCategory.size() == 0) {
				System.out.println("	this customer don't purchase coupons with category " + category);
			}
			System.out.println("getCustomerCoupons by category succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Coupon> customerCouponsMaxPrice = null;
		try {
			double maxPrice = 90;
			customerCouponsMaxPrice = customerFacade.getCustomerCoupons(maxPrice);
			System.out.println("\ncustomer coupons by max price: " + maxPrice);
			for(Coupon c: customerCouponsMaxPrice) {
				System.out.println("	" + c.toString());
			}
			if(customerCouponsByCategory.size() == 0) {
				System.out.println("	this customer don't purchase coupons with price less then " + maxPrice);
			}
			System.out.println("getCustomerCoupons by max price succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			String title = "coupon2";
			Coupon couponByTitle = null;
			couponByTitle =	customerFacade.getCouponByTitle(title);
			if(couponByTitle != null) {
				System.out.println("coupon with title: " + title + " is: " + couponByTitle.toString());
			} else {
				System.out.println("There is not coupon with title: " + title + " in the database");
			}
			System.out.println("getCouponByTitle succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		try {
			System.out.println(customerFacade.getCustomerDetails().toString());
			System.out.println("getCustomerDetails succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

}
