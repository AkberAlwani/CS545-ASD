/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package org.rentframework.core;

/*
 * Author: Akber
 */
import java.sql.ResultSet;


public interface LocationFacade {

	public int saveLocation(Location location);
	public int removeLocation(Location location);
	public ResultSet getLocationById(int locationId);
	public ResultSet getAllLocations(Class<?> tableName);
	
	
}
