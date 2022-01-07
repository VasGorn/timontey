package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.QuotaTime;

public interface QuotaTimeRepository {

	List<QuotaTime> getQuotaTimeListForOrder(long orderId, short numMonth, 
			short year);

	void save(QuotaTime quotaTime);

	int update(QuotaTime quotaTime);

	void delete(long workHoursId);

}
