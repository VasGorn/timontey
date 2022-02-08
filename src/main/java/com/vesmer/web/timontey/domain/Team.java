package com.vesmer.web.timontey.domain;

import java.util.List;

public class Team {
	private Employee performer;
	private List<Employee> employeeList;
	
	public Employee getPerformer() {
		return performer;
	}
	public void setPerformer(Employee performer) {
		this.performer = performer;
	}
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	@Override
	public String toString() {
		return "Team [performer=" + performer + ", team=" + employeeList + "]";
	}
}
