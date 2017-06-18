package org.rentframework.factory;

import org.rentframework.core.Address;

public interface Person {
	
	public void setPersonId(int personId);
	public int getPersonId();
	public void setFirstName(String firstName);

	
	public String getFirstName();
	public void setLastName(String lastName);
	public String getLastName();
	public void setMiddleName(String middleName);
    public String getMiddleName();

	
	public void setAddress(Address address);
	public Address getAddress();

	public void setEmail(String email);

	public String getEmail();
}
