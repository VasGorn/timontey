package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.HoursSpend;
import com.vesmer.web.timontey.service.WorkDayService;

@RestController
@RequestMapping(path = "/rest/work-day", produces = "application/json")
public class WorkDayRest {
	@Autowired
	private WorkDayService workDayService;
	
	@GetMapping(path = "/employee/{masterId}/month/{numMonth}/year/{year}")
	public List<HoursSpend> getHoursSpendList(
			@PathVariable("masterId") long masterId,
			@PathVariable("numMonth") short numMonth,
			@PathVariable("year") short year){
		return workDayService.getWorkTypeTimeSpend(masterId, numMonth, year);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public HoursSpend postHoursSpend(@RequestBody HoursSpend hoursSpend) {
		return workDayService.save(hoursSpend);
	}
		
	@PutMapping(path = "/{workDayId}", consumes = "application/json")
	public HoursSpend putHoursSpend(@RequestBody HoursSpend hoursSpend) {
		return workDayService.update(hoursSpend);
	}
		
	@PutMapping(path = "/approve/{workDayId}")
	public void approveMoneySpendExpense(
			@PathVariable("workDayId") long workDayId) {
		workDayService.approve(workDayId);
	}
		
	@DeleteMapping("/{workDayId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteWorkDay(@PathVariable("workDayId") long workDayId) {
		workDayService.delete(workDayId);
	}
}
