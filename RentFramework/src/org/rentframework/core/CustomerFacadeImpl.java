/**
 * 
 */
package org.rentframework.core;
/*
 * Author : Akber
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import org.rentframework.dbcore.DBHandler;
import org.rentframework.dbcore.DBHandlerImpl;
import org.rentframework.logger.ConsoleLogger;
import org.rentframework.logger.Logger;
import org.rentframework.logger.LoggerImpl;
import org.rentframework.utils.DbHelper;

public class CustomerFacadeImpl implements CustomerFacade {
	private DBHandler dbaction;
	StringBuilder queryBuilder;
	private Logger logger;
	
	public CustomerFacadeImpl() {
		this.dbaction = new DBHandlerImpl();
		this.logger = new ConsoleLogger(new LoggerImpl()); 
	}
	@Override
	public int saveCustomer(Customer customer) {

		if (customer.getPersonId() > 0) {
			logger.debug("Update Customer");
			 this.dbaction.update(DbHelper.getUpdateQuery(customer));
			 Address address = customer.getAddress();
			 if(address!=null){
				 address.setPersonId(customer.getPersonId());
				 address.setIsCustomer(true);
				 return this.dbaction.update(DbHelper.getUpdateQuery(address));
			 }		
		} else {
			this.dbaction.Create(DbHelper.getInsertQuery(customer));
			String tableName = customer.getClass().getSimpleName();

			int personId = getRecentlyAddedCustomer(tableName);
			if (personId > 0) {
				Address address = customer.getAddress();
				address.setPersonId(personId);
				address.setIsCustomer(true);
				return this.dbaction.Create(DbHelper.getInsertQuery(address));
			}
		}
		return 0;

	}

	@Override
	public int removeCustomer(Customer customer) {
		queryBuilder = new StringBuilder();
		String tableName = customer.getClass().getSimpleName();
		queryBuilder.append("DELETE FROM " + tableName + " WHERE customerId=" + customer.getPersonId());
		return this.dbaction.delete(queryBuilder.toString());
	}

	@Override
	public ResultSet getCustomerById(int customerId) {
		String tableName = "AppCustomer";

		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName + " where customerId = " + customerId);
		return this.dbaction.read(queryBuilder.toString());
	}

	private int getRecentlyAddedCustomer(String tableName) {
		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName + " ORDER BY customerId DESC LIMIT 1");

		logger.info(queryBuilder.toString());
		ResultSet result = this.dbaction.read(queryBuilder.toString());
		int customerId = 0;
		try {

			while (result.next()) {
				customerId = result.getInt("customerId");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customerId;
	}

	@Override
	public ResultSet getAllCustomers(Class<?> tableName) {

		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName());
		System.out.println(queryBuilder.toString());
		return this.dbaction.read(queryBuilder.toString());
	}

	@Override
	public ResultSet getAddressByCustomerId(int customerId, Class<?> tableName) {

		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName() + " WHERE personId=" + customerId);
		return this.dbaction.read(queryBuilder.toString());
	}
}
