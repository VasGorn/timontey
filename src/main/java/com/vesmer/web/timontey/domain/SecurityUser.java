package com.vesmer.web.timontey.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vesmer.web.timontey.domain.enumeration.UserStatus;

public class SecurityUser implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private final String username;
	private final String password;
	private final Set<SimpleGrantedAuthority> authorities;
	private final boolean isActive;
	
	public SecurityUser(String username, String password, 
			Set<SimpleGrantedAuthority> authorities, boolean isActive) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isActive = isActive;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
	
	public static UserDetails fromUser(User user) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for(Role role: user.getRoles()) {
			String roleAuthority = "ROLE_" + role.getRoleName();
			grantedAuthorities.add(new SimpleGrantedAuthority(roleAuthority));
			for(Authority authority: role.getAuthorities()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
			}
		}
	
		return new org.springframework.security.core.userdetails.User(
			user.getUsername(), user.getPassword(),
			user.getStatus().equals(UserStatus.ACTIVE),
			user.getStatus().equals(UserStatus.ACTIVE),
			user.getStatus().equals(UserStatus.ACTIVE),
			user.getStatus().equals(UserStatus.ACTIVE),
			grantedAuthorities
		);
	}
}
