package com.abhinavsingh.lldticketbooking.model;

import lombok.Getter;

@Getter
public class Seat {

	private String seatNo;
	private String rowNo;
	private SeatClass seatClass;
	private double amount;

	public Seat(String seatId, String rowNo, SeatClass seatClass, double amount) {
		this.seatNo = seatId;
		this.rowNo = rowNo;
		this.seatClass = seatClass;
		this.amount = amount;
	}

}
