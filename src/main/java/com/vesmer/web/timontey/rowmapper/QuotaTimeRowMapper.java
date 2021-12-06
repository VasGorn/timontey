package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.domain.QuotaTime;

public class QuotaTimeRowMapper implements RowMapper<QuotaTime> {

	@Override
	public QuotaTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuotaTime quotaTime = new QuotaTime();
		
		quotaTime.setId(rs.getInt("id"));
		
		Order order = new Order();
		order.setId(rs.getInt("order_id"));
		quotaTime.setOrder(order);
		
		Employee employee = new Employee();
		employee.setId(rs.getInt("employee_id"));
		quotaTime.setEmployee(employee);
		
		quotaTime.setYear(rs.getShort("year"));

		return quotaTime;
	}

}
