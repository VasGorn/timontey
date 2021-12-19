package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.repository.QuotaTimeRepository;

@Repository
public class QuotaTimeJdbcRepo implements QuotaTimeRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECT_QUOTA_TIME_LIST_FOR_ORDER_SQL =
		"SELECT * FROM quota_time WHERE order_id=? AND year=?;";

	@Override
	public List<QuotaTime> getQuotaTimeListForOrder(long orderId, 
			short numMonth, short year) {
		return getQuotaTimeList(SELECT_QUOTA_TIME_LIST_FOR_ORDER_SQL,
				orderId, numMonth, year);
	}

}
