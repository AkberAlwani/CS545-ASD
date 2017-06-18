/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

/*
 * author : Akber
 */
import cs525.annotations.Validations.Number;
import cs525.annotations.Validations.NotNull;

public class Address {
	private int addressId;
	private int personId;

	private boolean isCustomer;
	
	@NotNull
	private String streetAddress;
	private String city;
	
	@Number
	private int zipCode;
	private String state;

	public Address() {
	}

	public Address(String streetAddress, String city, int zipCode, String state) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public int getZipCode() {
		return zipCode;
	}

	public String getState() {
		return state;
	}
	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public boolean isCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}
	
	public String getStreetAddress(){
		return getStreetAddress() + " , " + getCity() +" , " + getZipCode() + " , " + getState();
	}

}
