package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.NavigationLink;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.repository.NavigationLinkRepository;
import com.vesmer.web.timontey.service.NavigationLinkService;

@Service
@Transactional
public class NavigationLinkServiceImp implements NavigationLinkService {
	@Autowired
	private NavigationLinkRepository navigationLinkRepository;

	@Override
	public List<NavigationLink> getNavigationByRole(Role role) {
		return navigationLinkRepository.getNavigationByRole(role);
	}

}
