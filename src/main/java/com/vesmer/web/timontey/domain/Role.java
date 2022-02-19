package com.vesmer.web.timontey.domain;

import java.util.List;
import java.util.Objects;

public class Role {
	private long id;
	private String roleName;
	private List<Authority> authorities;
	
	public Role() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", authorities=" + authorities + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorities, id, roleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(authorities, other.authorities) && id == other.id
				&& Objects.equals(roleName, other.roleName);
	}
}
