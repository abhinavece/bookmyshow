package com.abhinavsingh.lldticketbooking.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.abhinavsingh.lldticketbooking.Exception.SeatTemporarilyUnavailableException;
import com.abhinavsingh.lldticketbooking.model.Seat;
import com.abhinavsingh.lldticketbooking.model.SeatLock;
import com.abhinavsingh.lldticketbooking.model.Show;
import com.google.common.collect.ImmutableList;

@Service
public class InMemorySeatLockProvider implements SeatLockProvider {

	final Map<Show, Map<Seat, SeatLock>> lock = new HashMap<>();

	@Override
	public void lockSeats(Show show, List<Seat> seats, String user) throws Exception {

		for (Seat seat : seats) {
			if (isSeatLocked(show, seat)) {
				throw new SeatTemporarilyUnavailableException();
			}
		}

		if (!lock.containsKey(show)) {
			lock.put(show, new HashMap<>());
		}

		for (Seat seat : seats) {
			SeatLock seatLockObj = new SeatLock(show, seat, new Date(), 300, user);
			lock.get(show).put(seat, seatLockObj);
		}
	}

	@Override
	public void unlockSeats(final Show show, final List<Seat> seats, final String user) {
		for (Seat seat : seats) {
			if (validateLock(show, seat, user)) {
				unlockSeat(show, seat);
			}
		}
	}

	public boolean isSeatLocked(final Show show, final Seat seat) {
		return lock.containsKey(show) && lock.get(show).containsKey(seat) && !lock.get(show).get(seat).isLockExpired();
	}

	private void unlockSeat(final Show show, final Seat seat) {
		if (!lock.containsKey(show)) {
			return;
		}
		lock.get(show).remove(seat);
	}

	@Override
	public boolean validateLock(Show show, Seat seat, String user) {
		return isSeatLocked(show, seat) && lock.get(show).get(seat).getLockedBy().equalsIgnoreCase(user);
	}

	@Override
	public List<Seat> fetchLockedSeats(Show show) {

		if (!lock.containsKey(show)) {
			return ImmutableList.of();
		}

		List<Seat> lockedSeats = new ArrayList<>();

		for (Seat seat : lock.get(show).keySet()) {
			if (isSeatLocked(show, seat)) {
				lockedSeats.add(seat);
			}
		}
		return lockedSeats;
	}

}
