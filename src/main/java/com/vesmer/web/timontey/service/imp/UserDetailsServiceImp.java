package com.vesmer.web.timontey.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.SecurityUser;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.service.UserService;

public class UserDetailsServiceImp implements UserDetailsService{
	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optUser = userService.getUserByUsername(username);
		if(!optUser.isPresent()) {
			String msg = String.format("Username '%s' not found", username);
			System.err.println(msg);
			throw new UsernameNotFoundException(msg);
		}
		
		User user = optUser.get();
		return SecurityUser.fromUser(user);
	}
}
