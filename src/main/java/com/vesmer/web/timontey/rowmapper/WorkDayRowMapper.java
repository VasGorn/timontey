package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.WorkDay;


public class WorkDayRowMapper implements RowMapper<WorkDay> {

	@Override
	public WorkDay mapRow(ResultSet rs, int rowNum) throws SQLException {
		WorkDay workDay = new WorkDay();
		
		workDay.setId(rs.getLong("id"));
		
		Employee employee = new Employee();
		employee.setId(rs.getLong("employee_id"));
		workDay.setEmployee(employee);
		
		workDay.setNumDay(rs.getShort("num_day"));
		workDay.setWorkHours(rs.getShort("hours_spend"));
		workDay.setOvertime(rs.getShort("overtime"));
		workDay.setApprove(rs.getBoolean("approve"));
		
		return workDay;
	}
}
