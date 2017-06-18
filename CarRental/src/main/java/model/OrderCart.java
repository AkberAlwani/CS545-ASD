/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package model;

import java.util.List;

/**
 * @author Akber
 *
 */
public class OrderCart {

	private int customerId;
	private String customerName;
	private List<Vehicle> cars;

	public OrderCart() {

	}

	public OrderCart(int customerId, List<Vehicle> cars) {
		super();
		this.setCustomerId(customerId);
		this.setCars(cars);
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<Vehicle> getCars() {
		return cars;
	}

	public void setCars(List<Vehicle> cars) {
		this.cars = cars;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
