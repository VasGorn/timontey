package com.vesmer.web.timontey.repository.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.repository.UserRepository;
import com.vesmer.web.timontey.rowmapper.UserRowMapper;

@Repository
public class UserJdbcRepo implements UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECT_ALL_SQL =
		"SELECT * FROM users;";
	private static final String SELECT_BY_USERNAME_SQL =
		"SELECT * FROM users WHERE username=?;";
	private static final String INSERT_SQL = 
		"INSERT INTO users (username, password, employee_id, status) "
		+ "VALUES (?, ?, ?, ?);";
	private static final String UPDATE_SQL =
		"UPDATE users SET password=?, employee_id=?, status=? WHERE username=?;";
	private static final String DELETE_SQL = 
		"DELETE FROM users WHERE username=?;";

	@Override
	public List<User> getAll() {
		List<User> list = (List<User>) jdbcTemplate.query(SELECT_ALL_SQL,
				new UserRowMapper());
		return list;
	}

	@Override
	public User save(User user) {
		String username = user.getUsername();
		long employeeId = user.getId();
		String password = user.getPassword();
		String status = user.getStatus().toString();
		
		jdbcTemplate.update(INSERT_SQL, username, password, employeeId, status);
		
		return user;
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_USERNAME_SQL, new UserRowMapper(), username));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public int update(User user) {
		return jdbcTemplate.update(UPDATE_SQL, user.getPassword(), 
				user.getId(), user.getStatus(), user.getUsername());
	}

	@Override
	public int delete(String username) {
		return jdbcTemplate.update(DELETE_SQL, username);
	}

}
