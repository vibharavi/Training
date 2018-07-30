package com.reserve.service;

import com.reserve.bean.Guest;
import com.reserve.util.GuestException;

public interface LoginService {

	public int addGuest(Guest guest) throws Exception;
	
	public boolean validateGuest(String emailId, String password) throws GuestException;
}
