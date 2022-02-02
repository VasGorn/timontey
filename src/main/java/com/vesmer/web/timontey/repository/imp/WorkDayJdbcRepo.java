package com.vesmer.web.timontey.repository.imp;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
}
