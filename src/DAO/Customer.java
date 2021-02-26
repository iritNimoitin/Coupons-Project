package DAO;

import java.util.ArrayList;

public class Customer {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<Coupon> coupons;
	
	public Customer(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName != null && firstName != "") {
			this.firstName = firstName;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName != null && lastName != "") {
			this.lastName = lastName;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email != null && email != "") {
			this.email = email;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password != null && password != "") {
			this.password = password;
		}
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	
}
