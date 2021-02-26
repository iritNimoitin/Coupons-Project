package facades;

import CRUD_DAO.CompaniesDAO;
import CRUD_DAO.CouponsDAO;
import CRUD_DAO.CustomersDAO;
import exceptions.CouponSystemException;


public abstract class ClientFacade {
	 protected CompaniesDAO companiesDAO;
	 protected CouponsDAO couponsDAO;
	 protected CustomersDAO customersDAO;
	
	 public abstract boolean login(String email, String password) throws CouponSystemException;
}
