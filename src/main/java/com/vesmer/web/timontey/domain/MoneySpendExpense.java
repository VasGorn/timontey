package com.vesmer.web.timontey.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MoneySpendExpense {
private long id;
	private Expenses expenses;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")		
	private Date date;
	private float money;
	private boolean approve;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Expenses getExpenses() {
		return expenses;
	}
	public void setExpenses(Expenses expenses) {
		this.expenses = expenses;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public boolean isApprove() {
		return approve;
	}
	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(approve, date, expenses, id, money);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoneySpendExpense other = (MoneySpendExpense) obj;
		return approve == other.approve && Objects.equals(date, other.date) && Objects.equals(expenses, other.expenses)
				&& id == other.id && Float.floatToIntBits(money) == Float.floatToIntBits(other.money);
	}
	@Override
	public String toString() {
		return "MoneySpendExpense [id=" + id + ", expenses=" + expenses + ", date=" + date + ", money=" + money
				+ ", approve=" + approve + "]";
	}
}
