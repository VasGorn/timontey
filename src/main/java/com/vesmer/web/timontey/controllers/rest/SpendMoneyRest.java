package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.service.SpendMoneyService;

@RestController
@RequestMapping(path="/rest/spend-money", produces="application/json")
public class SpendMoneyRest {
	@Autowired
	private SpendMoneyService spendMoneyService;

}
