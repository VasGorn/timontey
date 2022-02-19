package com.vesmer.web.timontey.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.vesmer.web.timontey.domain.enumeration.UserStatus;

public class User extends Employee{
	private String username;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private List<Role> roles;
	
	private UserStatus status;
	
	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
		
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public void setEmployee(Employee employee) {
		this.setLastName(employee.getLastName());
		this.setFirstName(employee.getFirstName());
		this.setMiddleName(employee.getMiddleName());
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password 
				+ ", roles=" + roles + ", status=" + status + "]";
	}
}
