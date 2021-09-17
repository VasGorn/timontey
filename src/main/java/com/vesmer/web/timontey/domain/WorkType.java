package com.vesmer.web.timontey.domain;

public class WorkType {
	private long id;
	private String workTypeName;
	
	public WorkType() {
		
	}

	public WorkType(long id, String workTypeName) {
		super();
		this.id = id;
		this.workTypeName = workTypeName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	@Override
	public String toString() {
		return "WorkType [id=" + id + ", workTypeName=" + workTypeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkType other = (WorkType) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
