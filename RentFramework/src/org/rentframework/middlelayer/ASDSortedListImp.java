/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.middlelayer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.rentframework.factory.Person;

public class ASDSortedListImp implements ASDSortedList {
	private Comparator<Person> nameComparator= Comparator.comparing(Person ::getFirstName)
			                                           .thenComparing(Person::getLastName) ;
	private List<Person> persons;
	public ASDSortedListImp(List<Person> persons) {
		this.persons = getSortedPerson(persons);
	}

	@Override
	public SortedListIterator createIterator(Predicate<String> functor) {
		return new SortedListIteratorImp(functor);
	}
	private List<Person> getSortedPerson(List<Person> persons){
		return persons.stream().sorted(nameComparator).collect(Collectors.toList());
	}
	private class SortedListIteratorImp implements SortedListIterator {

		private Predicate predicate;
		private int index;
		public SortedListIteratorImp(Predicate<String> functor) {
			predicate = functor;
		}

		@Override
		public boolean hasNext() {
			if (index >= persons.size()) {
				return false;
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object next() {
			if (this.hasNext()) {
				String firstName = persons.get(index).getFirstName();
				String lastName = persons.get(index).getLastName();
				index++;
				if ( predicate.apply(firstName)|| predicate.apply(lastName) ) {
					return persons.get(index);
				} else {
					return next();
				}
			}
			return null;
		}

		@Override
		public Object currentItem() {
			return persons.get(index);
		}
		

	}

}
