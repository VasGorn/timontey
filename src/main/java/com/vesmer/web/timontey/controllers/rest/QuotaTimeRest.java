package com.vesmer.web.timontey.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.service.QuotaTimeService;

@RestController
@RequestMapping(path = "/rest/quota-time", produces = "application/json")
public class QuotaTimeRest {
	@Autowired
	private QuotaTimeService quotaTimeService;

}
