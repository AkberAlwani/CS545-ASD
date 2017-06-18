package org.rentframework.command;

import org.rentframework.core.Customer;
import org.rentframework.core.CustomerFacade;
import org.rentframework.core.CustomerFacadeImpl;
import org.rentframework.command.Command;

public class SaveCustomerCommand implements Command {

	private Customer customer;
	private CustomerFacade facade;

	public SaveCustomerCommand(Customer customer) {
		this.customer = customer;
		this.facade = new CustomerFacadeImpl();
	}

	@Override
	public boolean execute() {
		int affectedRows = facade.saveCustomer(customer);
		return affectedRows == 1 ? true : false;
	}

	@Override
	public boolean undo() {
		int affectedRows = facade.removeCustomer(customer);
		return affectedRows == 1 ? true : false;

	}

}
