/**
 * 
 */
package org.rentframework.core;

import java.sql.ResultSet;

import org.rentframework.utils.LoginAccess;

public class OrderProtectionProxy implements OrderRecordFacade {

	private OrderRecordFacade orderRecordFacade;
	private boolean isSalesman = false;

	public OrderProtectionProxy() {
		this.orderRecordFacade = new OrderRecordFacadeImpl();
		isSalesman = LoginAccess.isSalesman();
	}

	@Override
	public int saveOrderRecord(OrderRecordEntry orderRecordEntry) {
		if (isSalesman) {
			return orderRecordFacade.saveOrderRecord(orderRecordEntry);
		}
		return 0;
	}

	@Override
	public int removeOrderRecord(OrderRecordEntry checkoutRecordEntry) {
		if (isSalesman) {
			return orderRecordFacade.removeOrderRecord(checkoutRecordEntry);
		}
		return 0;
	}

	@Override
	public int orderReturnRecord(OrderRecordEntry orderRecordEntry) {
		if (isSalesman) {
			return orderRecordFacade.orderReturnRecord(orderRecordEntry);
		}
		return 0;
	}

	@Override
	public int undoReturn(OrderRecordEntry orderRecordEntry) {
		if (isSalesman) {
			return orderRecordFacade.undoReturn(orderRecordEntry);
		}
		return 0;
	}

	@Override
	public ResultSet getAllOrderRecordsByCustomer(int customerId, Class<?> tableName, Class<?> joinTableName) {
		return orderRecordFacade.getAllOrderRecordsByCustomer(customerId, tableName, joinTableName);
	}

	@Override
	public ResultSet getAllOrderRecordsByCustomerAndUser(int customerId, int userId, Class<?> tableName) {
		return orderRecordFacade.getAllOrderRecordsByCustomerAndUser(customerId, userId, tableName);
	}

	@Override
	public ResultSet getAllOrderRecords(Class<?> tableName) {
		return orderRecordFacade.getAllOrderRecords(tableName);
	}

}
