package com.vesmer.web.timontey.service.imp;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.MoneySpend;
import com.vesmer.web.timontey.domain.MoneySpendExpense;
import com.vesmer.web.timontey.domain.ReportMoney;
import com.vesmer.web.timontey.service.ReportMoneyService;
import com.vesmer.web.timontey.service.SpendMoneyService;


@Service
@Transactional
public class ReportMoneyServiceImp implements ReportMoneyService {
	@Autowired
	private SpendMoneyService spendMoneyService;

	@Override
	public ReportMoney getReportMoney(long orderId, short year, short numMonth) {
		List<MoneySpend> mAllSpendList = 
				spendMoneyService.getMoneySpendListForOrder(orderId);
		List<MoneySpend> mSpendList = new LinkedList<MoneySpend>();
		
		for(MoneySpend mSpend: mAllSpendList) {
			
			List<MoneySpendExpense> mSpendExpenseOnDate = 
					getMoneyExpenseListOnDate(mSpend.getMoneyExpenseList(), 
							year, numMonth);
			MoneySpend mSpendOnDate = new MoneySpend();
			mSpendOnDate.setQuotaMoney(mSpend.getQuotaMoney());
			mSpendOnDate.setMoneyExpenseList(mSpendExpenseOnDate);
			mSpendList.add(mSpendOnDate);
		}
		
		ReportMoney reportMoney = new ReportMoney();
		reportMoney.setMoneySpendList(mSpendList);
		reportMoney.setYear(year);
		reportMoney.setNumMonth(numMonth);
		return reportMoney;
	}
}
