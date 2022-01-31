package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.HoursSpend;
import com.vesmer.web.timontey.repository.WorkDayRepository;
import com.vesmer.web.timontey.service.WorkDayService;


@Service
@Transactional
public class WorkDayServiceImp implements WorkDayService {
	@Autowired
	private WorkDayRepository workDayRepository;

	@Override
	public List<HoursSpend> getWorkTypeTimeSpend(long masterId, short numMonth, short year) {
		 List<HoursSpend> spendTimeList = workDayRepository.getWorkTypeTimeSpend(masterId, numMonth, year);
		 for(HoursSpend spendTime: spendTimeList) {
			 patchWorkTimeSpend(spendTime);
		 }
		 return spendTimeList;
	}
}
