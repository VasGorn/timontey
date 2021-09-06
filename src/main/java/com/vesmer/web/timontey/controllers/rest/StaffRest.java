package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.service.StaffService;

@RestController
@RequestMapping(path = "/rest/staff", produces = "application/json")
public class StaffRest {
	@Autowired
	private StaffService staffService;
	
	@GetMapping("/all")
	public List<Employee> staffGetAll(){
		return staffService.getAll();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee postEmployee(@RequestBody Employee employee) {
		return staffService.save(employee);
	}
	
	@PutMapping("/{id}")
	public Employee putEmployee(@RequestBody Employee employee) {
		return staffService.update(employee);
	}

}
