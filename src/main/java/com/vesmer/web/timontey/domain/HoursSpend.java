package com.vesmer.web.timontey.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoursSpend {
	private WorkTypeHours workTypeHours;
	private List<WorkDay> workDayList;
	
	public HoursSpend() {
	}
	
	public WorkTypeHours getWorkTypeHours() {
		return workTypeHours;
	}
	public void setWorkTypeHours(WorkTypeHours workTypeHours) {
		this.workTypeHours = workTypeHours;
	}
	public List<WorkDay> getWorkDayList() {
		return workDayList;
	}
	public void setWorkDayList(List<WorkDay> workDayList) {
		this.workDayList = workDayList;
	}
	
	public int getSumWorkHours(short numDay) {
		int sum = 0;
		for(WorkDay wDay: workDayList) {
			if(numDay == wDay.getNumDay()) {
				sum += wDay.getWorkHours();
			}
		}
		return sum;
	}
	
	public int getSumOvertime(short numDay) {
		int sum = 0;
		for(WorkDay wDay: workDayList) {
			if(numDay == wDay.getNumDay()) {
				sum += wDay.getOvertime();
			}
		}
		return sum;
	}
	
	public boolean isSumApprove(short numDay) {
		boolean isSumApprove = true;
		for(WorkDay wDay: workDayList) {
			if(numDay == wDay.getNumDay() && !wDay.isApprove()) {
				isSumApprove = false;
			}
		}
		return isSumApprove;
	}
	
	public Set<Short> getUniqueDays(){
		Set<Short> uniqueDays = new HashSet<Short>();
		for(WorkDay wDay: workDayList) {
			uniqueDays.add(wDay.getNumDay());
		}
		return uniqueDays;
	}
	
	public Set<Employee> getUniqueEmployees(){
		Set<Employee> uniqueEmployees = new HashSet<Employee>();
		for(WorkDay wDay: workDayList) {
			uniqueEmployees.add(wDay.getEmployee());
		}
		return uniqueEmployees;
	}

	@Override
	public String toString() {
		return "HoursSpend [workTypeHours=" + workTypeHours + ", workDayList=" + workDayList + "]";
	}

}
