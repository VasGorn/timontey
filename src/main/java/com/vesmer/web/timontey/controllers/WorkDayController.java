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
public class WorkDayController {
	private final UserService userService;

	private final short DAYS = 4;
	private final short WORK_HOURS = 8;
	private final short OVERTIME = 12;
	
	@Autowired
	public WorkDayController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/work-day")
	public ModelAndView getWorkDayPage(Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).get();
		long employeeId = user.getId();
		
		Calendar cal = Calendar.getInstance();
		short year = (short) cal.get(Calendar.YEAR);
		short numMonth =(short) (cal.get(Calendar.MONTH) + 1);

		short dayOfMonth = (short) cal.get(Calendar.DAY_OF_MONTH);
		List<String> daysOfMonth = getDaysOfMonth(dayOfMonth);
		List<String> hoursList = getStringList(0, WORK_HOURS);
		List<String> overtimeList = getStringList(0, OVERTIME);
		
		ModelAndView model = new ModelAndView("work-day");
		model.addObject("employeeId", employeeId);
		model.addObject("year", year);
		model.addObject("numMonth", numMonth);
		
		model.addObject("daysOfMonth", daysOfMonth);
		model.addObject("hoursList", hoursList);
		model.addObject("overtimeList", overtimeList);
		
		return model;
	}

	private List<String> getDaysOfMonth(short dayOfMonth) {
		short minDay = (short) (dayOfMonth - DAYS);
		if(minDay < 1) {
			minDay = 1;
		}
		List<String> daysList = getStringList(minDay, dayOfMonth);
		return daysList;
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
