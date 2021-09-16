package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.service.RoleService;

@RestController
@RequestMapping(path="/rest/role", produces="application/json")
public class RoleRest {
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/all")
	public List<Role> roleGetAll(){
		return roleService.getAll();
	}

}
