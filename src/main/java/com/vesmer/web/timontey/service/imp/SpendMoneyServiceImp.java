package com.vesmer.web.timontey.service.imp;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	private final SpendMoneyRepository spendMoneyRepository;
	private final QuotaMoneyService quotaMoneyService;
	private final ExpensesRepository expensesRepository;

	@Autowired
	public SpendMoneyServiceImp(SpendMoneyRepository spendMoneyRepository, QuotaMoneyService quotaMoneyService,
			ExpensesRepository expensesRepository) {
		this.spendMoneyRepository = spendMoneyRepository;
		this.quotaMoneyService = quotaMoneyService;
		this.expensesRepository = expensesRepository;
	}

	@Override
	public List<MoneySpend> getMoneySpendListForEmployee(long employeeId) {
		List<MoneySpend> moneySpendList = 
				spendMoneyRepository.getMoneySpendListForEmployee(employeeId);
		for(MoneySpend moneySpend: moneySpendList) {
			patch(moneySpend);
		}
		return moneySpendList;
	}
		
	@Override
	public List<MoneySpend> getMoneySpendListForManager(long managerId) {
		List<MoneySpend> moneySpendList = 
				spendMoneyRepository.getMoneySpendListForManager(managerId);
		for(MoneySpend moneySpend: moneySpendList) {
			patch(moneySpend);
		}
		return moneySpendList;
	}
		
	@Override
	public List<MoneySpend> getMoneySpendListForOrder(long orderId) {
		List<QuotaMoney> qMoneyList = 
				quotaMoneyService.getQuotaMoneysForOrder(orderId);
		
		List<MoneySpend> moneySpendList = new LinkedList<MoneySpend>();
		for(QuotaMoney qMoney: qMoneyList) {
			List<MoneySpendExpense> moneyExpenseList
				= spendMoneyRepository.getMoneyExpenseListByQuota(qMoney.getId());
			MoneySpend mSpend = new MoneySpend();
			mSpend.setQuotaMoney(qMoney);
			mSpend.setMoneyExpenseList(moneyExpenseList);
			moneySpendList.add(mSpend);
		}
		for(MoneySpend moneySpend: moneySpendList) {
			patch(moneySpend);
		}
		return moneySpendList;
	}
	
	@Override
	public MoneySpend save(MoneySpend moneySpend) {
		spendMoneyRepository.save(moneySpend);
		patch(moneySpend);
		return moneySpend;
	}
	
	@Override
	public MoneySpendExpense update(MoneySpendExpense mSpendExpense) {
		spendMoneyRepository.update(mSpendExpense);
		patchSpendExpense(mSpendExpense);
		return mSpendExpense;
	}

	@Override
	public void delete(long spendExpeseId) {
		try {
			spendMoneyRepository.delete(spendExpeseId);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Data not found: " + e.getMessage());
		}
	}
		
	@Override
	public void approve(long spendId) {
		spendMoneyRepository.approve(spendId);
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
