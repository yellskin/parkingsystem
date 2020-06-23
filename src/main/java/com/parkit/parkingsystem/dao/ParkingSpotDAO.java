package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <b>ParkingSpotDAO class is built to stock ParkingSpot Data.</b>
 * 
 * <p>
 * DAO means "Data Access Object". It is use to separate how we stock data with the
 * main code. So if stocking Data method change, only functional class will have
 * be to changed.
 * 
 * @see DataBaseConfig
 * @see DBConstants
 * @see ParkingType
 * @see ParkingSpot
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class ParkingSpotDAO {

	/**
	 * Call Logger
	 * 
	 * @param logger Logger's name is "ParkingSpotDAO"
	 * @since 1.0
	 */
	private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");

	/**
	 * Call class DataBaseConfig
	 * 
	 * @since 1.0
	 */
	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * getNextAvailableSlot() is built to get the next available slot in parking
	 * from DataBase in MySQL.
	 * 
	 * In a try/catch/finally instruction, it will :
	 * <ul>
	 * <li>getConnection from Database in MySQL</li>
	 * <li>use prepareStatement of getting next parking slot</li>
	 * <li>run query</li>
	 * <li>close ResultSet</li>
	 * <li>close PreparedStatement</li>
	 * <li>return the next available spot if no error</li>
	 * <li>close connection</li>
	 * </ul>
	 * 
	 * @param parkingType Parking type of vehicle
	 * @return result
	 * @since 1.0
	 */
	public int getNextAvailableSlot(ParkingType parkingType) {
		Connection con = null;
		int result = -1;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
			ps.setString(1, parkingType.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
				;
			}
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return result;
	}

	/**
	 * updateParking() is built to update parking after changed from DataBase in
	 * MySQL.
	 * 
	 * In a try/catch/finally instruction, it will :
	 * <ul>
	 * <li>getConnection from Database in MySQL</li>
	 * <li>use prepareStatement of updating parking spot</li>
	 * <li>run update</li>
	 * <li>close PreparedStatement</li>
	 * <li>return updating row</li>
	 * <li>close connection</li>
	 * </ul>
	 * 
	 * @param parkingSpot Parking spot with ID, type and isAvailable
	 * @return updateRowCount
	 * @since 1.0
	 */
	public boolean updateParking(ParkingSpot parkingSpot) {

		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
			ps.setBoolean(1, parkingSpot.isAvailable());
			ps.setInt(2, parkingSpot.getId());
			int updateRowCount = ps.executeUpdate();
			dataBaseConfig.closePreparedStatement(ps);
			return (updateRowCount == 1);
		} catch (Exception ex) {
			logger.error("Error updating parking info", ex);
			return false;
		} finally {
			dataBaseConfig.closeConnection(con);
		}
	}

}
