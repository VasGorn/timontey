package com.vesmer.web.timontey.repository;

import java.util.List;

import com.vesmer.web.timontey.domain.QuotaMoney;

public interface QuotaMoneyRepository {

	List<QuotaMoney> getQuotaMoneysForManager(long managerId);

}
