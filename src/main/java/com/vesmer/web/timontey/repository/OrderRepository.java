package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;

public interface OrderRepository {

	List<Order> getOrders(Employee manager);

	long save(Order order);

}
