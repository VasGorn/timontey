package com.vesmer.web.timontey.repository.imp;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.MoneySpendExpense;
import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.repository.QuotaMoneyRepository;
import com.vesmer.web.timontey.repository.SpendMoneyRepository;
import com.vesmer.web.timontey.rowmapper.MoneySpendExpenseRowMapper;

@Repository
public class SpendMoneyJdbcRepo implements SpendMoneyRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private QuotaMoneyRepository quotaMoneyRepository;
	
	private static final String SELECT_MONEY_EXPENSE_BY_QUOTA_ID_SQL =
		"SELECT * FROM spend_money WHERE quota_money_id = ?;";
	
	@Override
	public List<MoneySpend> getMoneySpendListForEmployee(long employeeId) {
		List<MoneySpend> moneySpendList = new LinkedList<MoneySpend>();
		List<QuotaMoney> quotaMoneyList =
				quotaMoneyRepository.getQuotaMoneysForEmployee(employeeId);
		for(QuotaMoney quotaMoney: quotaMoneyList) {
			long quotaId = quotaMoney.getId();
			List<MoneySpendExpense> moneyExpenseList =
					getMoneyExpenseListByQuota(quotaId);

			MoneySpend moneySpend = new MoneySpend();
			moneySpend.setQuotaMoney(quotaMoney);
			moneySpend.setMoneyExpenseList(moneyExpenseList);
			moneySpendList.add(moneySpend);
		}
		return moneySpendList;
	}
	
	@Override
	public List<MoneySpendExpense> getMoneyExpenseListByQuota(long quotaId) {
		List<MoneySpendExpense> moneyExpenseList =
				jdbcTemplate.query(SELECT_MONEY_EXPENSE_BY_QUOTA_ID_SQL, 
						new MoneySpendExpenseRowMapper(), quotaId);
		return moneyExpenseList;
	}

}
