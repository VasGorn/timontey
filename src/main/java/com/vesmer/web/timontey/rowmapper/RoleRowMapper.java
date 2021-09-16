package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Role;

public class RoleRowMapper implements RowMapper<Role> {

	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role role = new Role();
		role.setId(rs.getLong("id"));
		role.setRoleName(rs.getString("role_name"));
		return role;
	}

}
