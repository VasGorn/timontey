package com.vesmer.web.timontey.repository.imp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	private static final String SELECT_QUOTA_TIME_ID_SQL =
			"SELECT id FROM quota_time WHERE order_id=?"
			+ " AND employee_id=?"
			+ " AND year=?;";
	private static final String INSERT_QUOTA_TIME_SQL =
			"INSERT INTO quota_time (order_id, employee_id, year)"
			+ " VALUES (?, ?, ?);";

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
	
	private long getQuotaTimeId(QuotaTime quotaTime) {
		Long id = 0L;
		try {
			id = jdbcTemplate.queryForObject(SELECT_QUOTA_TIME_ID_SQL,
					Long.class,	quotaTime.getOrder().getId(),
									quotaTime.getEmployee().getId(),
									quotaTime.getYear()
					);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Not found quota time!!");
		}
		return id;
	}
	
	private long saveQuotaTime(QuotaTime quotaTime) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_QUOTA_TIME_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setLong(1, quotaTime.getOrder().getId());
	          ps.setLong(2, quotaTime.getEmployee().getId());
	          ps.setShort(3, quotaTime.getYear());
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
