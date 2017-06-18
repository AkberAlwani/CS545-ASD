package org.rentframework.core;

/* 
 * Author: Akber
 */
import java.sql.ResultSet;

import org.rentframework.dbcore.DBHandler;
import org.rentframework.dbcore.DBHandlerImpl;
import org.rentframework.logger.ConsoleLogger;
import org.rentframework.logger.Logger;
import org.rentframework.logger.LoggerImpl;
import org.rentframework.utils.DbHelper;

public class LocationFacadeImpl implements LocationFacade {
	private DBHandler dbaction;
	StringBuilder queryBuilder;
	private Logger logger;
	
	public LocationFacadeImpl() {
		this.dbaction = new DBHandlerImpl();
		this.logger = new ConsoleLogger(new LoggerImpl()); 
	}
	@Override
	public int saveLocation(Location location) {

		if (location.getLocationId()> 0) {
			logger.debug("Update Location");
			 this.dbaction.update(DbHelper.getUpdateQuery(location));
			 
		} else {
			this.dbaction.Create(DbHelper.getInsertQuery(location));
			
		}
		return 0;

	}

	@Override
	public int removeLocation(Location location) {
		queryBuilder = new StringBuilder();
		String tableName = location.getClass().getSimpleName();
		queryBuilder.append("DELETE FROM " + tableName + " WHERE locationId=" + location.getLocationId());
		return this.dbaction.delete(queryBuilder.toString());
	}

	@Override
	public ResultSet getLocationById(int locationId) {
		String tableName = "StoreCustomer";
		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName + " where locationId = " + locationId);
		return this.dbaction.read(queryBuilder.toString());
	}

	@Override
	public ResultSet getAllLocations(Class<?> tableName) {

		queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM " + tableName.getSimpleName());
		System.out.println(queryBuilder.toString());
		return this.dbaction.read(queryBuilder.toString());
	}


}
