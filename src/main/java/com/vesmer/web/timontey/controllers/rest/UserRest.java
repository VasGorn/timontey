package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public User postUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@PutMapping(path = "/{username}", consumes = "application/json")
	public User putUser(@RequestBody User user) {
		return userService.update(user);
	}
	
	@DeleteMapping("/{username}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("username") String username) {
		userService.delete(username);
	}

}
