/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

import java.sql.ResultSet;

import org.rentframework.middlelayer.SysUser;

public interface UserFacade {
	public int saveUser(SysUser sysUser);
	public int removeUser(SysUser sysUser);
	public ResultSet getAllUsers(Class<?> tableName);
	public ResultSet getUserByUserNameAndPassword(String userName,String password,Class<?> userTable);
}
