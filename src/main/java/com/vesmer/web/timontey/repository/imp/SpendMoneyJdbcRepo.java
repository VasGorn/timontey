package com.vesmer.web.timontey.repository.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpendMoneyJdbcRepo {
	@Autowired
	private JdbcTemplate jdbcTemplate;

}
