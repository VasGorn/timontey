package com.vesmer.web.timontey.service;

import com.vesmer.web.timontey.domain.Team;

public interface TeamService {

	Team getTeam(long performerId);

	Team save(Team team);

}
