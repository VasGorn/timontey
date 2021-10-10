package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.Order;

public interface OrderService {

	List<Order> getOrders(long managerId);

	Order save(Order order);

}
