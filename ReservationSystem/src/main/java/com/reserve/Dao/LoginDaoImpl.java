package com.reserve.Dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.reserve.bean.Guest;
import com.reserve.mapper.GuestRowMapper;
import com.reserve.util.GuestException;

@Transactional
@Repository
public class LoginDaoImpl implements LoginDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	int id = 0;

	Date date = new Date();
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());

	@Override
	public int addGuest(Guest guest) throws GuestException {
		int count = 0;
		RowMapper<Guest> rowMapper = new GuestRowMapper();

		String email = guest.getEmail();
		String sql = "select count(*) from guest";
		id = queryForInt(sql);
		for (int i = 1; i <= id; i++) {
			Guest guest1 = jdbcTemplate.queryForObject("SELECT * from guest WHERE guest_id = ?", new Object[] { i },
					rowMapper);
			if (email.equalsIgnoreCase(guest1.getEmail())) {
				/*throw new GuestException("Given Email Id is already present:-" + guest.getEmail());*/
				return 0;
			}
		}

		count = jdbcTemplate.update(
				"insert into guest (guest_id,email,first_name,last_name,address,phone,password,created_date,updated_date) VALUES(?,?,?,?,?,?,?,?,?)",
				new Object[] { ++id, guest.getEmail(), guest.getFirstName(), guest.getLastName(), guest.getAddress(),
						guest.getPhone(), guest.getPassword(), sqlDate, sqlDate });

		if (count == 0) {
			throw new GuestException("Guest details provided is incorrect");
		}
		return id;
	}

	@Override
	public boolean validateGuest(String emailId, String password) throws GuestException {
		RowMapper<Guest> rowmapper = new GuestRowMapper();
		Guest guest = new Guest();
		try {
			guest = jdbcTemplate.queryForObject("SELECT * FROM guest WHERE email = ? AND password= ?",
					new Object[] { emailId, password }, rowmapper);
		} catch (DataAccessException e) {
			throw new GuestException("Login credentials are incorrect");
		}
		return true;
	}

	@Deprecated
	public int queryForInt(String sql) throws DataAccessException {
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
