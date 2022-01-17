package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.MoneySpend;

public interface SpendMoneyRepository {

	List<MoneySpend> getMoneySpendListForEmployee(long employeeId);

}
