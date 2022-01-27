package com.vesmer.web.timontey.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Const;
import com.vesmer.web.timontey.domain.NavigationLink;

@Controller
public class NavigationController {
	@RequestMapping("/")
	public ModelAndView main() {
		List<NavigationLink> list = new LinkedList<>();
		
		NavigationLink navLink1 = new NavigationLink("Users", 
				Const.BASE_URL+"users/", "home");
		NavigationLink navLink2 = new NavigationLink("Staff", 
				Const.BASE_URL+"staff/", "file");
		NavigationLink navLink4 = new NavigationLink("Work type", 
				Const.BASE_URL+"work-type/", "bar-chart-2");
		NavigationLink navLink5 = new NavigationLink("Expenses", 
				Const.BASE_URL+"expenses/", "layers");
		NavigationLink navLink6 = new NavigationLink("Orders", 
				Const.BASE_URL+"orders/", "layers");
		NavigationLink navLink7 = new NavigationLink("Quota of money", 
				Const.BASE_URL+"quota-money/", "layers");
		NavigationLink navLink8 = new NavigationLink("Quota of time", 
				Const.BASE_URL+"quota-time/", "layers");
		NavigationLink navLink9 = new NavigationLink("Spending money", 
				Const.BASE_URL+"spend-money/", "layers");
		NavigationLink navLink10 = new NavigationLink("Work day", 
				Const.BASE_URL+"work-day/", "layers");

		list.add(navLink1);
		list.add(navLink2);
		list.add(navLink4);
		list.add(navLink5);
		list.add(navLink6);
		list.add(navLink7);
		list.add(navLink8);
		list.add(navLink9);
		list.add(navLink10);

        ModelAndView model = new ModelAndView("main");
		model.addObject("navList", list);

        return model;
	}
}
