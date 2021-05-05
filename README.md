# Coupons-Project

## About

**Coupon management system** allows companies to generate coupons as part of advertising campaigns
And marketing that they sustain.
The system also has registered customers. Customers can purchase coupons. Coupons are limited in quantity
And in effect. Customer is limited to one coupon of each type.
The system records the coupons purchased by each customer.
The system's income is from purchases of coupons by customers and from creation and updating of new coupons by the companies.

Access to the system is divided into three types of Clients:
1. Administrator - Manage the list of companies and the list of customers.
2. Company - Managing a list of coupons associated with the company.
3. Customer - Purchasing coupons.


## Execution stages

 * Part 1 - Setting up the database and building the tables.
  
 * Part 2 - Building Java Beans classes that represent the information in the database.
  
 * Part 3 - Building a ConnectionPool that allows you to manage the database of connections to the database.
  
 * Part 4 - Building the DAO classes that allow general CRUD operations to be performed on the database.
  
 * Part 5 - Building the business logic required by the three types of clients of the system.
  
 * Part 6 - Building a class that enables Clients to enter, and therefore using the appropriate business logic.
  
 * Part 7 - Building a Daily Job to delete expired coupons from the System.
  
 * Part 8 - Building the testing classes to demonstrate the system's capabilities, and operating it from the main.

