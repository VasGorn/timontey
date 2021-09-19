package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.WorkType;

public interface WorkTypeRepository {

	List<WorkType> getWorkTypesByRole(long roleId);

}
