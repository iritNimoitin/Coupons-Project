package DAO;

import java.time.LocalDate;

public class Coupon {
	private int id;
	private int companyID;
	private Category category;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private double price;
	private String image;
	
	public Coupon(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		if(category != null) {
			this.category = category;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title != null && title != "") {
			this.title = title;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description != null && description != "") {
			this.description = description;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if(startDate != null) {
			this.startDate = startDate;
		}
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		if(endDate != null) {
			this.endDate = endDate;
		}
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if(amount > 0) {
			this.amount = amount;
		}
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if(price > 0) {
			this.price = price;
		}
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		if(image != null && image != "") {
			this.image = image;
		}
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyID=" + companyID + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}

	
	
	
	
	
}
