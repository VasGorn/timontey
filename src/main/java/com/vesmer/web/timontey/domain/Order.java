package com.vesmer.web.timontey.domain;

public class Order {
	private long id;
	private String name;
	private Employee manager;
	private String description;
	private String address;
	
	public Order() {
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

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", manager=" + manager + ", description=" + description
				+ ", address=" + address + "]";
	}

}
