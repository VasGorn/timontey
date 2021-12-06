package com.vesmer.web.timontey.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.service.StaffService;

@Controller
public class QuotaTimeController {
	@Autowired
	private StaffService staffService;
	
	@RequestMapping("/quota-time")
	public ModelAndView getQuotaTime() {
		long managerId = 3;
		Employee manager = staffService.getEmployeeById(managerId).get();
		String fullName = manager.getLastName() + " " + manager.getFirstName() + " " + manager.getMiddleName();
		
		List<String> years = getYearsList();
		HashMap<Integer, String> months = getMonthsMap();
		
		ModelAndView model = new ModelAndView("quota-time");
		model.addObject("managerId", manager.getId());
		model.addObject("managerName", fullName);
		model.addObject("years", years);
		model.addObject("months", months);
		return model;
	}

}
