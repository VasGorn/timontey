package com.vesmer.web.timontey.domain;

public class WorkTypeHours {
	private long id;
	private WorkType workType;
	private short numMonth;
	private int hours;

	public WorkTypeHours() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public WorkType getWorkType() {
		return workType;
	}
	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	public short getNumMonth() {
		return numMonth;
	}
	public void setNumMonth(short numMonth) {
		this.numMonth = numMonth;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	@Override
	public String toString() {
		return "WorkTypeHours [id=" + id + ", workType=" + workType + ", numMonth=" + numMonth + ", hours=" + hours
				+ "]";
	}

}
