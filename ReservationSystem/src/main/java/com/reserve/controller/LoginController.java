package com.reserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reserve.bean.Guest;
import com.reserve.service.LoginService;
import com.reserve.util.GuestException;

@RestController
@RequestMapping("/guest-login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/add-guest", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Integer> addGuestDetails(@RequestBody Guest guest) throws Exception {
		int guestId = 0;
		HttpHeaders headers = new HttpHeaders();
		try {
			if (guest == null) {
				return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			}
			guestId = loginService.addGuest(guest);

			if (guestId == 0) {
				return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
			}
		} catch (GuestException g) {
			throw new GuestException("Given Email Id is already present:-" + guest.getEmail());
		}
		headers.add("Guest Created  - ", String.valueOf(guest.getGuestId()));
		return new ResponseEntity<Integer>(guestId, headers, HttpStatus.CREATED);
	}

	@PostMapping("/validate")
	public ResponseEntity<String> validate(@RequestBody Guest guest) throws GuestException {
		String result = "guest invalid";
		boolean v = loginService.validateGuest(guest.getEmail(), guest.getPassword());
		if (v != false) {
			result = "guest valid";
			return new ResponseEntity<String>(result, HttpStatus.FOUND);
		}
		return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
	}

}
