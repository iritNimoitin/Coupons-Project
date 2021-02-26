package facades;

import exceptions.CouponSystemException;

public class LoginManager {
	private static LoginManager instance;

	private LoginManager() {
		//empty
	}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}
	
	/**
	 * According to @param(clientType) trying to login to the system
	 * with @param(email) and @param(password)
	 * @return client facade in accordance to clientType
	 * @throws CouponSystemException
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
		ClientFacade facade = null;
		switch (clientType) {
		case Administrator:
			facade = new AdminFacade();
			if(!facade.login(email, password)) {
				return null;
			}
			break;
		case Company:
			facade = new CompanyFacade();
			if(!facade.login(email, password)) {
				return null;
			}
			break;
		case Customer:
			facade = new CustomerFacade();
			if(!facade.login(email, password)) {
				return null;
			}
			break;
		}
		return facade;
	}
}
