package com.reserve.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reserve.bean.DiningReservation;
import com.reserve.bean.ResortReservation;
import com.reserve.bean.ResponseBooking;
import com.reserve.service.GuestService;
import com.reserve.util.GuestException;

@RestController
@RequestMapping("/response")
public class GuestController {

	@Autowired
	private GuestService guestService;

	@GetMapping("/bookings/{id}")
	public ResponseEntity<ResponseBooking> getTopics(@PathVariable("id") int guestId) throws GuestException {

		ResponseBooking booking = new ResponseBooking();

		List<DiningReservation> diningReservations = new ArrayList<>();
		DiningReservation diningReservation = guestService.getBookingDetails(guestId);

		List<ResortReservation> resortReservations = new ArrayList<>();
		ResortReservation resortReservation1 = guestService.getResortDetails(guestId);

		diningReservations.add(diningReservation);
		resortReservations.add(resortReservation1);

		booking.setGuestId(guestId);
		booking.setDiningReservations(diningReservations);
		booking.setResortReservations(resortReservations);

		return new ResponseEntity<ResponseBooking>(booking, HttpStatus.FOUND);

	}

	@RequestMapping(value = "/cancel-dining/{diningId}", method = RequestMethod.PUT)
	public ResponseEntity<DiningReservation> updateDining(@PathVariable("diningId") int diningReservationNum) throws GuestException {

		HttpHeaders headers = new HttpHeaders();
		DiningReservation diningReservation = guestService.cancelDining(diningReservationNum);
		headers.add("Cancel Updated - ", String.valueOf(diningReservationNum));
		return new ResponseEntity<DiningReservation>(diningReservation, headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/cancel-resort/{resortId}", method = RequestMethod.PUT)
	public ResponseEntity<ResortReservation> updateResort(@PathVariable("resortId") int resortReservationNum) throws GuestException{

		HttpHeaders headers = new HttpHeaders();
		ResortReservation resortReservation = guestService.cancelResort(resortReservationNum);
		headers.add("Cancel Updated - ", String.valueOf(resortReservationNum));
		return new ResponseEntity<ResortReservation>(resortReservation, headers, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/add-resort/{guestId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Integer> addResortDetails(@RequestBody ResortReservation resort, @PathVariable("guestId") int guestId) 
			throws GuestException{
		
		HttpHeaders headers = new HttpHeaders();
		if (resort == null) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		int reservationNumber = guestService.bookResort(resort, guestId);
		headers.add("Resort Booked  - ", String.valueOf(reservationNumber));
		return new ResponseEntity<Integer>(reservationNumber, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/add-dining/{guestId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Integer> addDiningDetails(@RequestBody DiningReservation dining, @PathVariable("guestId") int guestId)
			throws GuestException{
		
		HttpHeaders headers = new HttpHeaders();
		if (dining == null) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		int reservationNumber = guestService.bookDining(dining, guestId);
		headers.add("Dining Booked  - ", String.valueOf(reservationNumber));
		return new ResponseEntity<Integer>(reservationNumber, headers, HttpStatus.CREATED);
	}

}
