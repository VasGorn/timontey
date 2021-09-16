package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.repository.RoleRepository;
import com.vesmer.web.timontey.rowmapper.RoleRowMapper;

@Repository
public class RoleJdbcRepo implements RoleRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECT_ALL_SQL	= "SELECT * FROM roles;";

	@Override
	public List<Role> getAll() {
		List<Role> roleList = (List<Role>) jdbcTemplate.query(SELECT_ALL_SQL,
				new RoleRowMapper());
		return roleList;
	}

}
