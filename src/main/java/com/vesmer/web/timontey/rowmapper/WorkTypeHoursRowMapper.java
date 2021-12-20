package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.domain.WorkTypeHours;

public class WorkTypeHoursRowMapper implements RowMapper<WorkTypeHours> {

	@Override
	public WorkTypeHours mapRow(ResultSet rs, int rowNum) throws SQLException {
		WorkTypeHours workTypeHours = new WorkTypeHours();
		
		workTypeHours.setId(rs.getInt("id"));
		
		WorkType workType = new WorkType();
		workType.setId(rs.getLong("work_type_id"));
		workTypeHours.setWorkType(workType);
		
		workTypeHours.setNumMonth(rs.getShort("num_month"));
		workTypeHours.setHours(rs.getInt("hours"));
		
		return workTypeHours;
	}

}
