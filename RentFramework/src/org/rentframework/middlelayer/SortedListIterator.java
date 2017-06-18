/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.middlelayer;


public interface SortedListIterator {
	public boolean hasNext();
	public Object next();

	public Object currentItem();
}
