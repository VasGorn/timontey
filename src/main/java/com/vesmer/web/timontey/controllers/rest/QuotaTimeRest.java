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

import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.service.QuotaTimeService;

@RestController
@RequestMapping(path = "/rest/quota-time", produces = "application/json")
public class QuotaTimeRest {
	@Autowired
	private QuotaTimeService quotaTimeService;
	
	@GetMapping("/order/{orderId}/month/{numMonth}/year/{year}")
	public List<QuotaTime> getQuotaTimeListForOrder(
			@PathVariable("orderId") long orderId,
			@PathVariable("numMonth") short numMonth, 
			@PathVariable("year") short year){
		return quotaTimeService.getQuotaTimeListForOrder(orderId, numMonth, year);
	}
		
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public QuotaTime postQuotaTime(@RequestBody QuotaTime quotaTime) {
		return quotaTimeService.save(quotaTime);
	}
	
	@PutMapping("/{quotaTimeId}")
	public QuotaTime putQuotaTime(@PathVariable("quotaTimeId") long quotaTimeId,
			@RequestBody QuotaTime quotaTime) {
		return quotaTimeService.update(quotaTime);
	}
	
	@DeleteMapping("/workHours/{workHoursId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteWorkHours(@PathVariable("workHoursId") long workHoursId) {
		quotaTimeService.delete(workHoursId);
	}

}
