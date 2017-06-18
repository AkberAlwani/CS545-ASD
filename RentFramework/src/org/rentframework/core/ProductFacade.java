/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

import java.sql.ResultSet;


public interface ProductFacade {
	public int saveProduct(Product product);
	public int removeProduct(Product product);
	public ResultSet getProductById(int productId, Class<?> tableName);
	public ResultSet getAllProduct(Class<?> tableName);
}
