package com.abhinavsingh.lldticketbooking.model;

import java.util.Date;

import lombok.Getter;

@Getter
public class Show {

	private final String showId;
	private final String showName;
	private final Movie movie;
	private Date startTime;
	private long durationInSeconds;
	private Theater theater;
	private Screen screen;

	public Show(String showId, String showName, Movie movie, long durationInSeconds, Theater theater, Screen screen) {
		this.showId = showId;
		this.showName = showName;
		this.movie = movie;
		this.startTime = new Date();
		this.durationInSeconds = durationInSeconds;
		this.theater = theater;
		this.screen = screen;
	}

}
