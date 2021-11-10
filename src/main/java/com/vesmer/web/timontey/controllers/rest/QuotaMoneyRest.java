package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.service.QuotaMoneyService;

@RestController
@RequestMapping(path = "/rest/quota-money", produces = "application/json")
public class QuotaMoneyRest {
	@Autowired
	private QuotaMoneyService quotaMoneyService;

}
