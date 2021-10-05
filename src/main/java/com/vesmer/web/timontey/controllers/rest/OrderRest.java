package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.service.OrderService;

@RestController
@RequestMapping(path = "/rest/orders", produces = "application/json")
public class OrderRest {
	@Autowired 
	private OrderService orderService;

}
