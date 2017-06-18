/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */

package org.rentframework.utils;

import java.util.HashMap;

public class SessionCache {

	private static SessionCache instance = new SessionCache();
	private HashMap<Object, Object> context;

	public static SessionCache getInstance() {
		return instance;

	}

	public void add(Object name, Object value) {
		if (context != null) {
			context.put(name, value);
		}
	}

	public Object get(Object name) {
		if (context == null) {
			return null;
		}
		return context.get(name);
	}

	public void remove(Object name) {
		context.remove(name);
	}

	private SessionCache() {
		context = new HashMap<Object, Object>();
		context.put(LoginRoles.LOGGED_IN, Boolean.FALSE);
	}
}
