package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.MoneySpendExpense;

public interface SpendMoneyRepository {

	List<MoneySpend> getMoneySpendListForEmployee(long employeeId);
	
	List<MoneySpendExpense> getMoneyExpenseListByQuota(long quotaId);

	Optional<MoneySpendExpense> findMoneyExpenseById(long id);

	void save(MoneySpend moneySpend);

}
