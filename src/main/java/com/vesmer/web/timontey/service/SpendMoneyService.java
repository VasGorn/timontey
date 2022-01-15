package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.MoneySpend;

public interface SpendMoneyService {

	List<MoneySpend> getMoneySpendListForEmployee(long employeeId);

}
