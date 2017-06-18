package org.rentframework.factory;

import org.rentframework.core.Customer;
import org.rentframework.middlelayer.SysUser;

public class PersonFactoryImpl implements PersonFactory {

	private static PersonFactory factory = new PersonFactoryImpl();

	/**
	 * private constructor to avoid instantiation
	 */
	private PersonFactoryImpl() {
	}

	public static PersonFactory getFactory() {
		return factory;
	}

	
	@Override
	public Person createPerson(String type) {
		Person person = null;
		if (type.equals("customer")) {
			person = (Person) new Customer();
		} else if (type.equals("sysuser")) {
			person = new SysUser();
		}
		return person;
	}

}
