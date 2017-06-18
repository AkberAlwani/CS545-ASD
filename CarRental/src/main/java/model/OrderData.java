/**
 * Copyright 2016 the original author or authors.
 * 
 * Licensed under the MIT License (MIT);
 */
package model;

import org.rentframework.core.OrderRecordEntry;

/**
 * @author Akber
 *
 */
public class OrderData extends OrderRecordEntry {
	private String productName;
	private String make;
	private String model;
	private double dailyFee;
	private double dailyFine;
	private double rentalFee;

	
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
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

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	
	/**
	 * @return the rentalFee
	 */
	public double getRentalFee() {
		return rentalFee;
	}

	/**
	 * @param rentalFee
	 *            the rentalFee to set
	 */
	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReturnData [name=" + productName + ", make="+make+", model=" + model + ", dailyFee=" + dailyFee
				+ ", dailyFine=" + dailyFine+ ", rentalFee=" + rentalFee + "]";
	}

}
