package com.abhinavsingh.lldticketbooking.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinavsingh.lldticketbooking.Exception.InvalidLockException;
import com.abhinavsingh.lldticketbooking.Exception.SeatPermanentlyBookedException;
import com.abhinavsingh.lldticketbooking.model.Booking;
import com.abhinavsingh.lldticketbooking.model.Seat;
import com.abhinavsingh.lldticketbooking.model.Show;
import com.abhinavsingh.lldticketbooking.provider.SeatLockProvider;

@Service
public class BookingService {

	final Map<String, Booking> bookingMap;

	@Autowired
	private SeatLockProvider seatLockProvider;

	// Redis will be keeping Show based on SeatLock
	/**
	 * 
	 * Whenever I am going to book a ticket, I will need to take a lock. When a next
	 * booking comes, it will check if those seat are already locked
	 * 
	 */

	public BookingService() {
		bookingMap = new HashMap<>();
	}

	public Booking createNewBooking(final String bookingId, final Booking bk) throws Exception {

		// If any seat is already booked
		if (isAnySeatAlreadyBooked(bk.getShow(), bk.getSeats(), bk.getUserName())) {
			throw new SeatPermanentlyBookedException();
		}

		// Get the lock on the seat
		seatLockProvider.lockSeats(bk.getShow(), bk.getSeats(), bk.getUserName());

		final Booking booking = new Booking(bookingId, bk.getUserName(), bk.getShow(), bk.getSeats(),
				bk.getTotalSeats());

		// Save to database
		bookingMap.put(bookingId, booking);

		return booking;
		// Create Timer for booking expiry.
	}

	public void confirmBooking(@Nonnull final Booking booking, @Nonnull final String user) throws Exception {

		// Validate the lock
		// Need to change status to confirm for this booking
		// Save it to DB

		for (Seat seat : booking.getSeats()) {
			if (!seatLockProvider.validateLock(booking.getShow(), seat, user)) {
				throw new InvalidLockException();
			}
		}
		booking.confirmBooking();
		bookingMap.put(booking.getBookingId(), booking);
	}

	private boolean isAnySeatAlreadyBooked(Show show, List<Seat> seats, String userName) {

		List<Seat> bookedSeats = getBookedSeats(show);
		for (Seat seat : seats) {
			if (bookedSeats.contains(seat)) {
				return true;
			}
		}
		return false;
	}

	private List<Seat> getBookedSeats(Show show) {

		List<Seat> bookedSeats = new ArrayList<>();
		List<Booking> bookings = getAllBookings(show);
		for (Booking bk : bookings) {
			if (bk.isConfirmed()) {
				bookedSeats.addAll(bk.getSeats());
			}
		}
		return bookedSeats;
	}

	private List<Booking> getAllBookings(@Nonnull final Show show) {

		List<Booking> bookingForShow = new ArrayList<>();
		final List<Booking> bookings = (List<Booking>) bookingMap.values();
		for (Booking booking : bookings) {
			if (booking.getShow().equals(show)) {
				bookingForShow.add(booking);
			}
		}
		return bookingForShow;
	}
}
