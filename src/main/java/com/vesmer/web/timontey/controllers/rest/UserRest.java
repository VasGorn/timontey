package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.service.UserService;

@RestController
@RequestMapping(path = "/rest/users", produces = "application/json")
public class UserRest {
	@Autowired
	private UserService userService;


}
