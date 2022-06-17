package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Authority;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.repository.AuthorityRepository;
import com.vesmer.web.timontey.rowmapper.AuthorityRowMapper;

@Repository
public class AuthorityJdbcRepo implements AuthorityRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public AuthorityJdbcRepo(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final String SELECT_BY_ROLE_SQL	= 
		"SELECT a.id, a.name FROM authorities AS a, roles AS r, role_authorities AS ra"
		+ " WHERE a.id = ra.authorities_id AND r.id = ra.role_id"
		+ " AND r.id = ?;";

	@Override
	public List<Authority> getAuthoritiesForRole(Role role) {
		List<Authority> authorities = jdbcTemplate.query(SELECT_BY_ROLE_SQL, 
				new AuthorityRowMapper(), role.getId());
		return authorities;
	}
}
