package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.WorkType;

public interface WorkTypeService {

	List<WorkType> getWorkTypesByRole(long roleId);

}