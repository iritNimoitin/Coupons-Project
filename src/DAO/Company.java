package DAO;

import java.util.ArrayList;

public class Company {
	private int id;
	private String name;
	private String email;
	private String password;
	private ArrayList<Coupon> coupons;
	
	public Company(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Company [name=" + name + ", email=" + email + "]";
	}
	
	
	
	
}
