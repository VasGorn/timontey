package com.vesmer.web.timontey.domain;

import java.util.Objects;

public class NavigationLink {
	private long id;
	private String name;
	private String url;
	private String icon;
	
	public NavigationLink() {
	}
	
	public NavigationLink(String name, String url, String icon) {
		this.name = name;
		this.url = url;
		this.icon = icon;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Override
	public String toString() {
		return "NavigationLink [id=" + id + ", name=" + name + ", url=" + url + ", icon=" + icon + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(icon, id, name, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NavigationLink other = (NavigationLink) obj;
		return Objects.equals(icon, other.icon) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(url, other.url);
	}
}
