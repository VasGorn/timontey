package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExpensesController {
	
	@RequestMapping("/expenses")
	public String getExpensesPage(Model model) {
		return "expenses";
	}

}
