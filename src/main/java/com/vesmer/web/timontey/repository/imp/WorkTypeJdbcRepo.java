package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.repository.WorkTypeRepository;

@Repository
public class WorkTypeJdbcRepo implements WorkTypeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

}
