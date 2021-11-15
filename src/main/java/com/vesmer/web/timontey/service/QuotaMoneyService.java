package com.vesmer.web.timontey.service;

import java.util.List;

import com.vesmer.web.timontey.domain.QuotaMoney;

public interface QuotaMoneyService {

	List<QuotaMoney> getQuotaMoneysForManager(long managerId);

}
