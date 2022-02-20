package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.NavigationLink;
import com.vesmer.web.timontey.domain.Role;

public interface NavigationLinkRepository {

	List<NavigationLink> getNavigationByRole(Role role);

}
