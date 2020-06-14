package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

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