/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.middlelayer;

public interface ASDSortedList {
	public SortedListIterator createIterator(Predicate<String> functor);
}
