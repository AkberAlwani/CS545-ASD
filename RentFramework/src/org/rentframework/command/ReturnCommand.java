package org.rentframework.command;

import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.OrderRecordFacade;
import org.rentframework.core.OrderProtectionProxy;
import org.rentframework.command.Command;

public class ReturnCommand implements Command {

	private OrderRecordFacade facade;
	private OrderRecordEntry orderRecordEntry;

	public ReturnCommand(OrderRecordEntry orderRecordEntry) {
		this.facade = new OrderProtectionProxy();
		this.orderRecordEntry = orderRecordEntry;
	}

	@Override
	public boolean execute() {

		int affectedRows = facade.saveOrderRecord(orderRecordEntry);
		return affectedRows == 1 ? true : false;
	}

	@Override
	public boolean undo() {

		int affectedRows = facade.removeOrderRecord(orderRecordEntry);
		return affectedRows == 1 ? true : false;
	}

}

