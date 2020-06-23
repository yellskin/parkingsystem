package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;

/**
 * <b>ParkingDataBaseIT is built to integration test incoming and outgoing of
 * vehicle in park.</b>
 * 
 * <p>
 * Use DataBaseTestConfig and DataBasePrepareService
 * 
 * @see Fare
 * @see ParkingSpotDAO
 * @see TicketDAO
 * @see DataBaseTestConfig
 * @see DataBasePrepareService
 * @see ParkingSpot
 * @see Ticket
 * @see ParkingService
 * @see InputReaderUtil
 * 
 * @author Frederic VO
 * @version 3.0
 */
@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterEach
	private void closeUpPerTest() throws Exception {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	@DisplayName("Test process incoming vehicle should save a ticket and update parking table in DB")
	public void testParkingACar() throws Exception {

		// TODO: check that a ticket is actually saved in DB and Parking table is
		// updated with availability

		/**
		 * GIVEN : call ParkingService
		 */
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		/**
		 * WHEN : call parkingService.processIncomingVehicle
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		parkingService.processIncomingVehicle(inTime);

		/**
		 * THEN : check that ticket is actually saved in DB and parking table is update
		 * in DB
		 */

		Ticket getTicketInDB = ticketDAO.getTicket("ABCDEF");
		assertThat(getTicketInDB.getVehicleRegNumber()).isEqualTo("ABCDEF");
		assertThat(getTicketInDB.getPrice()).isEqualTo(0);
		assertThat(getTicketInDB.getInTime()).isNotNull();

		ParkingSpot parkingspot = getTicketInDB.getParkingSpot();
		assertNotNull(parkingspot);
		assertThat(parkingspot.isAvailable()).isEqualTo(false);

	}

	@Test
	@DisplayName("Test process outgoing vehicle should generate fare and out time in DB")
	public void testParkingLotExit() throws Exception {

		// TODO: check that the fare generated and out time are populated correctly in
		// the database

		/**
		 * GIVEN : call ParkingService
		 */
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		/**
		 * WHEN : call parkingService.processExitingVehicle
		 */
		parkingService.processExitingVehicle();

		/**
		 * THEN : check fare generate and out time populated
		 */
		Ticket getTicketInDB = ticketDAO.getTicket("ABCDEF");
		getTicketInDB.setPrice(Fare.CAR_RATE_PER_HOUR);
		assertNotNull(getTicketInDB);
		ticketDAO.updateTicket(getTicketInDB);

		assertThat(getTicketInDB.getOutTime()).isNotNull();
		assertThat(getTicketInDB.getPrice()).isEqualTo(1.5);

	}

	@Test
	@DisplayName("Test process incoming and outgoing and incoming vehicle should generate a recurrent user in DB")
	public void testIsRecurrentCustomer_TRUE() throws Exception {

		/**
		 * GIVEN : call ParkingService
		 */
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		/**
		 * WHEN : call parkingService.processIncomingVehicle and
		 * parkingService.processExitingVehicle
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis());
		parkingService.processIncomingVehicle(inTime);
		Thread.sleep(100);
		parkingService.processExitingVehicle();
		Thread.sleep(100);
		parkingService.processIncomingVehicle(inTime);

		/**
		 * THEN : check this is recurrent customer
		 */
		boolean testRecurrent = ticketDAO.recurrentCustomer("ABCDEF");
		assertTrue(testRecurrent);

	}

	@Test
	@DisplayName("Test process incoming and outgoing vehicle should not generate a recurrent user in DB")
	public void testIsRecurrentCustomer_FALSE() throws Exception {

		/**
		 * GIVEN : call ParkingService
		 */
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		/**
		 * WHEN : call parkingService.processIncomingVehicle and
		 * parkingService.processExitingVehicle
		 */
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis());
		parkingService.processIncomingVehicle(inTime);
		Thread.sleep(100);
		parkingService.processExitingVehicle();

		/**
		 * THEN : check this is not recurrent customer
		 */
		boolean testRecurrent = ticketDAO.recurrentCustomer("789456");
		assertFalse(testRecurrent);

	}

}