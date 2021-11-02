package com.vesmer.web.timontey.service;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.User;

public interface UserService {

	List<User> getAll();

	User save(User user);
	
	Optional<User> getUserByUsername(String username);

	User update(User user);

}
