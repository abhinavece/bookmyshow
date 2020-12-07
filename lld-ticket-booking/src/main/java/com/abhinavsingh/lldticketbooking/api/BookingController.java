package com.abhinavsingh.lldticketbooking.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinavsingh.lldticketbooking.Exception.BadRequestException;
import com.abhinavsingh.lldticketbooking.model.Booking;
import com.abhinavsingh.lldticketbooking.services.BookingService;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	final private BookingService bookingService;

	/**
	 * How would you book a seat and take lock for 5 minutes before it gets
	 * processed.
	 * 
	 * If it fails, you need to release lock after the timeout.
	 * 
	 * If the user deliberately cancelled the session, this lock should be released
	 * immediately.
	 * 
	 * @param bookingWrapper
	 * @return
	 * @throws Exception
	 */

	@PostMapping("/create")
	public String createBooking(@NonNull @RequestBody Booking booking) throws Exception {

		if (booking.getSeats().size() != booking.getTotalSeats()) {
			throw new BadRequestException();
		}

		final String bookingId = UUID.randomUUID().toString();

		final Booking myBooking = bookingService.createNewBooking(bookingId, booking);

		return myBooking.getBookingId();

	}

}
