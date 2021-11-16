package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;

public interface OrderRepository {

	List<Order> getOrders(Employee manager);

	long save(Order order);

	int update(Order order);

	void delete(long orderId);

	Optional<Order> findById(long id);

}
