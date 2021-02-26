package tests;
import connections.ConnectionPool;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CustomerFacade;
import facades.LoginManager;
import initializeDB.CreateDB;
import jobs.CouponExpirationDailyJob;

public class Test {

	public static void testAll() {
		System.out.println("***WELCOME TO MY COUPON PROJECT***\n");
		CouponExpirationDailyJob job = new CouponExpirationDailyJob();
		Thread thread = new Thread(job);
		try {
			CreateDB.create();
			System.out.println("Database was created successfully"); 
			ConnectionPool.getInstance();
			
			thread.start();
			System.out.println("Coupon-Expiration-Daily-Job-thread started");
			ClientFacade facade;
			facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("Administrator"));
			if(facade instanceof AdminFacade) {
				System.out.println("\n--------------------Admin was able to login successfully--------------------");
				TestAdminFacade.initializeCompaniesAndCustomers((AdminFacade) facade);
			}
			facade = LoginManager.getInstance().login("company2@gmail.com", "pass2", ClientType.valueOf("Company"));
			if(facade instanceof CompanyFacade) {
				System.out.println("\n--------------------company2 was able to login successfully--------------------");
				TestCompanyFacade.initializeCoupons1((CompanyFacade) facade);
			}
			facade = LoginManager.getInstance().login("company3@gmail.com", "pass3", ClientType.valueOf("Company"));
			if(facade instanceof CompanyFacade) {
				System.out.println("\n--------------------company3 was able to login successfully--------------------");
				TestCompanyFacade.initializeCoupons2((CompanyFacade) facade);
			}
			facade = LoginManager.getInstance().login("customer2@gmail.com", "pass2", ClientType.valueOf("Customer"));
			if(facade instanceof CustomerFacade) {
				System.out.println("\n--------------------customer2 was able to login successfully--------------------");
				TestCustomerFacade.start((CustomerFacade) facade);
			}
			facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("Administrator"));
			if(facade instanceof AdminFacade) {
				System.out.println("\n--------------------Admin was able to login successfully--------------------");
				TestAdminFacade.start((AdminFacade) facade);
			}
			facade = LoginManager.getInstance().login("company2@gmail.com", "pass2", ClientType.valueOf("Company"));
			if(facade instanceof CompanyFacade) {
				System.out.println("\n--------------------company2 was able to login successfully--------------------");
				TestCompanyFacade.start((CompanyFacade) facade);
			}
			facade = LoginManager.getInstance().login("company3@gmail.com", "pass3", ClientType.valueOf("Company"));
			if(facade instanceof CompanyFacade) {
				System.out.println("\n--------------------company3 was able to login successfully--------------------");
				TestCompanyFacade.start((CompanyFacade) facade);
			} else {
				System.out.println("Company with email: company3@gmail.com not exists in the system. can't login");
			}

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("----------------------------------------------------");
			job.stop();
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("\nCoupon-Expiration-Daily-Job-thread finished");
			try {
				ConnectionPool.getInstance().closeAllConnections();
				System.out.println("All connections are closed");
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("\n***GOODBAY***");	
		}
	}
}