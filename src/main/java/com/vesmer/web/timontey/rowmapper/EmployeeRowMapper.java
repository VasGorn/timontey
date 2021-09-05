package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Employee;


public class EmployeeRowMapper implements RowMapper<Employee> {
	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getLong("id"));
		employee.setLastName(rs.getString("last_name"));
		employee.setFirstName(rs.getString("first_name"));
		employee.setMiddleName(rs.getString("middle_name"));
		return employee;
	}
}
