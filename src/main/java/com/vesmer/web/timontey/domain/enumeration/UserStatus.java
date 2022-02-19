package com.vesmer.web.timontey.domain.enumeration;

public enum UserStatus {
	ACTIVE("ACTIVE"), 
	BANNED("BANNED");
		
	private String strStatus;
		
	private UserStatus(String strStatus) {
		this.strStatus = strStatus; 
	}
	
	public String getString() {
		return strStatus;
	}
}
