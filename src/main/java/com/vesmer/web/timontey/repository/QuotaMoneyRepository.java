package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.QuotaMoney;

public interface QuotaMoneyRepository {

	List<QuotaMoney> getQuotaMoneysForManager(long managerId);

	long save(QuotaMoney quotaMoney);

	int update(QuotaMoney quotaMoney);

	void delete(long quotaMoneyId);

	Optional<QuotaMoney> findQuotaMoneyById(long id);

	List<QuotaMoney> getQuotaMoneysForEmployee(long employeeId);

	List<QuotaMoney> getQuotaMoneysForOrder(long orderId);

}
