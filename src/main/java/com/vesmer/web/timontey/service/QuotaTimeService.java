package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.QuotaTime;

public interface QuotaTimeService {

	List<QuotaTime> getQuotaTimeListForOrder(long orderId, short numMonth, 
			short year);

	QuotaTime save(QuotaTime quotaTime);

	QuotaTime update(QuotaTime quotaTime);

}
