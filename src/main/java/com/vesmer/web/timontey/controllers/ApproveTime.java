package com.vesmer.web.timontey.controllers;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApproveTime {
	private final short WORK_HOURS = 8;
	private final short OVERTIME = 12;
	
	@RequestMapping("/approve-time")
	public ModelAndView getApproveTimePage() {
		long managerId = 3;
		
		Calendar cal = Calendar.getInstance();
		short year = (short) cal.get(Calendar.YEAR);
		short numMonth =(short) (cal.get(Calendar.MONTH) + 1);

		List<String> hoursList = getStringList(0, WORK_HOURS);
		List<String> overtimeList = getStringList(0, OVERTIME);
		
		ModelAndView model = new ModelAndView("approve-time");
		model.addObject("managerId", managerId);
		model.addObject("year", year);
		model.addObject("numMonth", numMonth);
		
		model.addObject("hoursList", hoursList);
		model.addObject("overtimeList", overtimeList);
		
		return model;
	}
	

}
