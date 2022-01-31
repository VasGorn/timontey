package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.HoursSpend;

public interface WorkDayService {

	List<HoursSpend> getWorkTypeTimeSpend(long masterId, short numMonth, short year);

}
