package com.vesmer.web.timontey.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vesmer.web.timontey.domain.Expenses;
import com.vesmer.web.timontey.domain.MoneySpendExpense;


public class MoneySpendExpenseRowMapper implements RowMapper<MoneySpendExpense> {

	@Override
	public MoneySpendExpense mapRow(ResultSet rs, int rowNum) throws SQLException {
		MoneySpendExpense moneyExpense = new MoneySpendExpense();
		moneyExpense.setId(rs.getLong("id"));
		
		Expenses expenses = new Expenses();
		expenses.setId(rs.getLong("expenses_id"));
		moneyExpense.setExpenses(expenses);
		
		moneyExpense.setDate(rs.getDate("date_spend"));
		moneyExpense.setMoney(rs.getFloat("money"));
		moneyExpense.setApprove(rs.getBoolean("approve"));

		return moneyExpense;
	}

}
