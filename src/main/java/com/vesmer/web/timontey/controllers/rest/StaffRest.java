package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PatchMapping(path = "/{id}", consumes = "application/json")
	public Employee patchEmployee(@PathVariable("id") Long id, @RequestBody Employee patch){
		Employee employee = staffService.getEmployeeById(id).get();
		if(patch.getLastName() != null) {
			employee.setLastName(patch.getLastName());
		}
		if(patch.getFirstName() != null) {
			employee.setFirstName(patch.getFirstName());
		}
		if(patch.getMiddleName() != null) {
			employee.setMiddleName(patch.getMiddleName());
		}
		return staffService.update(employee);
	}

}
