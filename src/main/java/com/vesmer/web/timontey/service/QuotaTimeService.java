package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.QuotaTime;

public interface QuotaTimeService {

	List<QuotaTime> getQuotaTimeListForOrder(long orderId, short numMonth, 
			short year);

	QuotaTime save(QuotaTime quotaTime);

	QuotaTime update(QuotaTime quotaTime);

	void delete(long workHoursId);

	List<QuotaTime> getQuotaTimeListForEmployee(long employeeId, short numMonth, short year);

	List<QuotaTime> getQuotaTimeListForManager(long managerId, short numMonth, 
			short year);

}
