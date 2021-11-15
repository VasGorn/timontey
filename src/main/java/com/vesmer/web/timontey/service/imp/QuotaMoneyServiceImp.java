package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.repository.QuotaMoneyRepository;
import com.vesmer.web.timontey.service.QuotaMoneyService;

public class QuotaMoneyServiceImp implements QuotaMoneyService{
	@Autowired
	private QuotaMoneyRepository quotaMoneyRepository;

	@Override
	public List<QuotaMoney> getQuotaMoneysForManager(long managerId) {
		List<QuotaMoney> quotaMoneyList = quotaMoneyRepository.getQuotaMoneysForManager(managerId);
		for(QuotaMoney qMoney: quotaMoneyList) {
			patch(qMoney);
		}
		return quotaMoneyList;
	}

}
