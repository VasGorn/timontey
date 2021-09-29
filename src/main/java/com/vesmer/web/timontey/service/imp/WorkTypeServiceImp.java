package com.vesmer.web.timontey.service.imp;

import java.util.List;
import java.util.Optional;

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

	@Override
	public WorkType save(WorkType workType, long roleId) {
		long id = workTypeRepository.save(roleId, workType);
		workType.setId(id);
		return workType;
	}

	@Override
	public void delete(Long roleId, Long workTypeId) {
		workTypeRepository.delete(roleId, workTypeId);
	}

	@Override
	public Optional<WorkType> getWorkTypeById(long id) {
		return workTypeRepository.findById(id);
	}

}
