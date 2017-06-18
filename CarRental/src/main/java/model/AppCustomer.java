package model;

import org.rentframework.core.Customer;

import cs525.annotations.Validations.NotNull;


public class AppCustomer extends Customer {	
	
	@NotNull
	private String phone;
	
	public void setPhone(String phoneNumber) {
		this.phone = phoneNumber;
	}

	public String getPhone() {
		return this.phone;
	}

}
