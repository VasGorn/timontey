package com.vesmer.web.timontey.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoneySpend {
	private QuotaMoney quotaMoney;
	private List<MoneySpendExpense> moneyExpenseList;
	
	public QuotaMoney getQuotaMoney() {
		return quotaMoney;
	}
	public void setQuotaMoney(QuotaMoney quotaMoney) {
		this.quotaMoney = quotaMoney;
	}
	public List<MoneySpendExpense> getMoneyExpenseList() {
		return moneyExpenseList;
	}
	public void setMoneyExpenseList(List<MoneySpendExpense> moneyExpenseList) {
		this.moneyExpenseList = moneyExpenseList;
	}
	
	public float getSumMoney(Expenses expenses, short numDay) {
		float sum = 0.0f;
		for(MoneySpendExpense mExpense: moneyExpenseList) {
			Date dateExpense = mExpense.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateExpense);
			short numDayInList = (short) calendar.get(Calendar.DAY_OF_MONTH);
			
			if(mExpense.getExpenses().equals(expenses) && numDayInList == numDay) {
				sum += mExpense.getMoney();
			}
		}
		sum = Math.round(sum * 100.0f) / 100.0f;
		return sum;
	}
	
	public boolean isSumApprove(Expenses expenses, short numDay) {
		boolean isSumApprove = true;
		for(MoneySpendExpense mExpense: moneyExpenseList) {
			Date dateExpense = mExpense.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateExpense);
			short numDayInList = (short) calendar.get(Calendar.DAY_OF_MONTH);
			
			if(mExpense.getExpenses().equals(expenses) && numDayInList == numDay) {
				isSumApprove = isSumApprove && mExpense.isApprove();
			}
		}
		return isSumApprove;
	}
	
	public Set<Expenses> getUniqueExpense(){
		Set<Expenses> uniqueExpenses = new HashSet<Expenses>();
		for(MoneySpendExpense mExpense: moneyExpenseList) {
			uniqueExpenses.add(mExpense.getExpenses());
		}
		return uniqueExpenses;
	}
	
	public Set<Short> getUniqueDays(){
		Set<Short> uniqueDays = new HashSet<Short>();
		for(MoneySpendExpense mExpense: moneyExpenseList) {
			Date dateExpense = mExpense.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateExpense);
			short numDay = (short) calendar.get(Calendar.DAY_OF_MONTH);
			uniqueDays.add(numDay);
		}
		return uniqueDays;
	}
	
	@Override
	public String toString() {
		return "MoneySpend [quotaMoney=" + quotaMoney + ", moneyExpenseList=" + moneyExpenseList + "]";
	}
}
