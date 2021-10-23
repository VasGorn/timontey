package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.UserService;

@RestController
@RequestMapping(path = "/rest/users", produces = "application/json")
public class UserRest {
	@Autowired
	private UserService userService;
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userService.getAll();
	}

}
