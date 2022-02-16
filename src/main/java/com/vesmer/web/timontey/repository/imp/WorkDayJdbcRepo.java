package com.vesmer.web.timontey.repository.imp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.HoursSpend;
import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.domain.WorkDay;
import com.vesmer.web.timontey.domain.WorkTypeHours;
import com.vesmer.web.timontey.repository.QuotaTimeRepository;
import com.vesmer.web.timontey.repository.WorkDayRepository;
import com.vesmer.web.timontey.rowmapper.WorkDayRowMapper;

@Repository
public class WorkDayJdbcRepo implements WorkDayRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private QuotaTimeRepository quotaTimeRepository;
	
	private static final String SELECT_WORK_DAY_SQL =
		"SELECT * FROM work_day WHERE worktype_quota_id = ?;";
	private static final String INSERT_WORK_DAY_SQL = 
		"INSERT INTO work_day (worktype_quota_id, employee_id, num_day, "
		+ "hours_spend, overtime) VALUES (?, ?, ?, ?, ?);";
	private static final String UPDATE_WORK_DAY_SQL = 
			"UPDATE work_day SET worktype_quota_id = ?, employee_id = ?, "
			+ "hours_spend = ?, overtime = ?, approve = FALSE "
			+ "WHERE id = ?;";
	private static final String APPROVE_WORK_DAY_SQL =
			"UPDATE work_day SET approve = TRUE WHERE id = ?;";
	private static final String DELETE_WOKR_DAY_SQL = 
			"DELETE FROM work_day WHERE id = ?;";

	@Override
	public List<HoursSpend> getWorkTypeTimeSpend(long masterId, short numMonth, short year) {
		List<QuotaTime> listQuota = 
				quotaTimeRepository.getQuotaTimeListForEmployee(masterId, numMonth, year);
		List<HoursSpend> workTypeTimeSpendList = new LinkedList<HoursSpend>();
		for(int i = 0; i < listQuota.size(); ++i) {
			List<WorkTypeHours> workTypeTimeQuotaList = listQuota.get(i).getWorkTypeHours();
			for(int j = 0; j < workTypeTimeQuotaList.size(); ++j) {
				HoursSpend workTypeSpend = new HoursSpend();
				workTypeSpend.setWorkTypeHours(workTypeTimeQuotaList.get(j));
				workTypeTimeSpendList.add(workTypeSpend);
			}
		}
		
		for(int i = 0; i < workTypeTimeSpendList.size(); ++i) {
			List<WorkDay> workDayList = jdbcTemplate.query(SELECT_WORK_DAY_SQL, 
					new WorkDayRowMapper(), 
					workTypeTimeSpendList.get(i).getWorkTypeHours().getId());
			workTypeTimeSpendList.get(i).setWorkDayList(workDayList);
		}
		
		return workTypeTimeSpendList;
	}
	
	@Override
	public void save(HoursSpend hoursSpend) {
		long workTypeQuotaId = hoursSpend.getWorkTypeHours().getId();
		
		WorkDay workDay = hoursSpend.getWorkDayList().get(0);
		long workDayId = saveWorkDay(workDay, workTypeQuotaId);
		workDay.setId(workDayId);
	}
		
	@Override
	public int update(HoursSpend hoursSpend) {
		long workTypeQuotaId = hoursSpend.getWorkTypeHours().getId();
		WorkDay workDay = hoursSpend.getWorkDayList().get(0);

		return jdbcTemplate.update(UPDATE_WORK_DAY_SQL, workTypeQuotaId,
				workDay.getEmployee().getId(), workDay.getWorkHours(),
				workDay.getOvertime(), workDay.getId());
	}
		
	@Override
	public void delete(long workDayId) {
		jdbcTemplate.update(DELETE_WOKR_DAY_SQL, workDayId);
	}
		
	@Override
	public void approve(long workDayId) {
		jdbcTemplate.update(APPROVE_WORK_DAY_SQL, workDayId);
	}
	
	private long saveWorkDay(WorkDay workDay, long workTypeQuotaId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_WORK_DAY_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setLong(1, workTypeQuotaId);
	          ps.setLong(2, workDay.getEmployee().getId());
	          ps.setShort(3, workDay.getNumDay());
	          ps.setShort(4, workDay.getWorkHours());
	          ps.setShort(5, workDay.getOvertime());
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
