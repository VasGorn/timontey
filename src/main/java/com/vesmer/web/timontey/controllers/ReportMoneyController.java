package com.vesmer.web.timontey.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.ReportMoney;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.ReportMoneyService;
import com.vesmer.web.timontey.service.StaffService;
import com.vesmer.web.timontey.service.UserService;

@Controller
@RequestMapping("/report-money")
public class ReportMoneyController {
	private final StaffService staffService;
	private final UserService userService;
	private final ReportMoneyService reportMoneyService;
	
	private static final int YEARS_RANGE = 2;
	
	@Autowired
	public ReportMoneyController(StaffService staffService, UserService userService,
			ReportMoneyService reportMoneyService) {
		this.staffService = staffService;
		this.userService = userService;
		this.reportMoneyService = reportMoneyService;
	}

	@RequestMapping("")
	public ModelAndView getReportMoneyView(Principal principal) {
		User user = userService.getUserByUsername(principal.getName()).get();
		long managerId = user.getId();

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

	@RequestMapping(value = "/excelReportMoney", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam long orderId,
			@RequestParam short year,
			@RequestParam short numMonth,
			@RequestParam String filename){
		ReportMoney reportMoney = reportMoneyService.getReportMoney(orderId,
				year, numMonth);
		reportMoney.setName(filename);
		return new ModelAndView("ExcelMoneyView", "reportMoney", reportMoney);
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
