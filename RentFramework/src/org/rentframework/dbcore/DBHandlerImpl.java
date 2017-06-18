package org.rentframework.dbcore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.rentframework.logger.*;



public class DBHandlerImpl implements DBHandler {
	ConnectionPool connection = ConnectionPool.getInstance();
	Logger consoleLogger;

	public DBHandlerImpl() {
		consoleLogger = new ConsoleLogger(new LoggerImpl());
	}
	
	@Override
	public int Create(String query) {
		Connection con = null;
		PreparedStatement ps = null;
		int countRecord = 0;
		try {
			consoleLogger.debug("INSERT QUERY: " + query);
			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(query);

			countRecord = ps.executeUpdate();
		} catch (Exception e) {

		} finally {
			cleanupResources(ps, con);
		}
		return countRecord;
	}

	
	@Override
	public ResultSet read(String query) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			con = ConnectionPool.getConnection();
			Statement statement = con.createStatement();
			// ps = con.prepareStatement(query);
			consoleLogger.debug(query);
			rs = statement.executeQuery(query);

		} catch (Exception e) {
			// false;
		} finally {
			if (ps != null && con != null)
				cleanupResources(ps, con);
		}

		return rs;
	}

	
	@Override
	public int delete(String query) {
		PreparedStatement ps = null;
		Connection con = null;
		int countRecord = 0;
		try {

			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(query);

			countRecord = ps.executeUpdate();
		} catch (Exception e) {

		} finally {
			cleanupResources(ps, con);
		}
		return countRecord;
	}

	
	@Override
	public int update(String query) {
		Connection con = null;
		PreparedStatement ps = null;
		int recordCounter = 0;
		try {

			con = ConnectionPool.getConnection();
			ps = con.prepareStatement(query);

			consoleLogger.debug("UPDATE QUERY: " + query);
			recordCounter = ps.executeUpdate();
		} catch (Exception e) {

			return recordCounter;
		} finally {
			cleanupResources(ps, con);
		}
		return recordCounter;
	}

	private void cleanupResources(PreparedStatement ps, Connection con) {
		try {
			ps.close();
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
