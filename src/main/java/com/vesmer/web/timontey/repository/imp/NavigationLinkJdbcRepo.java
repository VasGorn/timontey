package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.NavigationLink;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.repository.NavigationLinkRepository;
import com.vesmer.web.timontey.rowmapper.NavigationLinkRowMapper;

@Repository
public class NavigationLinkJdbcRepo implements NavigationLinkRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	private static final String SELECT_NAVIGATION_BY_ROLE_SQL = 
		"SELECT n.id, n.name, n.url, n.icon "
		+ "FROM navigation_link AS n, role_navigation AS rn, roles AS r "
		+ "WHERE n.id = rn.navigation_id AND r.id = rn.role_id AND r.id = ? "
		+ "ORDER BY n.name;";

	@Override
	public List<NavigationLink> getNavigationByRole(Role role) {
		return jdbcTemplate.query(SELECT_NAVIGATION_BY_ROLE_SQL, 
				new NavigationLinkRowMapper(), role.getId());
	}
}
