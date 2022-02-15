package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.QuotaTime;
import com.vesmer.web.timontey.domain.WorkTypeHours;

public interface QuotaTimeRepository {

	List<QuotaTime> getQuotaTimeListForOrder(long orderId, short numMonth, 
			short year);

	void save(QuotaTime quotaTime);

	int update(QuotaTime quotaTime);

	void delete(long workHoursId);

	List<QuotaTime> getQuotaTimeListForEmployee(long employeeId, short numMonth, short year);

	Optional<WorkTypeHours> findWorkTypeQuotaById(long id);

	List<QuotaTime> getQuotaTimeListForManager(long managerId, short numMonth, short year);

}
