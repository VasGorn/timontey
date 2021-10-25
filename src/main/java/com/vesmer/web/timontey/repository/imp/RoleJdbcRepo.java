package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.repository.RoleRepository;
import com.vesmer.web.timontey.rowmapper.RoleRowMapper;

@Repository
public class RoleJdbcRepo implements RoleRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECT_ALL_SQL	= "SELECT * FROM roles;";
	private static final String SELECT_ROLES_FOR_USER_SQL =
		"SELECT r.id, r.role_name "
		+ "FROM roles AS r, user_role AS ur "
		+ "WHERE r.id = ur.role_id "
		+ "AND ur.username = ?;";

	@Override
	public List<Role> getAll() {
		List<Role> roleList = (List<Role>) jdbcTemplate.query(SELECT_ALL_SQL,
				new RoleRowMapper());
		return roleList;
	}
	
	@Override
	public List<Role> getRolesForUser(User user) {
		List<Role> list = (List<Role>) jdbcTemplate.query(SELECT_ROLES_FOR_USER_SQL,
				new RoleRowMapper(), user.getUsername());
		return list;
	}

}
