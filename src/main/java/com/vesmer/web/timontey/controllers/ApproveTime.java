package com.vesmer.web.timontey.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.UserService;

@Controller
public class ApproveTime {
	private final UserService userService;

	private final short WORK_HOURS = 8;
	private final short OVERTIME = 12;
	
	@Autowired
	public ApproveTime(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/approve-time")
	public ModelAndView getApproveTimePage(Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).get();
		long managerId = user.getId();
		
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
	
	private List<String> getStringList(int minValue, int maxValue){
		List<String> stringList = new ArrayList<String>();
		int number = maxValue;
		while(number >= minValue) {
			String str = String.valueOf(number);
			stringList.add(str);
			number--;
		}
		return stringList;
	}
}
