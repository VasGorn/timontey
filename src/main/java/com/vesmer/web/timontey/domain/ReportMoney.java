package com.vesmer.web.timontey.domain;

import java.util.List;

public class ReportMoney {
	private String name;
	private short year;
	private short numMonth;
	private List<MoneySpend> moneySpendList;

	public ReportMoney() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MoneySpend> getMoneySpendList() {
		return moneySpendList;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public short getNumMonth() {
		return numMonth;
	}

	public void setNumMonth(short numMonth) {
		this.numMonth = numMonth;
	}

	public void setMoneySpendList(List<MoneySpend> moneySpendList) {
		this.moneySpendList = moneySpendList;
	}
}
