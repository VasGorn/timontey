package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
