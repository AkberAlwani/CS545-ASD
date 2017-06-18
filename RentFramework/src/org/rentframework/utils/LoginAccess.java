/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package org.rentframework.utils;


public class LoginAccess {

	
	public static boolean isLoggedIn() {
		Object value = SessionCache.getInstance().get(LoginRoles.LOGGED_IN);
		Boolean flag = false;
		if (value != null)
			flag = true;
		System.out.println("LoggedIn: " + flag);
		return flag;
	}
	public static boolean isAdmin() {
		Object value = SessionCache.getInstance().get(LoginRoles.ADMIN);
		Boolean flag = false;
		if (value != null)
			flag = true;
		System.out.println("IsADMIN: " + flag);
		return flag;
	}

	public static boolean isSalesman() {
		Object value = SessionCache.getInstance().get(LoginRoles.SALESMAN);
		Boolean flag = false;
		if (value != null)
			flag = true;
		System.out.println("isSalesman: " + flag);
		return flag;
	}

	public static String getLoggedInUsername() {
		Object value = SessionCache.getInstance().get(LoginRoles.SALESMAN);
		if (value != null)
			return value.toString();
		return null;
	}

}
