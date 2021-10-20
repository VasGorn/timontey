package com.vesmer.web.timontey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

	@RequestMapping("/")
	public String getUserList() {
		return "users";
	}

}
