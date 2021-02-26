package jobs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import CRUD_DAO.CouponsDAO;
import CRUD_DAO.CouponsDBDAO;
import DAO.Coupon;
import exceptions.CouponSystemException;


/**
 * The daily job task that goes over the coupons daily
 * to check if any coupons have expired and then deletes them
 */
public class CouponExpirationDailyJob implements Runnable {
	
	private boolean quit;
	private CouponsDAO couponsDAO;
	
	public CouponExpirationDailyJob() {
		super();
		this.quit = false;
		this.couponsDAO = new CouponsDBDAO();
	}
	
	@Override
	public void run() {
		ArrayList<Coupon> coupons = new ArrayList<>();
		while(!quit) {
			try {
				coupons = couponsDAO.getExpiredCoupons();
				for(Coupon coupon: coupons) {
					if(coupon.getEndDate().isBefore(LocalDate.now())) {
						couponsDAO.deleteAllCouponPurchases(coupon.getId());
						couponsDAO.deleteCoupon(coupon.getId());
					}
				}
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			try {
				Thread.sleep(TimeUnit.MINUTES.toMillis(1));
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void stop() {
		this.quit = true;
	}
}
