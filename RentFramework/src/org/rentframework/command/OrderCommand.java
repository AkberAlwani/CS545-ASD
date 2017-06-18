package org.rentframework.command;

import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.OrderRecordFacade;
import org.rentframework.core.OrderProtectionProxy;

public class OrderCommand implements Command {

	private OrderRecordFacade facade;
	private OrderRecordEntry orderRecordEntry;

	public OrderCommand(OrderRecordEntry orderRecordEntry) {
		this.facade = new OrderProtectionProxy();
		this.orderRecordEntry = orderRecordEntry;
	}

	@Override
	public boolean execute() {
		int affectedRows = facade.orderReturnRecord(orderRecordEntry);
		return affectedRows == 1 ? true : false;
	}

	@Override
	public boolean undo() {
		int affectedRows = facade.undoReturn(orderRecordEntry);
		return affectedRows == 1 ? true : false;
	}

}
