package com.vesmer.web.timontey.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vesmer.web.timontey.domain.Team;
import com.vesmer.web.timontey.repository.TeamRepository;
import com.vesmer.web.timontey.service.TeamService;

@Service
public class TeamServiceImp implements TeamService {
	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Team getTeam(long performerId) {
		return teamRepository.getTeam(performerId);
	}
	
	@Override
	public Team save(Team team) {
		long performerId = team.getPerformer().getId();
		long employeeId = team.getEmployeeList().get(0).getId();
		return teamRepository.saveEmployeeToPerformer(performerId, employeeId);
	}
}
