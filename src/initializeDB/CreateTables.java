package initializeDB;

public class CreateTables {
	
	public static String companies(String dbName) {
		return ("create table " + dbName + ".COMPANIES"
				+ "("
				+ "ID int auto_increment, "
				+ "NAME varchar(100), "
				+ "EMAIL varchar(30), "
				+ "PASSWORD varchar(30), "
				+ "primary key(ID)"
				+ ")");
	}
	
	public static String customers(String dbName) {
		return ("create table " + dbName + ".CUSTOMERS"
				+ "("
				+ "ID int auto_increment, "
				+ "FIRST_NAME varchar(30), "
				+ "LAST_NAME varchar(30), "
				+ "EMAIL varchar(30), "
				+ "PASSWORD varchar(30), "
				+ "primary key(ID)"
				+ ")");
	}
	
	public static String categories(String dbName) {
		return ("create table " + dbName + ".CATEGORIES"
				+ "("
				+ "ID int auto_increment, "
				+ "NAME varchar(30), "
				+ "primary key(ID)"
				+ ")");
	}
	
	public static String coupons(String dbName) {
		return ("create table " + dbName + ".COUPONS"
				+ "("
				+ "ID int auto_increment, "
				+ "COMPANY_ID int, "
				+ "CATEGORY_ID int, "
				+ "TITLE varchar(30), "
				+ "DESCRIPTION varchar(2000), "
				+ "START_DATE date, "
				+ "END_DATE date, "
				+ "AMOUNT int, "
				+ "PRICE double, "
				+ "IMAGE varchar(200), "
				+ "primary key(ID), "
				+ "foreign key(COMPANY_ID) references COMPANIES(ID), "
				+ "foreign key(CATEGORY_ID) references CATEGORIES(ID)"
				+ ")");
	}
	
	public static String coustomers_vs_coupons(String dbName) {
		return ("create table " + dbName + ".CUSTOMERS_VS_COUPONS"
				+ "("
				+ "CUSTOMER_ID int, "
				+ "COUPON_ID int, "
				+ "primary key(CUSTOMER_ID, COUPON_ID), "
				+ "foreign key(CUSTOMER_ID) references CUSTOMERS(ID), "
				+ "foreign key(COUPON_ID) references COUPONS(ID)"
				+ ")");
	}
}
