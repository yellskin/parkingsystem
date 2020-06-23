package com.parkit.parkingsystem.unit.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;

/**
 * <b>DataBaseConfigTest is built to unit test DataBaseConfig.</b>
 * 
 * @see DataBaseConfig
 * @see DBConstants
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class DataBaseConfigTest {

	private static DataBaseConfig dataBaseConfig;

	@BeforeAll
	private static void setUp() {
		dataBaseConfig = new DataBaseConfig();
	}

	@Test
	@DisplayName("Test when DataBaseConfig.getConnection is called, should create a connection")
	public void getConnection_test() throws ClassNotFoundException, SQLException {
		/**
		 * GIVEN : init Connection
		 */
		Connection connection;
		/**
		 * WHEN : get connection
		 */
		connection = dataBaseConfig.getConnection();
		/**
		 * THEN : verify Connection is OK
		 */
		assertThat(connection).isNotNull();
	}

	@Test
	@DisplayName("Test existing connection and when DataBaseConfig.closeConnection is called, should close this connection")
	public void closeConnection_test() throws ClassNotFoundException, SQLException {
		/**
		 * GIVEN : init Connection
		 */
		Connection connection;
		connection = dataBaseConfig.getConnection();
		/**
		 * WHEN : close connection
		 */
		dataBaseConfig.closeConnection(connection);
		/**
		 * THEN : verify Connection is close
		 */
		assertThat(connection.isClosed()).isTrue();
	}

	@Test
	@DisplayName("Test existing PreparedStatement and when DataBaseConfig.closePreparedStatement is called, should close this PreparedStatement")
	public void closePreparedStatement_test() throws ClassNotFoundException, SQLException {
		/**
		 * GIVEN : init Connection and PreparedStatement
		 */
		Connection connection;
		connection = dataBaseConfig.getConnection();
		PreparedStatement ps = null;
		ps = connection.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
		/**
		 * WHEN : close PreparedStatement and Connection
		 */
		dataBaseConfig.closePreparedStatement(ps);
		dataBaseConfig.closeConnection(connection);
		/**
		 * THEN : verify PreparedStatement is close
		 */
		assertThat(ps.isClosed()).isTrue();
	}

	@Test
	@DisplayName("Test when DataBaseConfig.getConnection is called, should close ResultSet")
	public void closeResultSet_test() throws ClassNotFoundException, SQLException {
		/**
		 * GIVEN : init Connection and PreparedStatement and ResultSet
		 */
		Connection connection;
		connection = dataBaseConfig.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		ps = connection.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
		ps.setString(1, "CAR");
		rs = ps.executeQuery();
		/**
		 * WHEN : close ResultSet and Connection
		 */
		dataBaseConfig.closeResultSet(rs);
		dataBaseConfig.closePreparedStatement(ps);
		dataBaseConfig.closeConnection(connection);
		/**
		 * THEN : verify ResultSet is close
		 */
		assertThat(rs.isClosed()).isTrue();
	}

}