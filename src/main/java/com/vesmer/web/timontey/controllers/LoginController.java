package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
		
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginView(Model model, String error){
		if(error != null) {
			model.addAttribute("error", "Username or password is wrong!");
		}
		
		return "login";
	}

}
