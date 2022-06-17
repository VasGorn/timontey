package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Authority;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.repository.AuthorityRepository;
import com.vesmer.web.timontey.repository.RoleRepository;
import com.vesmer.web.timontey.rowmapper.RoleRowMapper;

@Repository
public class RoleJdbcRepo implements RoleRepository {
	private final JdbcTemplate jdbcTemplate;
	private final AuthorityRepository authorityRepository;

	@Autowired
	public RoleJdbcRepo(JdbcTemplate jdbcTemplate, AuthorityRepository authorityRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.authorityRepository = authorityRepository;
	}

	private static final String SELECT_ALL_SQL	= "SELECT * FROM roles;";
	private static final String SELECT_ROLES_FOR_USER_SQL =
		"SELECT r.id, r.role_name "
		+ "FROM roles AS r, user_role AS ur "
		+ "WHERE r.id = ur.role_id "
		+ "AND ur.username = ?;";
	private static final String INSERT_ROLE_TO_USER_SQL =
		"INSERT INTO user_role (username, role_id) "
		+ "VALUES (?, ?);";
	private static final String DELETE_ROLE_FROM_USER_SQL =
		"DELETE FROM user_role WHERE username=?;";

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
		for(Role role: list) {
			List<Authority> authorities = authorityRepository.getAuthoritiesForRole(role);
			role.setAuthorities(authorities);
		}
	
		return list;
	}

	@Override
	public void saveRoleToUser(User user, Role role) {
		jdbcTemplate.update(INSERT_ROLE_TO_USER_SQL, user.getUsername(), role.getId());
	}

	@Override
	public void deleteRolesFromUser(String username) {
		jdbcTemplate.update(DELETE_ROLE_FROM_USER_SQL, username);
	}
}
