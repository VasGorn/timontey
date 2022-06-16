package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.HoursSpend;
import com.vesmer.web.timontey.domain.WorkDay;
import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.domain.WorkTypeHours;
import com.vesmer.web.timontey.repository.QuotaTimeRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.repository.WorkDayRepository;
import com.vesmer.web.timontey.repository.WorkTypeRepository;
import com.vesmer.web.timontey.service.WorkDayService;


@Service
@Transactional
public class WorkDayServiceImp implements WorkDayService {
	private final WorkDayRepository workDayRepository;
	private final QuotaTimeRepository quotaTimeRepository;
	private final WorkTypeRepository workTypeRepository;
	private final StaffRepository staffRepository;

	@Autowired
	public WorkDayServiceImp(WorkDayRepository workDayRepository, QuotaTimeRepository quotaTimeRepository,
			WorkTypeRepository workTypeRepository, StaffRepository staffRepository) {
		this.workDayRepository = workDayRepository;
		this.quotaTimeRepository = quotaTimeRepository;
		this.workTypeRepository = workTypeRepository;
		this.staffRepository = staffRepository;
	}

	@Override
	public List<HoursSpend> getWorkTypeTimeSpend(long masterId, short numMonth, short year) {
		 List<HoursSpend> spendTimeList = workDayRepository.getWorkTypeTimeSpend(masterId, numMonth, year);
		 for(HoursSpend spendTime: spendTimeList) {
			 patchWorkTimeSpend(spendTime);
		 }
		 return spendTimeList;
	}
	
	@Override
	public HoursSpend save(HoursSpend hoursSpend) {
		workDayRepository.save(hoursSpend);
		patchWorkTimeSpend(hoursSpend);
		return hoursSpend;
	}
		
	@Override
	public HoursSpend update(HoursSpend hoursSpend) {
		workDayRepository.update(hoursSpend);
		patchWorkTimeSpend(hoursSpend);
		return hoursSpend;
	}
		
	@Override
	public void delete(long workDayId) {
		try {
			workDayRepository.delete(workDayId);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Data not found: " + e.getMessage());
		}
	}
			
	@Override
	public void approve(long workDayId) {
		workDayRepository.approve(workDayId);
	}
	
	private void patchWorkTimeSpend(HoursSpend hoursSpend) {
		WorkTypeHours workHours = 
			quotaTimeRepository.findWorkTypeQuotaById(hoursSpend.getWorkTypeHours().getId()).get();
		WorkType workType = workTypeRepository.findById(workHours.getWorkType().getId()).get();
		workHours.setWorkType(workType);
		hoursSpend.setWorkTypeHours(workHours);
		
		List<WorkDay> workDayList = hoursSpend.getWorkDayList();
		for(int i = 0; i < workDayList.size(); ++i) {
			WorkDay workDay = hoursSpend.getWorkDayList().get(i);
			Employee employee = staffRepository.findById(workDay.getEmployee().getId()).get();
			workDay.setEmployee(employee);
		}
	}
}
