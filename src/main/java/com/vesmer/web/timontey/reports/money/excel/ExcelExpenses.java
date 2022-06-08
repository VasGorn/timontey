package com.vesmer.web.timontey.reports.money.excel;

import org.apache.poi.ss.usermodel.Sheet;

import com.vesmer.web.timontey.domain.MoneySpend;

public interface ExcelExpenses {
	void writeExpenses(Sheet sheet, MoneySpend moneySpend);
}
