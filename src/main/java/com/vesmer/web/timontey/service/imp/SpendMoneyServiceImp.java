package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.repository.SpendMoneyRepository;
import com.vesmer.web.timontey.service.SpendMoneyService;

@Service
@Transactional
public class SpendMoneyServiceImp implements SpendMoneyService {
	@Autowired
	private SpendMoneyRepository spendMoneyRepository;

	@Override
	public List<MoneySpend> getMoneySpendListForEmployee(long employeeId) {
		List<MoneySpend> moneySpendList = 
				spendMoneyRepository.getMoneySpendListForEmployee(employeeId);
		for(MoneySpend moneySpend: moneySpendList) {
			patch(moneySpend);
		}
		return moneySpendList;
	}

}
