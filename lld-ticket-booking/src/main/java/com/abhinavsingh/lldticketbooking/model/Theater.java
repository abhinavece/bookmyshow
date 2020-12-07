package com.abhinavsingh.lldticketbooking.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

//@Getter
public class Theater {

	@Getter
	private final String id;
	private final String name;
	private final List<Screen> screens;
	private final String location;

	public Theater(String id, String name, String location) {
		this.id = id;
		this.name = name;
		this.screens = new ArrayList<Screen>();
		this.location = location;
	}

	// Add Screens if needed to the theater
	public void addScreen(Screen screen) {
		this.screens.add(screen);
	}

	public String getName() {
		return name;
	}

	public List<Screen> getScreens() {
		return screens;
	}

	public String getLocation() {
		return location;
	}

}
