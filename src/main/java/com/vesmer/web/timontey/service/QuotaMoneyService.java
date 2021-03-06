package com.vesmer.web.timontey.service;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.QuotaMoney;

public interface QuotaMoneyService {

	List<QuotaMoney> getQuotaMoneysForManager(long managerId);

	QuotaMoney save(QuotaMoney quotaMoney);

	QuotaMoney update(QuotaMoney quotaMoney);

	void delete(long quotaMoneyId);

	Optional<QuotaMoney> getQuotaMoneyById(long id);
	
	List<QuotaMoney> getQuotaMoneysForOrder(long orderId);

}
