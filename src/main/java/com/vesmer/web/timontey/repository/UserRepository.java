package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.User;

public interface UserRepository {

	List<User> getAll();

	User save(User user);

	Optional<User> getUserByUsername(String username);

	int update(User user);

	int delete(String username);

}
