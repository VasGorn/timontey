package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.domain.enumeration.UserStatus;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setId(rs.getLong("employee_id"));
			
		String strUserStatus = rs.getString("status");
		UserStatus userStatus = UserStatus.valueOf(strUserStatus);
		user.setStatus(userStatus);
		
		return user;
	}

}
