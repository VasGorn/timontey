package com.vesmer.web.timontey.service;

import com.vesmer.web.timontey.domain.ReportMoney;

public interface ReportMoneyService {

	ReportMoney getReportMoney(long orderId, short year, short numMonth);

}
