/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

import java.sql.ResultSet;

import java.time.LocalDate;

import org.rentframework.dbcore.*;
import org.rentframework.utils.DbHelper;

public class OrderRecordFacadeImpl implements OrderRecordFacade {
	private DBHandler dbaction;
	StringBuilder queryBuilder;
	StringBuilder updateProductQuery;

	public OrderRecordFacadeImpl() {
		this.dbaction = new DBHandlerImpl();
	}
	@Override
	public int saveOrderRecord(OrderRecordEntry orderRecordEntry) {
		System.out.println("QUERY: " + DbHelper.getInsertQuery(orderRecordEntry));
		int rows = this.dbaction.Create(DbHelper.getInsertQuery(orderRecordEntry));

		updateProductQuery = new StringBuilder();
		updateProductQuery.append("UPDATE vehicle SET quantity = quantity - " + orderRecordEntry.getQuantity()
				+ " WHERE productId=" + orderRecordEntry.getProductId());
		this.dbaction.update(updateProductQuery.toString());
		return rows;
	}
	@Override
	public int removeOrderRecord(OrderRecordEntry orderRecordEntry) {
		queryBuilder = new StringBuilder();
		queryBuilder.append(
				"DELETE FROM orderrecordentry WHERE customerId=" + orderRecordEntry.getOrderId());
		return this.dbaction.delete(queryBuilder.toString());
	}
	@Override
	public int orderReturnRecord(OrderRecordEntry orderRecordEntry) {
		queryBuilder = new StringBuilder();
		updateProductQuery = new StringBuilder();

		queryBuilder.append("UPDATE orderrecordentry SET isReturned=b'1', returnedDate='"
				+ LocalDate.now().toString() + "', rentalFine=" + orderRecordEntry.getDailyFine()
				+ " WHERE orderId=" + orderRecordEntry.getOrderId());

		updateProductQuery.append("UPDATE car SET quantity = quantity + " + orderRecordEntry.getQuantity()
				+ " WHERE productId=" + orderRecordEntry.getProductId());

		System.out.println("UPDATE QUERY: " + queryBuilder.toString());
		System.out.println("UPDATE PRODUCT QUERY: " + updateProductQuery.toString());

		this.dbaction.update(updateProductQuery.toString());
		return this.dbaction.update(queryBuilder.toString());
	}

	@Override
	public int undoReturn(OrderRecordEntry orderRecordEntry) {
		queryBuilder = new StringBuilder();
		queryBuilder.append("UPDATE orderrecordentry SET isReturned='false', returnedDate=NULL" + " WHERE customerId="
				+ orderRecordEntry.getOrderId());
		return this.dbaction.update(queryBuilder.toString());
	}

	@Override
	public ResultSet getAllOrderRecordsByCustomer(int customerId, Class<?> tableName, Class<?> joinTableName) {

		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT a.*, b.make, b.model, b.productName, b.dailyFee, b.dailyFine, b.plate FROM "
				+ tableName.getSimpleName() + " a INNER JOIN " + joinTableName.getSimpleName()
				+ " b ON a.customerId = " + customerId + " AND a.productId=b.productId");
		return this.dbaction.read(queryBuilder.toString());
	}

	@Override
	public ResultSet getAllOrderRecordsByCustomerAndUser(int customerId, int userId, Class<?> tableName) {
		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName() + " WHERE customerId = " + customerId
				+ " and personId = " + userId);
		return this.dbaction.read(queryBuilder.toString());
	}

	@Override
	public ResultSet getAllOrderRecords(Class<?> tableName) {
		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName());
		return this.dbaction.read(queryBuilder.toString());
	}

}
