package com.vesmer.web.timontey.repository.imp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	private static final String SELECT_MONEY_EXPENSE_BY_ID_SQL =
		"SELECT * FROM spend_money WHERE id = ?;";
	private static final String INSERT_SPEND_MONEY_SQL = 
		"INSERT INTO spend_money (quota_money_id, expenses_id, money) "
		+ "VALUES (?, ?, ?);";
	private static final String UPDATE_MONEY_EXPENSE_SQL =
		"UPDATE spend_money SET expenses_id = ?, money = ?, approve = FALSE "
		+ "WHERE id = ?;";
	
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

	@Override
	public Optional<MoneySpendExpense> findMoneyExpenseById(long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_MONEY_EXPENSE_BY_ID_SQL, 
				new MoneySpendExpenseRowMapper(), id));
		} catch (EmptyResultDataAccessException ex){
			return Optional.empty();
		}
	}

	@Override
	public void save(MoneySpend moneySpend) {
		long quotaMoneyId = moneySpend.getQuotaMoney().getId();
		
		MoneySpendExpense moneyExpense = moneySpend.getMoneyExpenseList().get(0);
		long moneyExpenseId = saveMoneyExpense(quotaMoneyId, moneyExpense);
		moneyExpense.setId(moneyExpenseId);
	}
	
	@Override
	public int update(MoneySpendExpense mSpendExpense) {
		long mSpendExpenseId = mSpendExpense.getId();
		long expenseId = mSpendExpense.getExpenses().getId();
		float money = mSpendExpense.getMoney();
		return jdbcTemplate.update(UPDATE_MONEY_EXPENSE_SQL, expenseId,
				money, mSpendExpenseId);
	}
	
	private long saveMoneyExpense(long quotaMoneyId, MoneySpendExpense moneyExpense) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_SPEND_MONEY_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setLong(1, quotaMoneyId);
	          ps.setLong(2, moneyExpense.getExpenses().getId());
	          ps.setFloat(3, moneyExpense.getMoney());
	          return ps;
	        }, keyHolder);
	    
	    long newId;
	    if (keyHolder.getKeys().size() > 1) {
	        newId = ((Number) keyHolder.getKeys().get("id")).longValue();
	    } else {
	        newId= keyHolder.getKey().longValue();
	    }

        return newId;
	}
}
