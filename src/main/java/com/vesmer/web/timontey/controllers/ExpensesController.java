package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("expenses")
public class ExpensesController {
	
	@RequestMapping("/")
	public String getExpensesPage(Model model) {
		return "expenses";
	}

}
