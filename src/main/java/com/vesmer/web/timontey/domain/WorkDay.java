package com.vesmer.web.timontey.domain;

public class WorkDay {
	private long id;
	private Employee employee;
	private short numDay;
	private short workHours;
	private short overtime;
	private boolean isApprove;
	
	public WorkDay() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public short getNumDay() {
		return numDay;
	}
	public void setNumDay(short numDay) {
		this.numDay = numDay;
	}
	public short getWorkHours() {
		return workHours;
	}
	public void setWorkHours(short workHours) {
		this.workHours = workHours;
	}
	public short getOvertime() {
		return overtime;
	}
	public void setOvertime(short overtime) {
		this.overtime = overtime;
	}
	public boolean isApprove() {
		return isApprove;
	}
	public void setApprove(boolean isApprove) {
		this.isApprove = isApprove;
	}

	@Override
	public String toString() {
		return "WorkDay [id=" + id + ", employee=" + employee + ", numDay=" + numDay + ", workHours=" + workHours
				+ ", overtime=" + overtime + ", isApprove=" + isApprove + "]";
	}
}
