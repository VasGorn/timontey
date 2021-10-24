package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.repository.UserRepository;

@Repository
public class UserJdbcRepo implements UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

}
