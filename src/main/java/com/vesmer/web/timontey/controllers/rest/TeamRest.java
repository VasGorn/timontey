package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.service.TeamService;

@RestController
@RequestMapping(path = "/rest/team", produces = "application/json")
public class TeamRest {
	@Autowired
	private TeamService teamService;

}
