package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaffController {

	@RequestMapping("/staff")
	public String getStaffList() {
		return "staff";
	}

}
