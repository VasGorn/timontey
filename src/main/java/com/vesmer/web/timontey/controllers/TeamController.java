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
}
