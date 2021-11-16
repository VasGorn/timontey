package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.repository.QuotaMoneyRepository;
import com.vesmer.web.timontey.rowmapper.QuotaMoneyRowMapper;

@Repository
public class QuotaMoneyJdbcRepo implements QuotaMoneyRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECT_QUOTA_MONEY_BY_MANAGER_ID_SQL = 
		"SELECT q.id, q.order_id, q.employee_id, "
		+ "q.money_limit FROM quota_money AS q, "
		+ "orders AS o "
		+ "WHERE o.id=q.order_id AND "
		+ "o.manager_id=?;";

	@Override
	public List<QuotaMoney> getQuotaMoneysForManager(long managerId) {
		return jdbcTemplate.query(SELECT_QUOTA_MONEY_BY_MANAGER_ID_SQL, 
				new QuotaMoneyRowMapper(), managerId);
	}

}
