package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;

/**
 * <b>ParkingSpot class is built to stock all methods referencing parking
 * spot.</b>
 * 
 * <p>
 * Methods :
 * <ul>
 * <li>ParkingSpot</li>
 * <li>getId</li>
 * <li>setId</li>
 * <li>getParkingType</li>
 * <li>setParkingType</li>
 * <li>isAvailable</li>
 * <li>setAvailable</li>
 * <li>equals</li>
 * <li>hashCode</li>
 * </ul>
 * 
 * 
 * @see DataBaseConfig
 * @see DBConstants
 * @see ParkingType
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class ParkingSpot {
	private int number;
	private ParkingType parkingType;
	private boolean isAvailable;

	public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
		this.number = number;
		this.parkingType = parkingType;
		this.isAvailable = isAvailable;
	}

	public int getId() {
		return number;
	}

	public void setId(int number) {
		this.number = number;
	}

	public ParkingType getParkingType() {
		return parkingType;
	}

	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ParkingSpot that = (ParkingSpot) o;
		return number == that.number;
	}

	@Override
	public int hashCode() {
		return number;
	}
}
