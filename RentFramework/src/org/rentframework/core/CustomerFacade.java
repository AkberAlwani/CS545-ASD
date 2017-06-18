/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

import java.sql.ResultSet;


public interface CustomerFacade {

	public int saveCustomer(Customer customer);
	public int removeCustomer(Customer customer);
	public ResultSet getCustomerById(int customerId);
	public ResultSet getAllCustomers(Class<?> tableName);
	public ResultSet getAddressByCustomerId(int customerId,Class<?> tableName);
}
