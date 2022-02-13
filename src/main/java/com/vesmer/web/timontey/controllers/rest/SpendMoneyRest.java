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

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.MoneySpendExpense;
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
		
	@GetMapping("/manager/{managerId}")
	public List<MoneySpend> getMoneySpendListForManager(
			@PathVariable("managerId") long managerId){
		return spendMoneyService.getMoneySpendListForManager(managerId);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public MoneySpend postMoneySpend(@RequestBody MoneySpend moneySpend) {
		return spendMoneyService.save(moneySpend);
	}
		
	@PutMapping(path = "/{moneySpendExpenseId}", consumes = "application/json")
	public MoneySpendExpense putMoneySpendExpense(
			@RequestBody MoneySpendExpense mSpendExpense) {
		return spendMoneyService.update(mSpendExpense);
	}
		
	@PutMapping(path = "/approve/{moneySpendExpenseId}")
	public void approveMoneySpendExpense(
			@PathVariable("moneySpendExpenseId") long spendId) {
		spendMoneyService.approve(spendId);
	}
		
	@DeleteMapping("/{moneySpendExpenseId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMoneySpendExpense(
			@PathVariable("moneySpendExpenseId") long spendExpeseId) {
		spendMoneyService.delete(spendExpeseId);
	}
}
