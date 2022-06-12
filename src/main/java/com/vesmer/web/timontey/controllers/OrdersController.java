package com.vesmer.web.timontey.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.StaffService;
import com.vesmer.web.timontey.service.UserService;

@Controller
public class OrdersController {
	private final StaffService staffService;
	private final UserService userService;
	
	@Autowired
	public OrdersController(StaffService staffService, UserService userService) {
		this.staffService = staffService;
		this.userService = userService;
	}

	@RequestMapping("/orders")
	public ModelAndView getOrders(Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).get();
		long managerId = user.getId();
		Employee manager = staffService.getEmployeeById(managerId).get();
		String fullName = manager.getLastName() 
				+ " " + manager.getFirstName() 
				+ " " + manager.getMiddleName();
		
		ModelAndView model = new ModelAndView("orders");
		model.addObject("managerId", manager.getId());
		model.addObject("managerName", fullName);
		return model;
	}
}
