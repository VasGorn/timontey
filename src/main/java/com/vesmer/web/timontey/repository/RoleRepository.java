package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.domain.User;

public interface RoleRepository {

	List<Role> getAll();

	List<Role> getRolesForUser(User user);

}
