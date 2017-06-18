/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package org.rentframework.middlelayer;

import org.rentframework.core.Address;
import org.rentframework.factory.Person;


public class SysUser implements Person {
	private int sysuserId;
	private String firstName;
	private String lastName;
	private String middleName;
	private Address address;
	private String email;
	public int getSysuserId() {
		return sysuserId;
	}
	public void setSysuserId(int sysuserId) {
		this.sysuserId = sysuserId;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}
	@Override
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Override
	public String getMiddleName() {
		return middleName;
	}

	@Override
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public Address getAddress() {
		return address;
	}
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setPersonId(int personId) {
		this.sysuserId = personId;
	}
	@Override
	public int getPersonId() {
		return sysuserId;
	}

}
