package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.Team;
import com.vesmer.web.timontey.service.TeamService;

@RestController
@RequestMapping(path = "/rest/team", produces = "application/json")
public class TeamRest {
	@Autowired
	private TeamService teamService;
		
	@GetMapping("/performer/{performerId}")
	public Team getTeamByPerformerId(
			@PathVariable("performerId") long performerId ) {
		return teamService.getTeam(performerId);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Team saveEmployeeToPerformer(@RequestBody Team team) {
		return teamService.save(team);
	}
		
	@DeleteMapping("/performer/{performerId}/employee/{employeeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUser(
			@PathVariable("performerId") long performerId,
			@PathVariable("employeeId") long employeeId) {
		teamService.delete(performerId, employeeId);
	}
}
