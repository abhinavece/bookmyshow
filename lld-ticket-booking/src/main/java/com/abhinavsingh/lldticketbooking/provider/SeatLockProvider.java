package com.abhinavsingh.lldticketbooking.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abhinavsingh.lldticketbooking.model.Seat;
import com.abhinavsingh.lldticketbooking.model.Show;

public interface SeatLockProvider {

	void lockSeats(Show show, List<Seat> seats, String user) throws Exception;

	void unlockSeats(Show show, List<Seat> seats, String user);

	boolean validateLock(Show show, Seat seat, String user);

	List<Seat> fetchLockedSeats(Show show);
	
}
