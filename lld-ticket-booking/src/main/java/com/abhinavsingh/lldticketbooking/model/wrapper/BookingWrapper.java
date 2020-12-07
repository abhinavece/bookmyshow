package com.abhinavsingh.lldticketbooking.model.wrapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookingWrapper {

	private final String showId;
	private final List<String> seatIds;
	private final String userName;
	private final int totalSeats;

}
