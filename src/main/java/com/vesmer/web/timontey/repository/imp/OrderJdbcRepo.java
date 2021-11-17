package com.vesmer.web.timontey.repository.imp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.rowmapper.OrderRowMapper;

@Repository
public class OrderJdbcRepo implements OrderRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECT_ALL_SQL	= 
			"SELECT * FROM orders WHERE manager_id=?;";
	private static final String SELECT_ONE_SQL	= 
			"SELECT * FROM orders WHERE id=?;";
	private static final String INSERT_SQL		= 
			"INSERT INTO orders (name, manager_id, description, address) "
			+ "VALUES (?, ?, ?, ?);";
	private static final String UPDATE_SQL		= 
			"UPDATE orders SET name=?, description=?, address=? WHERE id=?;";
	private static final String DELETE_SQL		= 
			"DELETE FROM orders WHERE id=?;";

	@Override
	public List<Order> getOrders(Employee manager) {
		List<Order> orderList = jdbcTemplate.query(SELECT_ALL_SQL,
				new OrderRowMapper(), manager.getId());
		return orderList;
	}

	@Override
	public long save(Order order) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, order.getName());
	          ps.setLong(2, order.getManager().getId());
	          ps.setString(3, order.getDescription());
	          ps.setString(4, order.getAddress());
	          return ps;
	        }, keyHolder);
	    
	    long newId;
	    
	    if (keyHolder.getKeys().size() > 1) {
	        newId = ((Number) keyHolder.getKeys().get("id")).longValue();
	    } else {
	        newId= keyHolder.getKey().longValue();
	    }

        return newId;
	}

	@Override
	public int update(Order order) {
		return jdbcTemplate.update(UPDATE_SQL, order.getName(), 
				order.getDescription(), order.getAddress(), order.getId());
	}

	@Override
	public void delete(long orderId) {
		jdbcTemplate.update(DELETE_SQL, orderId);
	}

	@Override
	public Optional<Order> findById(long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_ONE_SQL, 
				new OrderRowMapper(), id));
		} catch (EmptyResultDataAccessException ex){
			return Optional.empty();
		}
	}

}
