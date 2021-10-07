package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.service.OrderService;

public class OrderServiceImp implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private StaffRepository staffRepository;

	@Override
	public List<Order> getOrders(long managerId) {
		Employee manager = staffRepository.findById(managerId).get();
		List<Order> orderList = orderRepository.getOrders(manager);
		for(Order order: orderList) {
			order.setManager(manager);
		}
		return orderList;
	}

}
