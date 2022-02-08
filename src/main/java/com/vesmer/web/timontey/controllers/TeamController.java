package com.vesmer.web.timontey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.service.StaffService;

@Controller
public class TeamController {
	@Autowired
	private StaffService staffService;
		
	@RequestMapping("/team")
	public ModelAndView getTeamView() {
		long performerId = 12;
		Employee performer = staffService.getEmployeeById(performerId).get();
		String fullName = performer.getLastName() + " " 
							+ performer.getFirstName() + " " 
							+ performer.getMiddleName();
		
		ModelAndView model = new ModelAndView("team");
		model.addObject("performerId", performer.getId());
		model.addObject("performerName", fullName);
		return model;
	}
}
