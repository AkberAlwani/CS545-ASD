package org.rentframework.command;

import java.util.List;

import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.Customer;
import org.rentframework.core.Product;
import org.rentframework.middlelayer.SysUser;

public interface CommandManager {
	boolean saveCustomer(Customer customer);
	boolean saveUser(SysUser user);
	boolean saveProduct(Product product);
	boolean saveCheckoutRecords(List<OrderRecordEntry> checkoutRecordEntries);
	boolean checkReturnRecords(List<OrderRecordEntry> checkInEntries);
}
