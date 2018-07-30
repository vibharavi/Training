package com.reserve.Dao;

import com.reserve.bean.Guest;
import com.reserve.util.GuestException;

public interface LoginDao {
	
	public int addGuest(Guest guest) throws GuestException;
	
	public boolean validateGuest(String emailId, String password) throws GuestException;

}
