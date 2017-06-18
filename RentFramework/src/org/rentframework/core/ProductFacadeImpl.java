/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

import java.sql.ResultSet;

import org.rentframework.dbcore.DBHandler;
import org.rentframework.dbcore.DBHandlerImpl;
import org.rentframework.utils.DbHelper;


public class ProductFacadeImpl implements ProductFacade {
	private DBHandler dbaction;
	StringBuilder queryBuilder;
	public ProductFacadeImpl() {
		this.dbaction = new DBHandlerImpl();
	}
	@Override
	public int saveProduct(Product product) {
		if (product.getProductId() > 0)
			return this.dbaction.update(DbHelper.getUpdateQuery(product));
		return this.dbaction.Create(DbHelper.getInsertQuery(product));
	}

	@Override
	public int removeProduct(Product product) {
		queryBuilder = new StringBuilder();
		String tableName = product.getClass().getSimpleName();
		queryBuilder.append("DELETE FROM "+tableName+" WHERE productId=" + product.getProductId());
		return this.dbaction.delete(queryBuilder.toString());
	}
	@Override
	public ResultSet getProductById(int productId, Class<?> tableName) {
		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName() + " where productId = " + productId);
		return this.dbaction.read(queryBuilder.toString());
	}

	@Override
	public ResultSet getAllProduct(Class<?> tableName) {
		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName());
		System.out.println(tableName.getSimpleName());
		return this.dbaction.read(queryBuilder.toString());
	}

}
