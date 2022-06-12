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
public class TeamController {
	private final StaffService staffService;
	private final UserService userService;
	
	@Autowired
	public TeamController(StaffService staffService, UserService userService) {
		this.staffService = staffService;
		this.userService = userService;
	}
		
	@RequestMapping("/team")
	public ModelAndView getTeamView(Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).get();
		long performerId = user.getId();
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
