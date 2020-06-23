package com.parkit.parkingsystem.unit.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

/**
 * <b>FareCalculatorServiceTest is built to unit test FareCalculatorService.</b>
 * 
 * @see Fare
 * @see ParkingType
 * @see ParkingSpot
 * @see Ticket
 * @see FareCalculatorService
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class FareCalculatorServiceTest {

	private static FareCalculatorService fareCalculatorService;
	private Ticket ticket;

	@BeforeAll
	private static void setUp() {
		fareCalculatorService = new FareCalculatorService();
	}

	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	@Test
	@DisplayName("Test calculate Fare CAR : 1 hour")
	public void calculateFareCar() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);

	}

	@Test
	@DisplayName("Test calculate Fare BIKE : 1 hour")
	public void calculateFareBike() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);

	}

	@Test
	@DisplayName("Test calculate Fare Unknown Type of vehicule : 1 hour")
	public void calculateFareUnkownType() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		/**
		 * THEN : Verify throws
		 */
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false)); // null
																											// parking
		// type
	}

	@Test
	@DisplayName("Test calculate Fare BIKE with Future in time")
	public void calculateFareBikeWithFutureInTime() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		/**
		 * THEN : Verify throws
		 */
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false)); // illegal
																												// or
		// inapropriate
		// argue
	}

	// NEW TEST
	@Test
	@DisplayName("Test calculate Fare CAR with Future in time")
	public void calculateFareCarWithFutureInTime() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		/**
		 * THEN : Verify throws
		 */
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false)); // illegal
																												// or
		// inapropriate
		// argue

	}

	@Test
	@DisplayName("Test calculate Fare BIKE less than one hour park time : 45 min")
	public void calculateFareBikeWithLessThanOneHourParkingTime() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	@DisplayName("Test calculate Fare CAR less than one hour park time : 45 min")
	public void calculateFareCarWithLessThanOneHourParkingTime() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	@DisplayName("Test calculate Fare CAR = 1 day")
	public void calculateFareCarWithMoreThanADayParkingTime() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	// New Test
	@Test
	@DisplayName("Test calculate Fare BIKE = 1 day")
	public void calculateFareBikeWithMoreThanADayParkingTime() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((24 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	// New Test
	@Test
	@DisplayName("Test calculate Fare CAR <= 30 min")
	public void calculateFareCarWithLessThanHalfHour() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));// 30 min should give free fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((0 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	// New Test
	@Test
	@DisplayName("Test calculate Fare BIKE <= 30 min")
	public void calculateFareBikeWithLessThanHalfHour() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));// 30 min should give free fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, false);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	// New Test
	@Test
	@DisplayName("Test calculate Fare CAR : RECURRENT CUSTOMER 1 hour")
	public void calculateFareCarWithRecurrentCustomer() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Boolean recurrent = true;

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, recurrent);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((0.95 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	// New Test
	@Test
	@DisplayName("Test calculate Fare BIKE : RECURRENT CUSTOMER 1 hour")
	public void calculateFareBikeWithRecurrentCustomer() {
		/**
		 * GIVEN :
		 */
		/**
		 * WHEN : Set parking spot
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		Boolean recurrent = true;

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, recurrent);
		/**
		 * THEN : Verify price is OK
		 */
		assertEquals((0.95 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

}
