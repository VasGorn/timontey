package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.NavigationLink;
import com.vesmer.web.timontey.domain.Role;

public interface NavigationLinkService {

	List<NavigationLink> getNavigationByRole(Role role);

}
