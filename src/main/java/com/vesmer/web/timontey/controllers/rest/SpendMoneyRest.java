package com.vesmer.web.timontey.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.service.SpendMoneyService;

@RestController
@RequestMapping(path="/rest/spend-money", produces="application/json")
public class SpendMoneyRest {
	@Autowired
	private SpendMoneyService spendMoneyService;
	
	@GetMapping("/employee/{employeeId}")
	public List<MoneySpend> getMoneySpendList(
			@PathVariable("employeeId") long employeeId){
		return spendMoneyService.getMoneySpendListForEmployee(employeeId);
	}

}
