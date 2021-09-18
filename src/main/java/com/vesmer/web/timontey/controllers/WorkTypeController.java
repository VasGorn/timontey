package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/work-type")
public class WorkTypeController {

	@RequestMapping("/")
	public String getWorkTypePage() {
		return "work-type";
	}

}
