package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@RequestMapping("/")
	public String getStaffList() {
		return "staff";
	}

}
