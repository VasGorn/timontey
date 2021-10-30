package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private static final String INSERT_SQL = 
		"INSERT INTO users (username, password, employee_id) "
		+ "VALUES (?, ?, ?);";

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
		
		jdbcTemplate.update(INSERT_SQL, username, password, employeeId);
		
		return user;
	}

}
