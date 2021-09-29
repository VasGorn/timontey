package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.service.WorkTypeService;

@RestController
@RequestMapping(path = "/rest/work-type", produces = "application/json")
public class WorkTypeRest {
	@Autowired
	private WorkTypeService workTypeService;
	
	@GetMapping(path = "/role/{id}")
	public List<WorkType> getWorkTypesByRole(@PathVariable("id") long roleId){
		return workTypeService.getWorkTypesByRole(roleId);
	}
	
	@PostMapping(path = "/role/{id}", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public WorkType postWorkTypeToRole(@RequestBody WorkType workType, @PathVariable("id") long roleId) {
		return workTypeService.save(workType, roleId);
	}
	
	@PatchMapping(path = "/{workTypeId}/role/{roleId}", consumes = "application/json")
	public WorkType patchWorkType(@PathVariable("workTypeId") Long workTypeId, @PathVariable("roleId") Long roleId,
			@RequestBody WorkType patch){
		WorkType workType = workTypeService.getWorkTypeById(workTypeId).get();
		if(patch.getWorkTypeName() != null) {
			workType.setWorkTypeName(patch.getWorkTypeName());
		}
		return workTypeService.update(workType);
	}
	
	@DeleteMapping("/{workTypeId}/role/{roleId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteWorkType(@PathVariable("workTypeId") Long workTypeId, @PathVariable("roleId") Long roleId) {
		workTypeService.delete(roleId, workTypeId);
	}

}
