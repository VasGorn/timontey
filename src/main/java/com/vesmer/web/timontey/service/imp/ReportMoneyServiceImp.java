package com.vesmer.web.timontey.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.ReportMoney;
import com.vesmer.web.timontey.service.ReportMoneyService;
import com.vesmer.web.timontey.service.SpendMoneyService;


@Service
@Transactional
public class ReportMoneyServiceImp implements ReportMoneyService {
	@Autowired
	private SpendMoneyService spendMoneyService;

}
