package com.abhinavsingh.lldticketbooking.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abhinavsingh.lldticketbooking.Exception.NotFoundException;
import com.abhinavsingh.lldticketbooking.model.Screen;
import com.abhinavsingh.lldticketbooking.model.Seat;
import com.abhinavsingh.lldticketbooking.model.SeatClass;
import com.abhinavsingh.lldticketbooking.model.Theater;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Service
public class TheaterService {

	private Map<String, Theater> theaterMap;
	private Map<String, Screen> screens;
	private Map<String, Seat> seats;

	public TheaterService() {
		theaterMap = new HashMap<>();
		screens = new HashMap<>();
		seats = new HashMap<>();
	}

	public Theater createTheater(String id, String name, String location) {

		Theater theater = new Theater(id, name, location);
		theaterMap.put(id, theater);

		return theater;
	}

	public List<Theater> fetchAllTheaters() {
		if (!theaterMap.isEmpty()) {
			return theaterMap.values().stream().collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public Theater getTheaterById(@NonNull String theaterId) {
		return theaterMap.get(theaterId);
	}

	public String createScreen(@NonNull String screenName, Theater theater) {

		Screen screen = new Screen(UUID.randomUUID().toString(), screenName, theater);
		return screen.getScreenId();
	}

	public Screen findScreenById(@NonNull String screenId) throws Exception {

		if (!screens.containsKey(screenId)) {
			throw new NotFoundException();
		}
		return screens.get(screenId);
	}

	public Seat addSeatToScreen(@NonNull final String rowNo, @NonNull final String seatNo, @NonNull final double amount, @NonNull final Screen screen){
		
		final Seat seat = new Seat(seatNo, rowNo, SeatClass.NORMAL, amount);
		seats.put(seat.getSeatNo()+""+seat.getRowNo(), seat);
		return seat;
	}

}
