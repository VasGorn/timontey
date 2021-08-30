package com.vesmer.web.timontey.domain;

public class Expenses {
	private long id;
	private String name;
	
	public Expenses(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Expenses() {
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
	
	@Override
	public String toString() {
		return "Expenses [id=" + id + ", name=" + name + "]";
	}

}
