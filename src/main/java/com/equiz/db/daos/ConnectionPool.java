package com.equiz.db.daos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * The class provides connection pool.
 *
 * @author bkalika
 */
public class ConnectionPool {
	private static final Logger LOG = Logger.getLogger(ConnectionPool.class);

	/**
	 * Establish connection with Database
	 *
	 * @return Connection with Database
	 */
	public static synchronized Connection getConnection() {
		try {
			Context initCtx = new InitialContext();
			Context envContext = (Context) initCtx.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/equiz");
			LOG.trace("Connection is successful");
			return ds.getConnection();
		} catch (NamingException e) {
			LOG.error("Cannot find the data source: " + e);
			return null;
		} catch (SQLException e) {
			LOG.error("Cannot get connection from data source: " + e);
			return null;
		}
	}
}
