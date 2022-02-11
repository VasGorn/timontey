package com.vesmer.web.timontey.domain;

import java.util.List;

public class QuotaTime {
	private long id;
	private Order order;
	private Employee employee;
	private short year;
	private List<WorkTypeHours> workTypeHours;
	
	public QuotaTime() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public List<WorkTypeHours> getWorkTypeHours() {
		return workTypeHours;
	}
	public void setWorkTypeHours(List<WorkTypeHours> workTypeHours) {
		this.workTypeHours = workTypeHours;
	}

	@Override
	public String toString() {
		return "QuotaTime [id=" + id + ", order=" + order + ", employee=" + employee + ", year=" + year
				+ ", workTypeHours=" + workTypeHours + "]";
	}

}
