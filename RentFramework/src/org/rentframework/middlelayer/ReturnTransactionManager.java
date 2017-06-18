/**
 * 
 */
package org.rentframework.middlelayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.rentframework.command.CommandManager;
import org.rentframework.command.CommandManagerImpl;
import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.Customer;
import org.rentframework.core.CustomerFacade;
import org.rentframework.core.CustomerFacadeImpl;
import org.rentframework.core.ProductFacade;
import org.rentframework.core.ProductFacadeImpl;
import org.rentframework.strategy.EmailNotificationStrategy;


public class ReturnTransactionManager extends TransactionManager {
	private CommandManager command;
	private ProductFacade productFacade;
	private CustomerFacade customerFacade;
	private Notifier notifier;

	
	public ReturnTransactionManager() {
		command = new CommandManagerImpl();
		productFacade = new ProductFacadeImpl();
		customerFacade = new CustomerFacadeImpl();
		notifier = new Notifier(new EmailNotificationStrategy());
	}

	@Override
	protected List<OrderRecordEntry> calculateFeeOrFine(List<OrderRecordEntry> orderRecordEntries,
			Class<?> productClass) {
		return orderRecordEntries;
	}

	@Override
	protected void processOrderRecord(List<OrderRecordEntry> orderRecordEntries) {
		command.checkReturnRecords(orderRecordEntries);
	}

	@Override
	protected void sendNotification(List<OrderRecordEntry> orderRecordEntries, Class<?> prodClass) {
		double totalFine = 0;
		int customerId = orderRecordEntries.get(0).getCustomerId();
		ResultSet rs = customerFacade.getCustomerById(customerId);
		Customer customer = new Customer();
		try {
			while (rs.next()) {

				customer.setEmail(rs.getString("email"));
				customer.setPersonId(rs.getInt("customerId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (OrderRecordEntry orderRecordEntry : orderRecordEntries) {
			totalFine += orderRecordEntry.getDailyFine();
		}

		StringBuilder message = new StringBuilder();
		message.append("Your total fine for the rented items is " + totalFine);

		notifier.notifyPerson(message.toString(), customer);
	}

}
