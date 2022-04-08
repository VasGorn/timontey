package com.vesmer.web.timontey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vesmer.web.timontey.service.ReportMoneyService;
import com.vesmer.web.timontey.service.StaffService;

@Controller
@RequestMapping("/report-money")
public class ReportMoneyController {
	@Autowired
	private ReportMoneyService reportMoneyService;
	
	@Autowired
	private StaffService staffService;
}
