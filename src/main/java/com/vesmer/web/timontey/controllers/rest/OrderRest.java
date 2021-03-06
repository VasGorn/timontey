package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.Order;
import com.vesmer.web.timontey.service.OrderService;

@RestController
@RequestMapping(path = "/rest/orders", produces = "application/json")
public class OrderRest {
	private final OrderService orderService;
	
	@Autowired 
	public OrderRest(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/{manager_id}")
	public List<Order> getOrders(@PathVariable("manager_id") long managerId) {
		return orderService.getOrders(managerId);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Order postOrder(@RequestBody Order order) {
		return orderService.save(order);
	}
	
	@PutMapping(consumes = "application/json")
	public Order putOrder(@RequestBody Order order) {
		return orderService.update(order);
	}
	
	@DeleteMapping("/{order_id}")
	public void deleteOrder(@PathVariable("order_id") long orderId) {
		orderService.delete(orderId);
	}
}
