package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkTypeController {

	@RequestMapping("/work-type")
	public String getWorkTypePage() {
		return "work-type";
	}

}
