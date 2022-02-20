package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.NavigationLink;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.repository.NavigationLinkRepository;

@Repository
public class NavigationLinkJdbcRepo implements NavigationLinkRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;

}
