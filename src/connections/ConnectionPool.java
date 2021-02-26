package connections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.CouponSystemException;

public class ConnectionPool {
	
		private String url ="jdbc:mysql://localhost:3306/coupon_system?serverTimezone=Israel&createDatabaseIfNotExist=true";
		private String user = "irit13";
		private String password = "1234";
		private Set<Connection> connections = new HashSet<Connection>();
		public static final int MAX = 5;

		private static ConnectionPool instance;
		
		/**
		 * Creating set of reusable connections to communicate with the database
		 * @throws CouponSystemException
		 */
		private ConnectionPool() throws CouponSystemException {
			try {
				for (int i = 0; i < MAX; i++) {
					Connection con = DriverManager.getConnection(url, user, password);
					connections.add(con);
				}
			} catch (SQLException e) {
				throw new CouponSystemException("ConnectionPool initialization faild.", e);
			}
		}

		public static ConnectionPool getInstance() throws CouponSystemException {
			if (instance == null) {
				instance = new ConnectionPool();
			}
			return instance;
		}

		public synchronized Connection getConnection() {
			while (this.connections.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("ERROR InterruptedException: Get connection faild.");
				}
			}
			Iterator<Connection> it = this.connections.iterator();
			Connection con = it.next();
			it.remove();
			return con;
		}
		
		public synchronized void restoreConnection( Connection con) {
			if(connections.size() != 5) {
				connections.add(con);				
			}
			notifyAll();
		}
		
		public synchronized void closeAllConnections() throws CouponSystemException{
			int closeCounter = 0;
			while(closeCounter < MAX) {
				Iterator<Connection> it = this.connections.iterator();
				if(it.hasNext()) {
					try {
						it.next().close();
						it.remove();
						closeCounter++;
					} catch (SQLException e) {
						throw new CouponSystemException("Connections closing faild.", e);
					}
				} else {
					try {
						wait();
					} catch (InterruptedException e) {
						System.out.println("ERROR InterruptedException: Connections closing faild.");
					}
				}
			}
		}

}