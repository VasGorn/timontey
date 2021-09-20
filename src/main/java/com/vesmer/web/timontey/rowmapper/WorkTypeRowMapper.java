package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.WorkType;

public class WorkTypeRowMapper implements RowMapper<WorkType> {

	@Override
	public WorkType mapRow(ResultSet rs, int rowNum) throws SQLException {
		WorkType workType = new WorkType();
		workType.setId(rs.getLong("id"));
		workType.setWorkTypeName(rs.getString("work_name"));
		return workType;
	}

}
