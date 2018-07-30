package com.reserve.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.reserve.bean.DiningReservation;

public class DiningRowMapper implements RowMapper<DiningReservation> {

	@Override
	public DiningReservation mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DiningReservation dining = new DiningReservation();
		
		dining.setDiningReservationNum(rs.getInt("d_reservation_number"));
		dining.setGuestId(rs.getInt("guest_id"));
		dining.setDiningType(rs.getString("dining_type"));
		dining.setArrivalDate(rs.getDate("arrival_date"));
		dining.setNoOfPeople(rs.getInt("no_of_people"));
		dining.setStatus(rs.getString("status"));
		dining.setCreatedDate(rs.getDate("created_date"));
		dining.setUpdatedDate(rs.getDate("updated_date"));
		
		return dining;
		
	}



}
