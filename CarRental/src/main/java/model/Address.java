/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package model;

/**
 * This address class extends fujframework address class and passes application
 * user address information to framework address class
 * 
 * @author Fish
 *
 */
public class Address extends org.rentframework.core.Address {

	public Address(String streetAddress, String city, int zipCode, String state) {
		super(streetAddress, city, zipCode, state);
	}	
	

}
