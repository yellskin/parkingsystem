package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

/**
 * <b>FareCalculatorService class is built to be a calculator.</b>
 * 
 * @author Frederic VO
 * @version 3.0
 */
public class FareCalculatorService {
	
	/**
	 * calculateFare is built to calculate fare depending on time
	 * in/out park and depending of vehicle's type.
	 * 
	 * <p>
	 * If outTime is not null, it will set price depending on duration + reduction + fare.
	 * With reduction depending on duration (30min free / 95% fare recurrent customer)
	 * With fare depending on type of vehicle (car/bike).
	 * 
	 * @param ticket Ticket
	 * @param reccurent Boolean
	 * @since 3.0
	 */
	public void calculateFare(Ticket ticket, boolean reccurent) {

		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		double inHour = ticket.getInTime().getTime();
		double outHour = ticket.getOutTime().getTime();
		double duration = (outHour - inHour) / (1000 * 60 * 60);
		if (duration <= 0.5) {
			ticket.setPrice(0);
			return;
		}

		double reduction = 0;
		if (reccurent == true) {
			reduction = 0.95;
		} else {
			reduction = 1;
		}

		double fare;

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			fare = Fare.CAR_RATE_PER_HOUR;
			break;
		}
		case BIKE: {
			fare = Fare.BIKE_RATE_PER_HOUR;
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		ticket.setPrice(reduction * duration * fare);
	}
}