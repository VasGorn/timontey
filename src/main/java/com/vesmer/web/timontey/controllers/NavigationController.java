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

		list.add(navLink1);
		list.add(navLink2);
		list.add(navLink4);
		list.add(navLink5);

        ModelAndView model = new ModelAndView("main");
		model.addObject("navList", list);

        return model;
	}
}
