package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Expenses;
import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.MoneySpendExpense;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.repository.ExpensesRepository;
import com.vesmer.web.timontey.repository.SpendMoneyRepository;
import com.vesmer.web.timontey.service.QuotaMoneyService;
import com.vesmer.web.timontey.service.SpendMoneyService;

@Service
@Transactional
public class SpendMoneyServiceImp implements SpendMoneyService {
	@Autowired
	private SpendMoneyRepository spendMoneyRepository;

	@Autowired
	private QuotaMoneyService quotaMoneyService;

	@Autowired
	private ExpensesRepository expensesRepository;

	@Override
	public List<MoneySpend> getMoneySpendListForEmployee(long employeeId) {
		List<MoneySpend> moneySpendList = 
				spendMoneyRepository.getMoneySpendListForEmployee(employeeId);
		for(MoneySpend moneySpend: moneySpendList) {
			patch(moneySpend);
		}
		return moneySpendList;
	}
	
	private void patch(MoneySpend moneySpend) {
		QuotaMoney quotaMoney = 
				quotaMoneyService.getQuotaMoneyById(moneySpend.getQuotaMoney().getId()).get();
		moneySpend.setQuotaMoney(quotaMoney);
		
		List<MoneySpendExpense> moneyExpensesList = moneySpend.getMoneyExpenseList();
		for(int i = 0; i < moneyExpensesList.size(); ++i) {
			MoneySpendExpense moneyExpense = moneyExpensesList.get(i);
			patchSpendExpense(moneyExpense);
		}
	}
	
	private void patchSpendExpense(MoneySpendExpense moneyExpense) {
		MoneySpendExpense fullMoneyExpense = 
					spendMoneyRepository.findMoneyExpenseById(moneyExpense.getId()).get();
		Expenses expenses = 
				expensesRepository.findById(moneyExpense.getExpenses().getId()).get();
		moneyExpense.setExpenses(expenses);
		moneyExpense.setDate(fullMoneyExpense.getDate());
	}

}
