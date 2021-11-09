package com.vesmer.web.timontey.domain;

public class QuotaMoney {
private long id;
	private Order order;
	private Employee employee;
	private float moneyLimit;
	
	public QuotaMoney() {
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

	public float getMoneyLimit() {
		return moneyLimit;
	}

	public void setMoneyLimit(float moneyLimit) {
		this.moneyLimit = moneyLimit;
	}

	@Override
	public String toString() {
		return "QuotaMoney [id=" + id + ", order=" + order + ", employee=" + employee + ", moneyLimit=" + moneyLimit
				+ "]";
	}

}
