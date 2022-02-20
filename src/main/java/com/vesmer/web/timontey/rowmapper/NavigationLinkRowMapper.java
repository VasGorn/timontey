package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.NavigationLink;

public class NavigationLinkRowMapper implements RowMapper<NavigationLink> {

	@Override
	public NavigationLink mapRow(ResultSet rs, int rowNum) throws SQLException {
		NavigationLink navLink = new NavigationLink();
		navLink.setId(rs.getLong("id"));
		navLink.setName(rs.getString("name"));
		navLink.setUrl(rs.getString("url"));
		navLink.setIcon(rs.getString("icon"));
		return navLink;
	}

}
