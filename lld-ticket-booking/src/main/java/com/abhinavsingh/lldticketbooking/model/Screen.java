package com.abhinavsingh.lldticketbooking.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Screen {

	private final String screenId;
	private final String screenName;
	private final List<Seat> seats;
	private final Theater theater;

	public Screen(String screenId, String screenName, Theater theater) {
		this.screenId = screenId;
		this.screenName = screenName;
		this.seats = new ArrayList<Seat>();
		this.theater = theater;
	}

	// adding seats here
	public void addSeats(Seat seat) {
		this.seats.add(seat);
	}
}
