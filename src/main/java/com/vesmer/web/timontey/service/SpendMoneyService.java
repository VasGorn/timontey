package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.MoneySpendExpense;

public interface SpendMoneyService {

	List<MoneySpend> getMoneySpendListForEmployee(long employeeId);

	MoneySpend save(MoneySpend moneySpend);

	MoneySpendExpense update(MoneySpendExpense mSpendExpense);

	void delete(long spendExpeseId);

	List<MoneySpend> getMoneySpendListForManager(long managerId);

}
