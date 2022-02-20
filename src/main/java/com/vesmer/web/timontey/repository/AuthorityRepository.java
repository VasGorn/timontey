package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.Authority;
import com.vesmer.web.timontey.domain.Role;

public interface AuthorityRepository {
	
	List<Authority> getAuthoritiesForRole(Role role);

}
