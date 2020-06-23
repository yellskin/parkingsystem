package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * <b>DataBaseConfig class is built to interact with DataBase in MySQL.</b>
 * 
 * <p>
 * DataBase is composed with two tables :
 * <ol>
 * <li>Parking</li>
 * <li>Ticket</li>
 * </ol>
 * 
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class DataBaseConfig {
	/**
	 * Call Logger
	 * 
	 * @param logger Logger's name is "DataBaseConfig"
	 * @since 1.0
	 */
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	/**
	 * getConnection() method is built to connect with Database in MySQL.
	 * 
	 * logger.info : level of standing 5
	 * 
	 * Class.forName : Instantiate class with Full name class
	 * 
	 * @return DriverManager.getConnection permit to connect to DataBase with
	 *         parameters as TimeZone, Id, password
	 * @throws ClassNotFoundException If class not found
	 * @throws SQLException           If can't connect
	 * @since 3.0
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/prod?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "Azerty123456.");
	}

	/**
	 * closeConnection() is built to close Connection with DataBase in MySQL.
	 * 
	 * logger.info : level of standing 5
	 * 
	 * @param con Connection with DataBase
	 * @since 1.0
	 */
	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	/**
	 * closePreparedStatement() is built to close PreparedStatement which is use for
	 * queries.
	 * 
	 * @param ps PreparedStatement
	 * @since 1.0
	 */
	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	/**
	 * closeResultSet() is built to close ResultSet.
	 * 
	 * @param rs ResultSet
	 * @since 1.0
	 */
	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}
}
