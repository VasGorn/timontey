package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.repository.OrderRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.service.OrderService;

@Service
@Transactional
public class OrderServiceImp implements OrderService {
	private final OrderRepository orderRepository;
	private final StaffRepository staffRepository;

	@Autowired
	public OrderServiceImp(OrderRepository orderRepository, StaffRepository staffRepository) {
		this.orderRepository = orderRepository;
		this.staffRepository = staffRepository;
	}

	@Override
	public List<Order> getOrders(long managerId) {
		Employee manager = staffRepository.findById(managerId).get();
		List<Order> orderList = orderRepository.getOrders(manager);
		return orderList;
	}

	@Override
	public Order save(Order order) {
		Employee manager = staffRepository.findById(order.getManager().getId()).get();
		long orderId = orderRepository.save(order);
		order.setId(orderId);
		order.setManager(manager);
		return order;
	}

	@Override
	public Order update(Order order) {
		orderRepository.update(order);
		return order;
	}

	@Override
	public void delete(long orderId) {
		try {
			orderRepository.delete(orderId);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Data not found: " + e.getMessage());
		}
	}
}
