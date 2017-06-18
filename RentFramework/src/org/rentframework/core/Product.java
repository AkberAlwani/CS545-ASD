
package org.rentframework.core;
/*
 * Author: Akber
 */

//public interface Product {
public class Product {
	private int productId;
	private String productName;
	private String description;
	private double dailyFee;
	private double dailyFine;

	public Product()
	{}
	/**
	 * @param name
	 * @param description
	 * @param perDayRentalFee
	 * @param perDayOverdueFine
	 */
	public Product(String productName, String description, double dailyFee, double dailyFine) {
		super();
		this.productName = productName;
		this.description = description;
		this.dailyFee = dailyFee;
		this.dailyFine = dailyFine;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the dailyFee
	 */
	public double getDailyFee() {
		return dailyFee;
	}
	/**
	 * @param dailyFee the dailyFee to set
	 */
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	/**
	 * @return the dailyFine
	 */
	public double getDailyFine() {
		return dailyFine;
	}
	/**
	 * @param dailyFine the dailyFine to set
	 */
	public void setDailyFine(double dailyFine) {
		this.dailyFine = dailyFine;
	}
	
	
//	public void setProductId(int productId);
//	public int getProductId();
//	public void setProductName(String productName);
//	public String getProductName();
//	public void setPerDayRentalFee(double rentalFee);
//	public double getPerDayRentalFee();
//	public void setPerDayOverdueFine(double overdueFine);
//	public double getPerDayOverdueFine();

}
