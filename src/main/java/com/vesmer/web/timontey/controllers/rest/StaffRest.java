package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.Employee;

@RestController
@RequestMapping(path = "/rest/staff", produces = "application/json")
public class StaffRest {
	@Autowired
	private StaffService staffService;
	
	@GetMapping("/all")
	public List<Employee> staffGetAll(){
		return staffService.getAll();
	}

}
