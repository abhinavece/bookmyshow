package com.abhinavsingh.lldticketbooking.model;

import java.util.List;

import com.abhinavsingh.lldticketbooking.Exception.InvalidStateException;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Booking {

	private final String bookingId;
	private final String userName;
	private final Show show;
//	private final double totalAmount;
//	private Map<String, Seat> seatMap;
	private final List<Seat> seats;
	private final int totalSeats;
	private BookingStatus bookingStatus;

	public Booking(@NonNull final String bookingId, @NonNull final String userName, @NonNull final Show show, @NonNull final List<Seat> seats, @NonNull final int totalSeats) {
		this.bookingId = bookingId;
		this.userName = userName;
		this.show = show;
		this.seats = seats;
		this.totalSeats = totalSeats;
		this.bookingStatus = BookingStatus.CREATED;
	}

	public boolean isConfirmed() {
		return this.bookingStatus == bookingStatus.CONFIRMED;
	}

	public void confirmBooking() throws Exception {
		if (this.bookingStatus != BookingStatus.CREATED) {
			throw new InvalidStateException();
		}

		this.bookingStatus = BookingStatus.CONFIRMED;
	}

	public void expireBooking() throws Exception {
		if (this.bookingStatus != BookingStatus.CONFIRMED) {
			throw new InvalidStateException();
		}

		this.bookingStatus = BookingStatus.EXPIRED;
	}

}
