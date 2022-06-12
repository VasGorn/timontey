package com.vesmer.web.timontey.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.UserService;

@Controller
public class SpendMoneyController {
	private final UserService userService;
	
	@Autowired
	public SpendMoneyController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/spend-money")
	public ModelAndView getSpendMoneyPage(Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).get();
		long employeeId = user.getId();
		
		ModelAndView model = new ModelAndView("spend-money");
		model.addObject("employeeId", employeeId);
		
		return model;
	}

}
