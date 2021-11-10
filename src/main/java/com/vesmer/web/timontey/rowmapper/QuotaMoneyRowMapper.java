package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.domain.QuotaMoney;

public class QuotaMoneyRowMapper implements RowMapper<QuotaMoney> {

	@Override
	public QuotaMoney mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuotaMoney quotaMoney = new QuotaMoney();

		Order order = new Order();
		Employee manager = new Employee();
		order.setId(rs.getLong("order_id"));
		manager.setId(rs.getLong("employee_id"));

		quotaMoney.setId(rs.getLong("id"));
		quotaMoney.setOrder(order);
		quotaMoney.setEmployee(manager);
		quotaMoney.setMoneyLimit(rs.getFloat("money_limit"));
		return quotaMoney;
	}

}
