package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpendMoneyController {
	
	@RequestMapping("/spend-money")
	public ModelAndView getSpendMoneyPage() {
		long employeeId = 12;
		
		ModelAndView model = new ModelAndView("spend-money");
		model.addObject("employeeId", employeeId);
		
		return model;
	}

}
