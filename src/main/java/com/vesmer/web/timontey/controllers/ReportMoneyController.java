package com.vesmer.web.timontey.controllers;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.service.ReportMoneyService;
import com.vesmer.web.timontey.service.StaffService;

@Controller
@RequestMapping("/report-money")
public class ReportMoneyController {
	private static final int YEARS_RANGE = 2;
	
	@Autowired
	private ReportMoneyService reportMoneyService;
	
	@Autowired
	private StaffService staffService;

	@RequestMapping("")
	public ModelAndView getReportMoneyView() {
		long managerId = 3;
		Employee manager = staffService.getEmployeeById(managerId).get();
		String fullName = manager.getLastName() + " " + manager.getFirstName() 
							+ " " + manager.getMiddleName();
		
		List<String> years = getYearsList();
		HashMap<Integer, String> months = getMonthsMap();
		
		ModelAndView model = new ModelAndView("report-money");
		model.addObject("managerId", manager.getId());
		model.addObject("managerName", fullName);
		model.addObject("years", years);
		model.addObject("months", months);
		return model;
	}
	
	private List<String> getYearsList() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<String> years = new ArrayList<String>(YEARS_RANGE);
		for(int i = 0; i < YEARS_RANGE; ++i) {
			years.add(Integer.toString(year - i));
		}
		return years;
	}
	
	private HashMap<Integer, String> getMonthsMap() {
		HashMap<Integer, String> months = new HashMap<Integer, String>(12);
		Locale locale = Locale.getDefault();
		for(int i = 0; i < 12; ++i) {
			months.put(new Integer(i), 
					Month.values()[i].getDisplayName(TextStyle.FULL, locale));
		}
		return months;
	}
}
