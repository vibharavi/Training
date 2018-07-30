package com.reserve.Dao;

import com.reserve.bean.DiningReservation;
import com.reserve.bean.ResortReservation;
import com.reserve.util.GuestException;

public interface GuestDao {

	public DiningReservation getBookingDetails(int guestId) throws GuestException;

	public ResortReservation getResortDetails(int guestId) throws GuestException;

	public DiningReservation cancelDining(int diningReservationNum) throws GuestException;

	public ResortReservation cancelResort(int resortReservationNum) throws GuestException;
	
	public int bookResort(ResortReservation resortReservation, int guestId) throws GuestException;
	
	public int bookDining(DiningReservation diningReservation, int guestId) throws GuestException;
}
