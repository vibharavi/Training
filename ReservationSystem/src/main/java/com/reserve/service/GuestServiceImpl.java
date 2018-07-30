package com.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reserve.Dao.GuestDao;
import com.reserve.bean.DiningReservation;
import com.reserve.bean.ResortReservation;
import com.reserve.util.GuestException;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	private GuestDao guestDao;

	@Override
	public DiningReservation getBookingDetails(int guestId) throws GuestException {

		return guestDao.getBookingDetails(guestId);
	}

	@Override
	public ResortReservation getResortDetails(int guestId) throws GuestException {
		return guestDao.getResortDetails(guestId);
	}

	@Override
	public DiningReservation cancelDining(int diningReservationNum) throws GuestException{
		return guestDao.cancelDining(diningReservationNum);
	}

	@Override
	public ResortReservation cancelResort(int resortReservationNum) throws GuestException {
		return guestDao.cancelResort(resortReservationNum);
	}

	@Override
	public int bookResort(ResortReservation resortReservation, int guestId) throws GuestException{
		return guestDao.bookResort(resortReservation, guestId);
	}

	@Override
	public int bookDining(DiningReservation diningReservation, int guestId) throws GuestException{
		return guestDao.bookDining(diningReservation, guestId);
	}
}
