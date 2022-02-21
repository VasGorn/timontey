package com.vesmer.web.timontey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.service.StaffService;

@Controller
public class OrdersController {
	@Autowired
	private StaffService staffService;

	@RequestMapping("/orders")
	public ModelAndView getOrders() {
		long managerId = 3;
		Employee manager = staffService.getEmployeeById(managerId).get();
		String fullName = manager.getLastName() + " " + manager.getFirstName() + " " + manager.getMiddleName();
		
		ModelAndView model = new ModelAndView("orders");
		model.addObject("managerId", manager.getId());
		model.addObject("managerName", fullName);
		return model;
	}
}
