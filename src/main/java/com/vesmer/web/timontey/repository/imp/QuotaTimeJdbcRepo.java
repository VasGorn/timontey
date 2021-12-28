package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.domain.WorkTypeHours;
import com.vesmer.web.timontey.repository.QuotaTimeRepository;
import com.vesmer.web.timontey.rowmapper.QuotaTimeRowMapper;
import com.vesmer.web.timontey.rowmapper.WorkTypeHoursRowMapper;

@Repository
public class QuotaTimeJdbcRepo implements QuotaTimeRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECT_QUOTA_TIME_LIST_FOR_ORDER_SQL =
		"SELECT * FROM quota_time WHERE order_id=? AND year=?;";
	private static final String SELECT_WORKTYPE_HOURS_LIST_SQL =
			"SELECT * FROM worktype_hours WHERE quota_time_id=?"
			+ " AND num_month=?;";

	@Override
	public List<QuotaTime> getQuotaTimeListForOrder(long orderId, 
			short numMonth, short year) {
		return getQuotaTimeList(SELECT_QUOTA_TIME_LIST_FOR_ORDER_SQL,
				orderId, numMonth, year);
	}
	
	@Override
	public void save(QuotaTime quotaTime) {
		long quotaTimeId = getQuotaTimeId(quotaTime);
		
		if(quotaTimeId < 1) {
			quotaTimeId = saveQuotaTime(quotaTime);
		}
		quotaTime.setId(quotaTimeId);
		
		WorkTypeHours workHours = quotaTime.getWorkTypeHours().get(0);
		long workTypeHoursId = saveWorkTypeHours(workHours, quotaTimeId);
		workHours.setId(workTypeHoursId);
		
	}
	
	private List<QuotaTime> getQuotaTimeList(String sql, long paramerId, 
			short numMonth, short year){
		List<QuotaTime> listQuota = jdbcTemplate.query(sql, 
				new QuotaTimeRowMapper(), paramerId, year);
		
		for(int i = 0; i < listQuota.size(); ++i) {
			long quotaId =  listQuota.get(i).getId();
			listQuota.get(i).setWorkTypeHours(getWorktypeQuotaList(quotaId, numMonth));
		}
		
		return listQuota;
	}
	
	private List<WorkTypeHours> getWorktypeQuotaList(long quotaTimeId, short numMonth) {
		return jdbcTemplate.query(SELECT_WORKTYPE_HOURS_LIST_SQL, 
				new WorkTypeHoursRowMapper(), 
				quotaTimeId, numMonth);
	}
}
