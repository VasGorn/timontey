package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.rowmapper.OrderRowMapper;

public class OrderJdbcRepo implements OrderRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECT_ALL_SQL	= 
			"SELECT * FROM orders WHERE manager_id=?;";

	@Override
	public List<Order> getOrders(Employee manager) {
		List<Order> orderList = jdbcTemplate.query(SELECT_ALL_SQL,
				new OrderRowMapper(), manager.getId());
		return orderList;
	}

}
