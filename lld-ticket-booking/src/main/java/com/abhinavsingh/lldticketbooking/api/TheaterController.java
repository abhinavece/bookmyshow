package com.abhinavsingh.lldticketbooking.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinavsingh.lldticketbooking.model.Screen;
import com.abhinavsingh.lldticketbooking.model.Theater;
import com.abhinavsingh.lldticketbooking.services.TheaterService;

import lombok.NonNull;

@RestController
@RequestMapping(value = "/theater")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;

	@RequestMapping(value = "/addtheater", method = RequestMethod.GET)
	public String createTheater(@NonNull @RequestParam("name") final String theaterName,
			@NonNull @RequestParam("place") final String place) {

		String theaterId = UUID.randomUUID().toString();

		return theaterService.createTheater(theaterId, theaterName, place).getId();

	}

	@GetMapping("/all")
	public List<Theater> getAllTheaters() {
		return theaterService.fetchAllTheaters();

	}

	// Create Screen In theater

	@GetMapping("/addscreen")
	public String createScreenInTheater(@NonNull @RequestParam("screenName") final String screenName,
			@NonNull @RequestParam("theaterId") final String theaterId) {

		final Theater theater = theaterService.getTheaterById(theaterId);
		return theaterService.createScreen(screenName, theater);

	}

	// Create Seat in the screen
	@GetMapping("/addSeat")
	public String addSeatToScreen(@NonNull final String screenId, @NonNull @RequestParam("rowNo") final String rowNo,
			@NonNull @RequestParam("seatNo") final String seatNo, @NonNull final double amount) throws Exception {

		final Screen screen = theaterService.findScreenById(screenId);
		return theaterService.addSeatToScreen(rowNo, seatNo, amount, screen).getSeatNo();
	}

}
