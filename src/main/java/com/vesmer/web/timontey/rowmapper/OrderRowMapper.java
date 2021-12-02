package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;


public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		order.setId(rs.getLong("id"));
		order.setName(rs.getString("name"));
		
		Employee manager = new Employee();
		manager.setId(rs.getLong("manager_id"));
		order.setManager(manager);
		
		order.setDescription(rs.getString("description"));
		order.setAddress(rs.getString("address"));
		
		return order;
	}

}
