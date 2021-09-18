package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/work-type", produces = "application/json")
public class WorkTypeRest {
	@Autowired
	private WorkTypeService workTypeService;

}
