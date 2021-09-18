package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.service.WorkTypeService;

@RestController
@RequestMapping(path = "/rest/work-type", produces = "application/json")
public class WorkTypeRest {
	@Autowired
	private WorkTypeService workTypeService;
	
	@GetMapping(path = "/role/{id}")
	public List<WorkType> getWorkTypesByRole(@PathVariable("id") long roleId){
		return workTypeService.getWorkTypesByRole(roleId);
	}

}
