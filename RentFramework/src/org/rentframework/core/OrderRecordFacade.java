/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

import java.sql.ResultSet;


public interface OrderRecordFacade {
	public int saveOrderRecord(OrderRecordEntry orderRecordEntry);
	public int removeOrderRecord(OrderRecordEntry orderRecordEntry);
	public int orderReturnRecord(OrderRecordEntry orderRecordEntry);
	public int undoReturn(OrderRecordEntry orderRecordEntry);
	public ResultSet getAllOrderRecordsByCustomer(int customerId, Class<?> tableName, Class<?> joinTableName);
	public ResultSet getAllOrderRecordsByCustomerAndUser(int customerId, int userId, Class<?> tableName);
	public ResultSet getAllOrderRecords(Class<?> tableName);
}
