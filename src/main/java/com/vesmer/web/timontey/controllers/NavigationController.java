package com.vesmer.web.timontey.controllers;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vesmer.web.timontey.domain.Const;
import com.vesmer.web.timontey.domain.NavigationLink;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.NavigationLinkService;
import com.vesmer.web.timontey.service.UserService;

@Controller
public class NavigationController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private NavigationLinkService navigationService;
	
	@RequestMapping("/")
	public ModelAndView main(Principal principal) {
		String username = principal.getName();
		System.out.println(String.format("Login user - '%s'", principal.getName()));
		User user = userService.getUserByUsername(username).get();
		
		Set<NavigationLink> navSet = new HashSet<NavigationLink>();
		for(Role role: user.getRoles()) {
			List<NavigationLink> navList = navigationService.getNavigationByRole(role);
			for(NavigationLink navLink: navList) {
				navSet.add(navLink);
			}
		}
		
		List<NavigationLink> navSortList = navSet.stream()
				.sorted(Comparator.comparing(NavigationLink::getName))
				.collect(Collectors.toList());

		System.out.println("Navigation set: " + navSortList);

        ModelAndView model = new ModelAndView("main");
		model.addObject("navList", navSortList);

        return model;
	}
}
