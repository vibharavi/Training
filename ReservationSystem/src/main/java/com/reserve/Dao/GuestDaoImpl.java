package com.reserve.Dao;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.reserve.bean.DiningReservation;
import com.reserve.bean.ResortReservation;
import com.reserve.mapper.DiningRowMapper;
import com.reserve.mapper.ResortRowMapper;
import com.reserve.util.GuestException;

@Transactional
@Repository
public class GuestDaoImpl implements GuestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());

	int resortNumber = 0;
	int diningNumber = 0;

	public DiningReservation getBookingDetails(int guestId) throws GuestException {
		DiningReservation diningReservation = null;
		RowMapper<DiningReservation> rowMapper = new DiningRowMapper();
		try {
			diningReservation = jdbcTemplate.queryForObject("SELECT * FROM dining WHERE guest_id = ?",
					new Object[] { guestId }, rowMapper);
		} catch (DataAccessException e) {
			throw new GuestException("Dining booking is not present for given guest" + guestId);
		}
		return diningReservation;

	}

	@Override
	public ResortReservation getResortDetails(int guestId) throws GuestException {
		ResortReservation resortReservation = null;
		RowMapper<ResortReservation> rowmapper = new ResortRowMapper();
		try {
			resortReservation = jdbcTemplate.queryForObject("SELECT * FROM resort WHERE guest_id = ?",
					new Object[] { guestId }, rowmapper);

		} catch (DataAccessException e) {
			throw new GuestException("Resort booking is not present for given guest" + guestId);
		}
		return resortReservation;
	}

	@Override
	public DiningReservation cancelDining(int diningReservationNum) throws GuestException {
		DiningReservation diningReservation = null;
		RowMapper<DiningReservation> rowMapper = new DiningRowMapper();
		int count = 0;
		try {
			count = jdbcTemplate.update("UPDATE dining set status = ? where d_reservation_number = ?",
					new Object[] { "CANCELED", diningReservationNum });
			if (count != 0) {
				diningReservation = jdbcTemplate.queryForObject("SELECT * FROM dining WHERE d_reservation_number = ?",
						new Object[] { diningReservationNum }, rowMapper);
			}
		} catch (Exception e) {
			throw new GuestException("Could not cancel booking");
		}

		return diningReservation;
	}

	@Override
	public ResortReservation cancelResort(int resortReservationNum) throws GuestException {
		ResortReservation resortReservation = null;
		RowMapper<ResortReservation> rowMapper = new ResortRowMapper();
		int count = 0;
		try {
			count = jdbcTemplate.update("UPDATE resort set status = ? where r_reservation_number = ?",
					new Object[] { "CANCELED", resortReservationNum });
			if (count != 0) {
				resortReservation = jdbcTemplate.queryForObject("SELECT * FROM resort WHERE r_reservation_number = ?",
						new Object[] { resortReservationNum }, rowMapper);
			}
		} catch (Exception e) {
			throw new GuestException("Could not cancel booking");
		}

		return resortReservation;
	}

	@Override
	public int bookResort(ResortReservation resortReservation, int guestId) throws GuestException {
		int count = 0;
		int resortReservationNumber = 0;
		String status = "BOOKED";
		try {

			count = jdbcTemplate.update(
					"insert into resort (r_reservation_number,guest_id,room_type,arrival_date,departure_date,no_of_people,status,created_date,updated_date) VALUES(?,?,?,?,?,?,?,?,?)",
					new Object[] { ++resortNumber, guestId, resortReservation.getRoomType(),
							resortReservation.getArrivalDate(), resortReservation.getDepartureDate(),
							resortReservation.getNoOfPeople(), status, sqlDate, sqlDate });

			if (count != 0) {
				ResortReservation resortReservation1 = null;
				RowMapper<ResortReservation> rowmapper = new ResortRowMapper();
				resortReservation1 = jdbcTemplate.queryForObject("SELECT * FROM resort WHERE guest_id = ?",
						new Object[] { guestId }, rowmapper);
				resortReservationNumber = resortReservation1.getReservationNumber();
			}

		} catch (Exception e) {
			throw new GuestException("Information provided are incorrect");
		}
		return resortReservationNumber;
	}

	@Override
	public int bookDining(DiningReservation diningReservation, int guestId) throws GuestException {
		int count = 0;
		int diningReservationNumber = 0;
		String status = "BOOKED";
		try {

			count = jdbcTemplate.update(
					"insert into dining (d_reservation_number,guest_id,dining_type,arrival_date,no_of_people,status,created_date,updated_date) VALUES(?,?,?,?,?,?,?,?)",
					new Object[] { ++diningNumber, guestId, diningReservation.getDiningType(),
							diningReservation.getArrivalDate(), diningReservation.getNoOfPeople(), status, sqlDate,
							sqlDate });

			if (count != 0) {
				DiningReservation diningReservation1 = null;
				RowMapper<DiningReservation> rowmapper = new DiningRowMapper();
				diningReservation1 = jdbcTemplate.queryForObject("SELECT * FROM dining WHERE guest_id = ?",
						new Object[] { guestId }, rowmapper);
				diningReservationNumber = diningReservation1.getDiningReservationNum();
			}

		} catch (Exception e) {
			throw new GuestException("Information provided are incorrect");
		}
		return diningReservationNumber;
	}

}