package com.vesmer.web.timontey.repository;

import com.vesmer.web.timontey.domain.Team;

public interface TeamRepository {

	Team getTeam(long performerId);

	Team saveEmployeeToPerformer(long performerId, long employeeId);

	int delete(long performerId, long employeeId);

}
