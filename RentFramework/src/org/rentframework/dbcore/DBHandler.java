package org.rentframework.dbcore;

import java.sql.ResultSet;

public interface DBHandler {
	
	public int Create(String query);
	public ResultSet read(String query);
	public int delete(String query);
	public int update(String query);
}
