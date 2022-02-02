package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.HoursSpend;

public interface WorkDayRepository {

	List<HoursSpend> getWorkTypeTimeSpend(long masterId, short numMonth, short year);

	void save(HoursSpend hoursSpend);

}
