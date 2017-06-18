package org.rentframework.dbcore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.rentframework.logger.ConsoleLogger;
import org.rentframework.logger.Logger;
import org.rentframework.logger.LoggerImpl;
import org.rentframework.utils.DbConfig;

public class ConnectionPool {

	private static ConnectionPool instance;

	private ConnectionPool() {
	}

	
	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		DbConfig config = new DbConfig();
		Connection con = null;
		Logger consoleLogger = new ConsoleLogger(new LoggerImpl());
		try {
			Class.forName(config.getDriver());

			consoleLogger.debug("URL:" + config.getDbUrl() + " UserName: " + config.getUserName() + " Password: "
					+ config.getDbPassword());
			con = DriverManager.getConnection(config.getDbUrl(), config.getUserName(), config.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
