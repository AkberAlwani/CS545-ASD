/**
 * Copyright 2016 the original author or authors.
 * 
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.middlelayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.rentframework.command.CommandManager;
import org.rentframework.command.CommandManagerImpl;
import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.Customer;
import org.rentframework.core.CustomerFacade;
import org.rentframework.core.CustomerFacadeImpl;

import org.rentframework.core.LocationFacadeImpl;

import org.rentframework.core.ProductFacade;
import org.rentframework.core.ProductFacadeImpl;
import org.rentframework.strategy.EmailNotificationStrategy;


public class OrderTransactionManager extends TransactionManager {
	private CommandManager command;
	private ProductFacade productFacade;
	private CustomerFacade customerFacade;
	private Notifier notifier;

	public OrderTransactionManager() {
		command = new CommandManagerImpl();
		productFacade = new ProductFacadeImpl();
		customerFacade = new CustomerFacadeImpl();
		new LocationFacadeImpl();
		notifier = new Notifier(new EmailNotificationStrategy());
	}

	@Override
	protected void processOrderRecord(List<OrderRecordEntry> orderRecordEntries) {
		command.saveCheckoutRecords(orderRecordEntries);
	}
	@Override
	protected List<OrderRecordEntry> calculateFeeOrFine(List<OrderRecordEntry> orderRecordEntries,
			Class<?> productClass) {

		return orderRecordEntries;
	}

	@Override
	protected void sendNotification(List<OrderRecordEntry> orderRecordEntries, Class<?> productClass) {
		double totalFee = 0;
		int customerId = orderRecordEntries.get(0).getCustomerId();
		LocalDate dueDate = orderRecordEntries.get(0).getDueDate();
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

		Set<String> productNames = new TreeSet<String>();

		for (OrderRecordEntry orderRecordEntry : orderRecordEntries) {
			totalFee += orderRecordEntry.getDailyFine();
			productNames.add(getProductName(orderRecordEntries.get(0).getProductId(), productClass));
		}

		StringBuilder message = new StringBuilder();
		message.append("You rented the following item(s) \n");
		for (String productName : productNames) {
			message.append(productName + "\n");
		}
		message.append("Due date for the rented item(s) is " + dueDate + "\n");
		message.append("Your total fee for the rented item(s) is " + totalFee + "\n");

		notifier.notifyPerson(message.toString(), customer);
	}

	private String getProductName(int productId, Class<?> prodClass) {
		ResultSet rs = productFacade.getProductById(productId, prodClass);

		String name = "";
		try {
			while (rs.next()) {
				name = rs.getString("productName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return name;
	}

}
