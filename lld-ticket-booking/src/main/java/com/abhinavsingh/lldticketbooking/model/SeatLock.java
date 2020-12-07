package com.abhinavsingh.lldticketbooking.model;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SeatLock {

	final private Show show;
	final private Seat seat;
	final private Date lockTime;
	final private long timeoutInSeconds;
	final private String lockedBy;

	public boolean isLockExpired() {
		final Instant lockInstant = lockTime.toInstant().plusSeconds(timeoutInSeconds);
		final Instant currentInstant = new Date().toInstant();
		return lockInstant.isBefore(currentInstant);
	}
}
