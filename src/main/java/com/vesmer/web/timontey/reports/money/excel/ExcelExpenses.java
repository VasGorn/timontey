package com.vesmer.web.timontey.reports.money.excel;

import org.apache.poi.ss.usermodel.Sheet;

public interface ExcelExpenses {
	void writeExpenses(Sheet sheet);
}
