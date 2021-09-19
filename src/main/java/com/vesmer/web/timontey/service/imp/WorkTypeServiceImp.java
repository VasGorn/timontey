package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.repository.WorkTypeRepository;
import com.vesmer.web.timontey.service.WorkTypeService;

@Service
@Transactional
public class WorkTypeServiceImp implements WorkTypeService {
	@Autowired
	private WorkTypeRepository workTypeRepository;

	@Override
	public List<WorkType> getWorkTypesByRole(long roleId) {
		return workTypeRepository.getWorkTypesByRole(roleId);
	}

}
