package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.service.QuotaMoneyService;

@RestController
@RequestMapping(path = "/rest/quota-money", produces = "application/json")
public class QuotaMoneyRest {
	@Autowired
	private QuotaMoneyService quotaMoneyService;
	
	@GetMapping("/manager/{managerId}")
	public List<QuotaMoney> getQuotaMoneyForManager(
			@PathVariable("managerId") long managerId){
		return quotaMoneyService.getQuotaMoneysForManager(managerId);
	}

}
